import cv2
from PIL import Image
import numpy as np
import os

def save_compressed_image(debug_image, pathFileToSave, quality=65, max_colors=256):
    """
    Salva a imagem com compressão agressiva.
    
    Args:
        debug_image: imagem em formato numpy array (OpenCV)
        pathFileToSave: caminho completo da imagem
        quality: qualidade JPEG (1-100, menor = mais compressão)
        max_colors: número máximo de cores para PNG (8-256)
    """
    ext = os.path.splitext(pathFileToSave)[1].lower()
    
    # Converte OpenCV (BGR) para Pillow (RGB)
    if len(debug_image.shape) == 3 and debug_image.shape[2] == 3:
        img = Image.fromarray(cv2.cvtColor(debug_image, cv2.COLOR_BGR2RGB))
    else:
        img = Image.fromarray(debug_image)
    
    if ext in [".jpg", ".jpeg"]:
        img = img.convert("RGB")
        img.save(pathFileToSave, "JPEG", optimize=True, quality=quality, subsampling=2)
        
    elif ext == ".png":
        # Opção 1: Reduzir cores drasticamente (compressão com perda controlada)
        img = img.convert("P", palette=Image.ADAPTIVE, colors=max_colors)
        img.save(pathFileToSave, "PNG", optimize=True, compress_level=9)
        
        # Opção 2 (alternativa): Salvar como JPEG mesmo sendo PNG
        # jpg_path = pathFileToSave.replace(".png", ".jpg")
        # img.convert("RGB").save(jpg_path, "JPEG", optimize=True, quality=quality)
        # print(f"PNG convertido para JPEG: {jpg_path}")
        
    else:
        # Fallback
        cv2.imwrite(pathFileToSave, debug_image)
    
    # Mostra o tamanho final
    file_size = os.path.getsize(pathFileToSave) / 1024  # KB
    # print(f"Imagem salva em: {pathFileToSave} ({file_size:.2f} KB)")