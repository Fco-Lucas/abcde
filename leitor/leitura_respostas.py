import cv2
import numpy as np

def ler_respostas(imagem_alinhada, blocos, debugMode, debugPath):
    print("\n[INFO] Etapa 5: Lendo respostas com controle total...")
    respostas = []
    gray = cv2.cvtColor(imagem_alinhada, cv2.COLOR_BGR2GRAY)
    debug_image = imagem_alinhada.copy()

    for idx_bloco, contorno in enumerate(blocos):
        x, y, w, h = cv2.boundingRect(contorno)

        # Controle de margens
        margem_esquerda_pct = 0.30
        margem_direita_pct = 0.21
        margem_superior_pct = 0.047
        margem_inferior_pct = 0.045

        area_util_x = x + int(w * margem_esquerda_pct)
        area_util_y = y + int(h * margem_superior_pct)
        area_util_w = w - int(w * (margem_esquerda_pct + margem_direita_pct))
        area_util_h = h - int(h * (margem_superior_pct + margem_inferior_pct))

        if debugMode:
            # Desenha um retângulo verde ao redor da área útil que estamos analisando
            cv2.rectangle(debug_image, (area_util_x, area_util_y), (area_util_x + area_util_w, area_util_y + area_util_h), (0, 255, 0), 2)

        linhas = 30
        colunas = 5

        espaco_x = area_util_w / colunas
        espaco_y = area_util_h / linhas

        fator_raio = 0.35  # Tamanho da bolha em relação à célula

        for linha in range(linhas):
            opcoes_marcadas = []

            for coluna in range(colunas):
                cx = int(area_util_x + coluna * espaco_x)
                cy = int(area_util_y + linha * espaco_y)

                celula = gray[cy:cy + int(espaco_y), cx:cx + int(espaco_x)]
                
                # Binariza a célula (inverte: bolhas marcadas ficam brancas = 255)
                _, binarizada = cv2.threshold(celula, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)

                # Calcula a proporção de pixels brancos (ou seja, preenchidos)
                preto_ratio = np.sum(binarizada == 255) / binarizada.size

                # Se mais de 30% da célula estiver marcada, considera marcada
                if preto_ratio > 0.3:
                    opcoes_marcadas.append(coluna)

            # Determina a resposta
            if len(opcoes_marcadas) == 0:
                resposta = 'Z'
                cor = (255, 255, 0)
            elif len(opcoes_marcadas) > 1:
                resposta = 'W'
                cor = (0, 255, 255)
            else:
                resposta = chr(ord('A') + opcoes_marcadas[0])
                cor = (0, 255, 0)

            respostas.append(resposta)

            # Desenho debug
            for coluna in range(colunas):
                cx = int(area_util_x + coluna * espaco_x)
                cy = int(area_util_y + linha * espaco_y)
                centro_x = int(cx + espaco_x / 2)
                centro_y = int(cy + espaco_y / 2)
                raio = int(min(espaco_x, espaco_y) * fator_raio)

                if coluna in opcoes_marcadas:
                    cv2.circle(debug_image, (centro_x, centro_y), raio, cor, 2)
                else:
                    cv2.circle(debug_image, (centro_x, centro_y), raio, (0, 0, 255), 1)

    if debugMode:
        cv2.imwrite(f"{debugPath}/resultado_etapa5_debug_visual.png", debug_image)
        print("[DEBUG] Imagem 'resultado_etapa5_debug_visual.png' gerada com controle total.")

    return respostas
