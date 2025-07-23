from alinhamento import alinhar_pagina
from deteccao_blocos import encontrar_blocos
from leitura_respostas import ler_respostas
from leituras_extras import ler_info_qr_code, verificar_falta_aluno 
from orientacao import corrigir_orientacao_imagem

import cv2
import os
import sys

debugMode = True
debugPath = "debug"
IMAGE_PATH = "imagens/200/200_cinza.png"

if __name__ == "__main__":
  # Cria o diretório de debug se necessário
  if debugMode:
    os.makedirs(debugPath, exist_ok=True)

  # ETAPA 1 - Carregar e Corrigir Orientação
  imagem_inicial = cv2.imread(IMAGE_PATH)
  if imagem_inicial is None:
    print(f"[ERRO] Não foi possível carregar a imagem em '{IMAGE_PATH}'")
    sys.exit(1)
      
  imagem_orientada = corrigir_orientacao_imagem(imagem_inicial, debugMode, debugPath)

  # ETAPA 2 - Alinhamento da página
  imagem_alinhada = alinhar_pagina(imagem_orientada, debugMode, debugPath)
  if imagem_alinhada is None:
    print("\n[FALHA] Etapa 2 falhou. Processo interrompido.")
    sys.exit(1) 

  if debugMode:
    cv2.imwrite(f"{debugPath}/debug_etapa2_imagem_alinhada.png", imagem_alinhada)

  # ### NOVA ETAPA: Leitura do QR Code e Verificação de Falta ###
  print("\n[INFO] Etapa 3: Lendo QR Code e marcação de falta...")
  dados_qr, bbox_qr = ler_info_qr_code(imagem_alinhada)
  aluno_faltou = verificar_falta_aluno(imagem_alinhada, bbox_qr, debugMode, debugPath)

  print(f"[RESULTADO 3] Dados do QR Code: {dados_qr}")
  print(f"[RESULTADO 3] Aluno faltou: {'Sim' if aluno_faltou else 'Não'}")
  
  if debugMode and bbox_qr:
    debug_extras = imagem_alinhada.copy()
    (x,y,w,h) = bbox_qr
    cv2.rectangle(debug_extras, (x, y), (x + w, y + h), (255, 0, 0), 2) # Desenha o QR Code em azul
    
    # Desenha a ROI da verificação de falta
    y_roi = y + h + int(h * 0.1)
    h_roi = int(h * 0.35)
    cor_roi_falta = (0, 255, 0) if aluno_faltou else (0, 0, 255) # Verde se faltou, vermelho se não
    cv2.rectangle(debug_extras, (x, y_roi), (x + w, y_roi + h_roi), cor_roi_falta, 2)
    
    cv2.imwrite(f"{debugPath}/debug_etapa3_areas_identificadas.png", debug_extras)

  # ETAPA 4 - Encontrar blocos de respostas
  blocos = encontrar_blocos(imagem_alinhada)
  print(f"[INFO] {len(blocos)} bloco(s) encontrado(s).")

  if debugMode:
    debug_etapa2 = imagem_alinhada.copy()
    for contorno in blocos:
      (x, y, w, h) = cv2.boundingRect(contorno)
      cv2.rectangle(debug_etapa2, (x, y), (x + w, y + h), (0, 255, 0), 3)
    cv2.imwrite(f"{debugPath}/desbug_etapa4_blocos.png", debug_etapa2)

  # ETAPA 5 - Ler respostas
  respostas = ler_respostas(imagem_alinhada, blocos, debugMode, debugPath)
  print("\n[RESULTADO FINAL]")
  for i, r in enumerate(respostas, start=1):
    print(f"Questão {i:02d}: {r}")
