import cv2
import numpy as np
import os
from compress_image import save_compressed_image

def ler_respostas(imagem_alinhada, blocos, debugMode, debugPath, pathFileToSave, aluno_faltou, qtdQuestoes):
    if debugMode: print("\n[INFO] Etapa 5: Lendo respostas com controle total...")
    respostas = []
    gray = cv2.cvtColor(imagem_alinhada, cv2.COLOR_BGR2GRAY)
    debug_image = imagem_alinhada.copy()

    for idx_bloco, contorno in enumerate(blocos):
        x, y, w, h = cv2.boundingRect(contorno)

        # Controle de margens
        margem_esquerda_pct = 0.18
        margem_direita_pct = 0.11
        margem_superior_pct = 0.042
        margem_inferior_pct = 0.023

        area_util_x = x + int(w * margem_esquerda_pct)
        area_util_y = y + int(h * margem_superior_pct)
        area_util_w = w - int(w * (margem_esquerda_pct + margem_direita_pct))
        area_util_h = h - int(h * (margem_superior_pct + margem_inferior_pct))

        if debugMode:
            cv2.rectangle(debug_image, (area_util_x, area_util_y),
                          (area_util_x + area_util_w, area_util_y + area_util_h), (0, 255, 0), 2)

        linhas = 30
        colunas = 5
        espaco_x = area_util_w / colunas
        espaco_y = area_util_h / linhas
        fator_raio = 0.40  # Tamanho da bolha em relação à célula

        # Define a questão que ele está com base no bloco (No máximo serão 3 blocos)
        if idx_bloco == 0:
            questao_atual = 1
        elif idx_bloco == 1:
            questao_atual = 31
        elif idx_bloco == 2:
            questao_atual = 61

        for linha in range(linhas):
            if questao_atual > qtdQuestoes:
                break; # Não processa mais nenhuma linha / questão
            
            opcoes_marcadas = []

            for coluna in range(colunas):
                cx = int(area_util_x + coluna * espaco_x)
                cy = int(area_util_y + linha * espaco_y)
                centro_x = int(cx + espaco_x / 2)
                centro_y = int(cy + espaco_y / 2)
                raio = int(min(espaco_x, espaco_y) * fator_raio)

                # Caso aluno tenha faltado → só desenha bolinha vermelha
                if aluno_faltou:
                    cv2.circle(debug_image, (centro_x, centro_y), raio, (0, 0, 255), 1)
                    continue

                # --- Processamento normal ---
                celula = gray[cy:cy + int(espaco_y), cx:cx + int(espaco_x)]
                _, binarizada = cv2.threshold(celula, 0, 255, cv2.THRESH_BINARY_INV + cv2.THRESH_OTSU)
                preto_ratio = np.sum(binarizada == 255) / binarizada.size

                if preto_ratio > 0.25:
                    opcoes_marcadas.append(coluna)

            if aluno_faltou:
                respostas.append("Z")  # Questão marcada como em branco
            else:
                if len(opcoes_marcadas) == 0:
                    resposta = 'Z'
                    cor = (255, 255, 0)
                else:
                    resposta = ''.join([chr(ord('A') + i) for i in opcoes_marcadas])
                    cor = (0, 255, 0) if len(opcoes_marcadas) == 1 else (0, 255, 255)

                respostas.append(resposta)

                # Desenha marcações
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

            # Atualiza o contador de questões
            questao_atual += 1

    # Salva imagem
    if debugMode:
        cv2.imwrite(f"{debugPath}/resultado_etapa5_debug_visual.png", debug_image)
        print("[DEBUG] Imagem 'resultado_etapa5_debug_visual.png' gerada com controle total.")

    if not debugMode:
        # ext = os.path.splitext(pathFileToSave)[1].lower()
        # if ext in [".jpg", ".jpeg"]:
        #     cv2.imwrite(pathFileToSave, debug_image, [cv2.IMWRITE_JPEG_QUALITY, 85])
        # elif ext == ".png":
        #     cv2.imwrite(pathFileToSave, debug_image, [cv2.IMWRITE_PNG_COMPRESSION, 9])
        # else:
        #     cv2.imwrite(pathFileToSave, debug_image)  # fallback sem compressão
        
        # Comprime a imagem agressivamente
        save_compressed_image(debug_image, pathFileToSave)

    return respostas
