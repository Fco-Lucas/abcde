import cv2
import numpy as np

def encontrar_blocos(image_alinhada, debugMode):
    if debugMode: 
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
        
        # Seus filtros originais - EXATAMENTE como estavam
        if (aspect > 2.0) and (page_w * 0.20 < w < page_w * 0.35) and (page_h * 0.40 < h < page_h * 0.70):
            
            # CORREÇÃO CIRÚRGICA: Ajusta o bounding box para remover extensões de linhas
            # Cria uma máscara apenas deste contorno
            mask = np.zeros(thresh.shape, dtype=np.uint8)
            cv2.fillPoly(mask, [c], 255)
            
            # Encontra a região retangular "mais densa" dentro do contorno
            # Isso ignora extensões finas como riscos
            y_proj = np.sum(mask, axis=1)  # Projeção vertical
            x_proj = np.sum(mask, axis=0)  # Projeção horizontal
            
            # Encontra os limites da região densa (ignora caudas finas)
            y_threshold = np.max(y_proj) * 0.1  # 10% do pico
            x_threshold = np.max(x_proj) * 0.1  # 10% do pico
            
            # Limites Y (vertical)
            y_dense = np.where(y_proj > y_threshold)[0]
            if len(y_dense) > 0:
                y_new = y_dense[0]
                h_new = y_dense[-1] - y_dense[0] + 1
            else:
                y_new, h_new = y, h
                
            # Limites X (horizontal)  
            x_dense = np.where(x_proj > x_threshold)[0]
            if len(x_dense) > 0:
                x_new = x_dense[0] 
                w_new = x_dense[-1] - x_dense[0] + 1
            else:
                x_new, w_new = x, w
            
            # Recalcula aspect com as dimensões corrigidas
            aspect_new = h_new / float(w_new) if w_new > 0 else 0
            
            # Verifica se ainda passa nos filtros após a correção
            if (aspect_new > 2.0) and (page_w * 0.20 < w_new < page_w * 0.35) and (page_h * 0.40 < h_new < page_h * 0.70):
                # Cria um contorno corrigido
                contour_corrected = np.array([
                    [x_new, y_new], 
                    [x_new + w_new, y_new], 
                    [x_new + w_new, y_new + h_new], 
                    [x_new, y_new + h_new]
                ], dtype=np.int32)
                
                candidate_blocks.append(contour_corrected)
                
                if debugMode:
                    print(f"[DEBUG] Bloco corrigido: original=({x},{y},{w},{h}) -> corrigido=({x_new},{y_new},{w_new},{h_new})")
            elif debugMode:
                print(f"[DEBUG] Bloco rejeitado após correção: aspect={aspect_new:.2f}, w={w_new}, h={h_new}")

    # Sua lógica de de-duplicação original - INALTERADA
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