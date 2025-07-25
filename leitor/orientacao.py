import cv2
from pyzbar.pyzbar import decode

# Verifica a posição do QR Code para corrigir a orientação da imagem se estiver de cabeça para baixo.
def corrigir_orientacao_imagem(imagem, debugMode=False, debugPath="."):
  print("\n[INFO] Etapa 1: Verificando e corrigindo a orientação da imagem...")
  
  # Encontra o QR code para usar como âncora
  barcodes = decode(imagem)
  
  if not barcodes:
      print("[WARNING] Nenhum QR Code encontrado para verificar a orientação. Assumindo que está correta.")
      return imagem # Retorna a imagem original se não achar o QR Code

  # Pega o primeiro QR Code encontrado (Só é para existir 1 na imagem do gabarito)
  barcode = barcodes[0]
  (x, y, w, h) = barcode.rect
  
  # Pega a altura total da imagem
  altura_total_imagem = imagem.shape[0]
  
  # Centro vertical do QR Code
  centro_y_qr = y + h / 2
  
  # Verifica se o centro do QR Code está na metade inferior da imagem
  if centro_y_qr > (altura_total_imagem / 2):
    print("[INFO] Imagem de cabeça para baixo detectada. Rotacionando 180 graus...")
    imagem_corrigida = cv2.rotate(imagem, cv2.ROTATE_180)
    
    if debugMode:
      cv2.imwrite(f"{debugPath}/debug_etapa1_imagem_rotacionada.png", imagem_corrigida)
        
    return imagem_corrigida
  else:
    print("[INFO] Orientação da imagem está correta.")
    return imagem # Retorna a imagem original sem alterações