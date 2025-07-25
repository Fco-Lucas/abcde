import cv2

def encontrar_blocos(image_alinhada):
    print("\n[INFO] Etapa 4: Encontrando blocos de respostas...")
    page_h, page_w = image_alinhada.shape[:2]
    gray = cv2.cvtColor(image_alinhada, cv2.COLOR_BGR2GRAY)
    blurred = cv2.GaussianBlur(gray, (5, 5), 0)
    _, thresh = cv2.threshold(blurred, 0, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
    
    cnts, _ = cv2.findContours(thresh.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)

    candidate_blocks = []
    for c in cnts:
        (x, y, w, h) = cv2.boundingRect(c)
        aspect = h / float(w) if w > 0 else 0
        # Filtro Proporcional: verifica o tamanho do bloco em relação ao tamanho da página
        if (aspect > 2.0) and (page_w * 0.20 < w < page_w * 0.35) and (page_h * 0.40 < h < page_h * 0.70):
            candidate_blocks.append(c)

    # Lógica de de-duplicação
    block_contours = []
    if candidate_blocks:
        candidate_blocks = sorted(candidate_blocks, key=lambda c: cv2.boundingRect(c)[0])
        block_contours.append(candidate_blocks[0])
        x_tolerance = page_w * 0.1 # Tolerância também proporcional
        for c in candidate_blocks[1:]:
            current_x = cv2.boundingRect(c)[0]
            last_kept_x = cv2.boundingRect(block_contours[-1])[0]
            if current_x > last_kept_x + x_tolerance:
                block_contours.append(c)
                
    return sorted(block_contours, key=lambda c: cv2.boundingRect(c)[0])
