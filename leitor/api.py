from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import os
import cv2

from alinhamento import alinhar_pagina
from deteccao_blocos import encontrar_blocos
from leitura_respostas import ler_respostas

app = FastAPI()

class ImagemRequest(BaseModel):
    path_image: str
    debug: bool = False

@app.post("/scanImage")
def processar_imagem(req: ImagemRequest):
    debug_mode = req.debug
    debug_path = "debug"

    if not os.path.exists(req.path_image):
        raise HTTPException(status_code=404, detail="Imagem não encontrada")

    if debug_mode:
        os.makedirs(debug_path, exist_ok=True)

    # Etapa 1: Alinhamento
    imagem_alinhada = alinhar_pagina(req.path_image, debug_mode, debug_path)
    if imagem_alinhada is None:
        raise HTTPException(status_code=400, detail="Erro ao alinhar imagem")

    if debug_mode:
        cv2.imwrite(f"{debug_path}/etapa1_alinhada.png", imagem_alinhada)

    # Etapa 2: Encontrar blocos
    blocos = encontrar_blocos(imagem_alinhada)

    if debug_mode:
        debug_etapa2 = imagem_alinhada.copy()
        for contorno in blocos:
            (x, y, w, h) = cv2.boundingRect(contorno)
            cv2.rectangle(debug_etapa2, (x, y), (x + w, y + h), (0, 255, 0), 3)
        cv2.imwrite(f"{debug_path}/etapa2_blocos.png", debug_etapa2)

    # Etapa 3: Ler respostas
    respostas = ler_respostas(imagem_alinhada, blocos, debug_mode, debug_path)

    resultado = {
        f"{i:02d}": r
        for i, r in enumerate(respostas, start=1)
    }

    return {"respostas": resultado}
