# leitura_extras.py
from pyzbar.pyzbar import decode
import cv2
import numpy as np

def ler_info_qr_code(imagem):
  barcodes = decode(imagem)
  if barcodes:
    barcode = barcodes[0]
    dados_qr = barcode.data.decode('utf-8')
    (x, y, w, h) = barcode.rect
    bbox = (x, y, w, h)
    print(f"[INFO] QR Code detectado. Dados: {dados_qr}")
    return dados_qr, bbox
  print("[AVISO] Nenhum QR Code foi encontrado na imagem.")
  return None, None

def verificar_falta_aluno(imagem, bbox_qr_code, debugMode=False, debugPath="."):
    if bbox_qr_code is None:
        return False

    (x_qr, y_qr, w_qr, h_qr) = bbox_qr_code

    # Distância vertical do QR Code até o topo da ROI (em % da altura do QR)
    Y_OFFSET_PCT = 0.29
    # Altura da ROI (em % da altura do QR)
    H_ROI_PCT = 0.16
    # Deslocamento horizontal da ROI (em % da largura do QR)
    X_OFFSET_PCT = 0.83
    # Largura da ROI (em % da largura do QR)
    W_ROI_PCT = 0.15
    # Limiar de preenchimento para considerar a marcação como "feita"
    FILL_RATIO_THRESHOLD = 0.20

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
    print(f"[INFO] Proporção de preenchimento da marcação de falta: {fill_ratio:.2f}")

    aluno_faltou = fill_ratio > FILL_RATIO_THRESHOLD
    
    return aluno_faltou