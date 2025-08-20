from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import os
import cv2

from alinhamento import alinhar_pagina
from deteccao_blocos import encontrar_blocos
from leitura_respostas import ler_respostas
from leituras_extras import ler_info_qr_code, verificar_falta_aluno
from orientacao import corrigir_orientacao_imagem

app = FastAPI()

class ImagemRequest(BaseModel):
    path_image: str
    lot_type: str
    debug: bool = False

@app.post("/scanImage")
def processar_imagem(req: ImagemRequest):
    debug_mode = req.debug
    debug_path = "debug"

    lot_type = req.lot_type
    if lot_type != "ABCDE" and lot_type != "VTB":
        raise HTTPException(status_code=404, detail="Campo lot_type com valor inválido")

    if not os.path.exists(req.path_image):
        raise HTTPException(status_code=404, detail="Imagem não encontrada")

    if debug_mode:
        os.makedirs(debug_path, exist_ok=True)

    # Etapa 1 - Carregar e Corrigir Orientação
    imagem_inicial = cv2.imread(req.path_image)
    if imagem_inicial is None:
        raise HTTPException(status_code=400, detail="Erro ao carregar imagem")

    imagem_orientada = corrigir_orientacao_imagem(imagem_inicial, debug_mode, debug_path)

    # Etapa 2 - Alinhar página
    imagem_alinhada = alinhar_pagina(imagem_orientada, debug_mode, debug_path)
    if imagem_alinhada is None:
        raise HTTPException(status_code=400, detail="Erro ao alinhar imagem")

    if debug_mode:
        cv2.imwrite(f"{debug_path}/debug_etapa2_imagem_alinhada.png", imagem_alinhada)

    # Etapa 3 - QR Code e Falta
    dados_qr, bbox_qr = ler_info_qr_code(imagem_alinhada, debug_mode)
    aluno_faltou = verificar_falta_aluno(imagem_alinhada, bbox_qr, debug_mode, debug_path)

    try:
        if lot_type == "ABCDE":
            matricula, nomeAluno, etapa, prova, gabarito, qtdQuestoes, codigoEscola, ano, grauSerie, turno, turma = dados_qr.split("-")
        else:
            matricula, vtbFracao, faseGab, prova, nomeAluno, qtdQuestoes = dados_qr.split("-");
    except ValueError:
        raise HTTPException(status_code=400, detail=f"QR Code inválido ou em formato incorreto, lot_type = {lot_type}, dados obtidos = {dados_qr}")

    if debug_mode and bbox_qr:
        debug_extras = imagem_alinhada.copy()
        (x, y, w, h) = bbox_qr
        cv2.rectangle(debug_extras, (x, y), (x + w, y + h), (255, 0, 0), 2)
        y_roi = y + h + int(h * 0.1)
        h_roi = int(h * 0.35)
        cor_roi_falta = (0, 255, 0) if aluno_faltou else (0, 0, 255)
        cv2.rectangle(debug_extras, (x, y_roi), (x + w, y_roi + h_roi), cor_roi_falta, 2)
        cv2.imwrite(f"{debug_path}/debug_etapa3_areas_identificadas.png", debug_extras)

    # Etapa 4 - Encontrar blocos
    blocos = encontrar_blocos(imagem_alinhada, debug_mode)
    if debug_mode:
        debug_blocos = imagem_alinhada.copy()
        for contorno in blocos:
            (x, y, w, h) = cv2.boundingRect(contorno)
            cv2.rectangle(debug_blocos, (x, y), (x + w, y + h), (0, 255, 0), 3)
        cv2.imwrite(f"{debug_path}/debug_etapa4_blocos.png", debug_blocos)

    # Etapa 5 - Ler respostas
    respostas = ler_respostas(imagem_alinhada, blocos, debug_mode, debug_path, req.path_image, aluno_faltou, int(qtdQuestoes))
    respostas_formatadas = {f"{i:02d}": r for i, r in enumerate(respostas, start=1)}

    if lot_type == "ABCDE":    
        dados = {
            "matricula": matricula,
            "nomeAluno": nomeAluno,
            "etapa": etapa,
            "prova": prova,
            "gabarito": gabarito,
            "qtdQuestoes": qtdQuestoes,
            "codigoEscola": codigoEscola,
            "ano": ano,
            "grauSerie": grauSerie,
            "turno": turno,
            "turma": turma,
            "presenca": 0 if aluno_faltou else 1
        }
    else:
        dados = {
            "matricula": matricula,
            "vtbFracao": vtbFracao,
            "faseGab": faseGab,
            "prova": prova,
            "nomeAluno": nomeAluno,
            "qtdQuestoes": qtdQuestoes,
            "presenca": 0 if aluno_faltou else 1
        }

    return {
        "dados": dados,
        "respostas": respostas_formatadas
    }
