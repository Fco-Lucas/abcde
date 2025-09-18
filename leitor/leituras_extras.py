from pyzbar.pyzbar import decode, ZBarSymbol
import cv2
import numpy as np
import base64

def ler_info_qr_code(imagem, debugMode=False):
    """
    Retorna (dados_qr:str, bbox_original:(x,y,w,h)) ou (None, None).
    Usa APENAS pyzbar. Quando rotacionar ou escalonar a imagem para leitura,
    o bbox é mapeado de volta para a imagem original.
    """
    if not isinstance(imagem, np.ndarray):
        raise TypeError("A função espera 'imagem' como numpy.ndarray (OpenCV).")

    # ---------- Helpers ----------
    def to_uint8(img):
        if img.dtype != np.uint8:
            img = cv2.normalize(img, None, 0, 255, cv2.NORM_MINMAX).astype(np.uint8)
        return img

    def strip_alpha(img):
        if img.ndim == 3 and img.shape[2] == 4:
            return cv2.cvtColor(img, cv2.COLOR_BGRA2BGR)
        return img

    def to_gray(img):
        if img.ndim == 2:
            return img
        return cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    def unsharp(gray):
        blur = cv2.GaussianBlur(gray, (0, 0), 1.0)
        return cv2.addWeighted(gray, 1.5, blur, -0.5, 0)

    def morph_close(bin_img, k=3):
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (k, k))
        return cv2.morphologyEx(bin_img, cv2.MORPH_CLOSE, kernel)

    def resize(img, scale):
        if scale == 1.0:
            return img
        h, w = img.shape[:2]
        return cv2.resize(img, (int(w*scale), int(h*scale)), interpolation=cv2.INTER_LINEAR)

    def rotate_cardinal(img, angle):
        if angle == 0:
            return img
        if angle == 90:
            return cv2.rotate(img, cv2.ROTATE_90_CLOCKWISE)
        if angle == 180:
            return cv2.rotate(img, cv2.ROTATE_180)
        if angle == 270:
            return cv2.rotate(img, cv2.ROTATE_90_COUNTERCLOCKWISE)
        return img

    def decode_qr(arr):
        return decode(arr, symbols=[ZBarSymbol.QRCODE])

    # Inverso da rotação para mapear ponto do frame rotacionado -> frame antes da rotação (já escalado)
    def inv_map_point(xr, yr, Ws, Hs, angle):
        # Ws, Hs = largura/altura antes da rotação (depois do scale)
        if angle == 0:
            xs, ys = xr, yr
        elif angle == 90:   # rot90 CW: x_r = Hs-1 - y_s; y_r = x_s
            xs, ys = yr, Hs - 1 - xr
        elif angle == 180:  # x_r = Ws-1 - x_s; y_r = Hs-1 - y_s
            xs, ys = Ws - 1 - xr, Hs - 1 - yr
        elif angle == 270:  # rot90 CCW: x_r = y_s; y_r = Ws-1 - x_s
            xs, ys = Ws - 1 - yr, xr
        else:
            xs, ys = xr, yr
        return xs, ys

    def map_rect_back_to_original(rect, scale, angle, orig_w, orig_h, Ws, Hs):
        # rect no frame rotacionado: (x, y, w, h)
        x, y, w, h = rect
        # Canto sup-esq, sup-dir, inf-esq, inf-dir
        pts_rot = [(x, y), (x + w, y), (x, y + h), (x + w, y + h)]
        # Para cada canto, volta pro frame escalado (pré-rotação)
        pts_scaled = [inv_map_point(px, py, Ws, Hs, angle) for (px, py) in pts_rot]
        # Agora desfaz o scale
        pts_orig = [(px / scale, py / scale) for (px, py) in pts_scaled]
        xs = [p[0] for p in pts_orig]
        ys = [p[1] for p in pts_orig]
        x0 = max(0, int(np.floor(min(xs))))
        y0 = max(0, int(np.floor(min(ys))))
        x1 = min(orig_w, int(np.ceil(max(xs))))
        y1 = min(orig_h, int(np.ceil(max(ys))))
        w0 = max(0, x1 - x0)
        h0 = max(0, y1 - y0)
        return (x0, y0, w0, h0)

    # ---------- Normalização da imagem ----------
    img0 = to_uint8(imagem)
    img0 = strip_alpha(img0)
    gray0 = to_gray(img0)
    H0, W0 = gray0.shape[:2]

    # ---------- Candidatos ----------
    gray = cv2.normalize(gray0, None, 0, 255, cv2.NORM_MINMAX)
    clahe = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8, 8)).apply(gray)
    sharp = unsharp(clahe)
    _, otsu = cv2.threshold(sharp, 0, 255, cv2.THRESH_BINARY + cv2.THRESH_OTSU)
    adapt = cv2.adaptiveThreshold(sharp, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C,
                                  cv2.THRESH_BINARY, 31, 2)
    otsu_c = morph_close(otsu, 3)
    adapt_c = morph_close(adapt, 3)
    inv_otsu = cv2.bitwise_not(otsu_c)
    inv_adapt = cv2.bitwise_not(adapt_c)

    candidates = [
        gray, clahe, sharp, otsu_c, adapt_c, inv_otsu, inv_adapt
    ]

    # ---------- Escalas e rotações ----------
    max_dim = max(H0, W0)
    if max_dim < 700:
        scales = [1.0, 1.5, 2.0]
    elif max_dim > 2200:
        scales = [0.75, 0.5, 1.0]
    else:
        scales = [1.0, 1.5]

    angles = [0, 90, 180, 270]

    # ---------- Tenta decodificar ----------
    for ci, cand in enumerate(candidates):
        for s in scales:
            cand_s = resize(cand, s)
            Hs, Ws = cand_s.shape[:2]  # antes da rotação
            for ang in angles:
                cand_r = rotate_cardinal(cand_s, ang)
                barcodes = decode_qr(cand_r)
                if not barcodes:
                    continue

                # Pega o maior bbox (se por acaso achar mais de um)
                barcode = max(barcodes, key=lambda b: b.rect.width * b.rect.height)
                
                # obtém os dados em base64
                base64_data = barcode.data

                # Decodifica o Base64 → bytes → string UTF-8
                dados_qr = base64.b64decode(base64_data).decode("utf-8")

                # Mapeia bbox do frame rotacionado de volta à imagem original
                bbox_orig = map_rect_back_to_original(
                    rect=barcode.rect,
                    scale=s,
                    angle=ang,
                    orig_w=W0,
                    orig_h=H0,
                    Ws=Ws,
                    Hs=Hs
                )

                if debugMode:
                    print(f"[INFO] QR via pyzbar | cand={ci} scale={s} rot={ang}° "
                          f"| bbox(rot)={barcode.rect} -> bbox(orig)={bbox_orig} "
                          f"| dados='{dados_qr[:60]}'")

                return dados_qr, bbox_orig

    if debugMode:
        print("[AVISO] Nenhum QR Code foi encontrado com pyzbar.")
    return None, None

def verificar_falta_aluno(imagem, bbox_qr_code, debugMode=False, debugPath="."):
    if bbox_qr_code is None:
        return False

    (x_qr, y_qr, w_qr, h_qr) = bbox_qr_code

    # Distância vertical do QR Code até o topo da ROI (em % da altura do QR)
    Y_OFFSET_PCT = 0.22
    # Altura da ROI (em % da altura do QR)
    H_ROI_PCT = 0.16
    # Deslocamento horizontal da ROI (em % da largura do QR)
    X_OFFSET_PCT = 0.87
    # Largura da ROI (em % da largura do QR)
    W_ROI_PCT = 0.16
    # Limiar de preenchimento para considerar a marcação como "feita"
    FILL_RATIO_THRESHOLD = 0.50 # 50% da imagem tem que está marcada no mínimo

    # Calcula a posição e o tamanho da ROI com base nos parâmetros
    y_inicio_roi = y_qr + h_qr + int(h_qr * Y_OFFSET_PCT)
    h_roi = int(h_qr * H_ROI_PCT)
    x_inicio_roi = x_qr + int(w_qr * X_OFFSET_PCT)
    w_roi = int(w_qr * W_ROI_PCT)

    # Recorta a região de interesse (ROI) da imagem em escala de cinza
    gray = cv2.cvtColor(imagem, cv2.COLOR_BGR2GRAY)
    roi_falta = gray[y_inicio_roi : y_inicio_roi + h_roi, x_inicio_roi : x_inicio_roi + w_roi]

    if roi_falta.size == 0:
        print("[AVISO] ROI da marcação de falta está fora da imagem.")
        return False
        
    # Binariza a imagem da ROI
    _, thresh = cv2.threshold(roi_falta, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)
    
    # --- GERAÇÃO DAS IMAGENS DE DEBUG ---
    if debugMode:
        cv2.imwrite(f"{debugPath}/debug_etapa3_roi_falta_recorte.png", roi_falta)
        cv2.imwrite(f"{debugPath}/debug_etapa3_roi_falta_binarizada.png", thresh)
        print("[DEBUG] Imagens de debug da ROI de falta salvas.")
    # -----------------------------------

    fill_ratio = cv2.countNonZero(thresh) / thresh.size
    if debugMode: print(f"[INFO] Proporção de preenchimento da marcação de falta: {fill_ratio:.2f}")

    aluno_faltou = fill_ratio > FILL_RATIO_THRESHOLD
    
    return aluno_faltou