from alinhamento import alinhar_pagina
from deteccao_blocos import encontrar_blocos
from leitura_respostas import ler_respostas
import cv2
import os
import sys

debugMode = True
debugPath = "debug"
IMAGE_PATH = "imagens/450/450_peb.png"

if __name__ == "__main__":
  # Cria o diretório de debug se necessário
  if debugMode:
    os.makedirs(debugPath, exist_ok=True)

  # ETAPA 1 - Alinhamento da página
  imagem_alinhada = alinhar_pagina(IMAGE_PATH, debugMode, debugPath)
  if imagem_alinhada is None:
    print("\n[FALHA] Etapa 1 falhou. Processo interrompido.")
    sys.exit(1) 

  if debugMode:
    cv2.imwrite(f"{debugPath}/etapa1_alinhada.png", imagem_alinhada)
  print("\n[SUCESSO] Etapa 1 concluída!")

  # ETAPA 2 - Encontrar blocos de respostas
  blocos = encontrar_blocos(imagem_alinhada)
  print(f"[INFO] {len(blocos)} bloco(s) encontrado(s).")

  if debugMode:
    debug_etapa2 = imagem_alinhada.copy()
    for contorno in blocos:
      (x, y, w, h) = cv2.boundingRect(contorno)
      cv2.rectangle(debug_etapa2, (x, y), (x + w, y + h), (0, 255, 0), 3)
    cv2.imwrite(f"{debugPath}/etapa2_blocos.png", debug_etapa2)

  # ETAPA 3 - Ler respostas
  respostas = ler_respostas(imagem_alinhada, blocos, debugMode, debugPath)
  print("\n[RESULTADO FINAL]")
  for i, r in enumerate(respostas, start=1):
    print(f"Questão {i:02d}: {r}")
