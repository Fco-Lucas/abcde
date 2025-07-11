import cv2
import numpy as np

def alinhar_pagina(path_da_imagem, debugMode, debugPath):
    print(f"[INFO] Etapa 1: Carregando a imagem '{path_da_imagem}'...")
    image = cv2.imread(path_da_imagem)
    if image is None:
        print(f"[ERRO] Não foi possível carregar a imagem. Verifique o caminho.")
        return None
    
    original_image = image.copy()
    
    # Pré-processamento
    print("[INFO] Pré-processando a imagem...")
    gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    # Usar um desfoque um pouco menor pode ajudar a preservar a borda externa
    blurred = cv2.GaussianBlur(gray, (5, 5), 0)
    edged = cv2.Canny(blurred, 50, 150)

    # ### MUDANÇA 1: Operação de Fechamento para unir as bordas quebradas ###
    print("[INFO] Unindo as bordas da página com operação morfológica...")
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (9, 9))
    closed_edges = cv2.morphologyEx(edged, cv2.MORPH_CLOSE, kernel)
    
    # Salva a imagem de debug para ver o efeito do fechamento
    if debugMode:
        cv2.imwrite(f"{debugPath}/debug_etapa1_bordas_fechadas.png", closed_edges)
        print("[DEBUG] Imagem 'debug_etapa1_bordas_fechadas.png' salva.")

    # ### MUDANÇA 2: Lógica de busca de contorno mais inteligente ###
    print("[INFO] Procurando o contorno da página de forma inteligente...")
    cnts, _ = cv2.findContours(closed_edges.copy(), cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    
    if not cnts:
        print("[ERRO] Nenhum contorno foi encontrado na imagem.")
        return None

    # Ordena os contornos por área, do maior para o menor
    cnts = sorted(cnts, key=cv2.contourArea, reverse=True)
    
    page_contour = None # Inicializa como None

    # Itera sobre os maiores contornos para encontrar o que se parece com a página
    for c in cnts[:5]: # Verifica os 5 maiores contornos
        # Aproxima o contorno para uma forma com menos pontos
        peri = cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, 0.02 * peri, True)

        # A nossa página deve ter 4 cantos (vértices)
        if len(approx) == 4:
            # Verifica se a área do contorno é razoável (ex: > 40% da área da imagem)
            # Isso evita pegar contornos internos pequenos que por acaso tenham 4 cantos
            if cv2.contourArea(c) > (image.shape[0] * image.shape[1] * 0.4):
                page_contour = approx
                print("[INFO] Contorno da página válido encontrado!")
                break # Encontramos, podemos parar o loop

    if page_contour is None:
        print("[ERRO] Não foi possível encontrar um contorno de página com 4 cantos. Tentando o maior contorno como fallback...")
        # Se a lógica falhar, usa o maior contorno como era antes, mas é um sinal de alerta.
        page_contour = max(cnts, key=cv2.contourArea)

    # Encontra os 4 cantos extremos
    print("[INFO] Encontrando os 4 cantos do contorno...")
    # Como 'page_contour' já vem de approxPolyDP, ele já tem os 4 cantos.
    # A lógica de ordenação dos cantos ainda é necessária.
    pts = page_contour.reshape(4, 2)
    rect = np.zeros((4, 2), dtype="float32")
    s = pts.sum(axis=1)
    rect[0] = pts[np.argmin(s)] # Top-left
    rect[2] = pts[np.argmax(s)] # Bottom-right
    diff = np.diff(pts, axis=1)
    rect[1] = pts[np.argmin(diff)] # Top-right
    rect[3] = pts[np.argmax(diff)] # Bottom-left

    # Salva a imagem de depuração
    if debugMode:
        debug_image = original_image.copy()
        cv2.drawContours(debug_image, [page_contour], -1, (0, 255, 0), 3)
        for point in rect:
            cv2.circle(debug_image, tuple(point.astype(int)), 20, (0, 0, 255), -1)
        cv2.imwrite(f"{debugPath}/debug_etapa1_cantos_detectados.png", debug_image)
        print("[DEBUG] Imagem 'debug_etapa1_cantos_detectados.png' salva.")

    # Aplica a correção de perspectiva
    print("[INFO] Aplicando correção de perspectiva...")
    (tl, tr, br, bl) = rect
    widthA = np.sqrt(((br[0] - bl[0]) ** 2) + ((br[1] - bl[1]) ** 2))
    widthB = np.sqrt(((tr[0] - tl[0]) ** 2) + ((tr[1] - tl[1]) ** 2))
    maxWidth = max(int(widthA), int(widthB))
    heightA = np.sqrt(((tr[0] - br[0]) ** 2) + ((tr[1] - br[1]) ** 2))
    heightB = np.sqrt(((tl[0] - bl[0]) ** 2) + ((tl[1] - bl[1]) ** 2))
    maxHeight = max(int(heightA), int(heightB))
    dst = np.array([[0, 0], [maxWidth - 1, 0], [maxWidth - 1, maxHeight - 1], [0, maxHeight - 1]], dtype="float32")
    M = cv2.getPerspectiveTransform(rect, dst)
    warped = cv2.warpPerspective(original_image, M, (maxWidth, maxHeight))
    print("[INFO] Alinhamento concluído.")
    return warped
