# Dicionário com sequências de bytes corrompidas e suas correções
CORRECOES_BYTES_QR  = {
  # Vogais maiúsculas com acento agudo
  b'\xef\xbe\x81': 'Á',
  b'\xef\xbe\x89': 'É', 
  b'\xef\xbe\x8d': 'Í',
  b'\xef\xbe\x93': 'Ó',
  b'\xef\xbe\x9a': 'Ú',  # Sequência específica do seu caso
  
  # Vogais minúsculas com acento agudo
  b'\xef\xbe\xa1': 'á',
  b'\xef\xbe\xa9': 'é',
  b'\xef\xbe\xad': 'í',
  b'\xef\xbe\xb3': 'ó',
  b'\xef\xbe\xba': 'ú',
  
  # Vogais maiúsculas com acento circunflexo
  b'\xef\xbe\x82': 'Â',
  b'\xef\xbe\x8a': 'Ê',
  b'\xef\xbe\x8e': 'Î',
  b'\xef\xbe\x94': 'Ô',
  b'\xef\xbe\x9b': 'Û',
  
  # Vogais minúsculas com acento circunflexo
  b'\xef\xbe\xa2': 'â',
  b'\xef\xbe\xaa': 'ê',
  b'\xef\xbe\xae': 'î',
  b'\xef\xbe\xb4': 'ô',
  b'\xef\xbe\xbb': 'û',
  
  # Vogais maiúsculas com til
  b'\xef\xbe\x83': 'Ã',
  b'\xef\xbe\x95': 'Õ',
  
  # Vogais minúsculas com til
  b'\xef\xbe\xa3': 'ã',
  b'\xef\xbe\xb5': 'õ',
  
  # Vogais com crase
  b'\xef\xbe\x80': 'À',
  b'\xef\xbe\xa0': 'à',
  
  # Vogais com trema
  b'\xef\xbe\x9c': 'Ü',
  b'\xef\xbe\xbc': 'ü',
  
  # Cedilha
  b'\xef\xbe\x87': 'Ç',
  b'\xef\xbe\xa7': 'ç',
  
  # Caracteres especiais
  b'\xef\xbe\xaa': 'ª',  # ordenador feminino
  b'\xef\xbe\xba': 'º',  # ordenador masculino
  b'\xef\xbe\xb0': '°',  # grau
  
  # Outras sequências problemáticas comuns
  b'\xc3\xa1': 'á', b'\xc3\xa9': 'é', b'\xc3\xad': 'í', 
  b'\xc3\xb3': 'ó', b'\xc3\xba': 'ú',
  b'\xc3\x81': 'Á', b'\xc3\x89': 'É', b'\xc3\x8d': 'Í', 
  b'\xc3\x93': 'Ó', b'\xc3\x9a': 'Ú',
  b'\xc3\xa2': 'â', b'\xc3\xaa': 'ê', b'\xc3\xb4': 'ô', 
  b'\xc3\xbb': 'û', b'\xc3\xae': 'î',
  b'\xc3\x82': 'Â', b'\xc3\x8a': 'Ê', b'\xc3\x94': 'Ô', 
  b'\xc3\x9b': 'Û', b'\xc3\x8e': 'Î',
  b'\xc3\xa3': 'ã', b'\xc3\xb5': 'õ',
  b'\xc3\x83': 'Ã', b'\xc3\x95': 'Õ',
  b'\xc3\xa0': 'à', b'\xc3\x80': 'À',
  b'\xc3\xa7': 'ç', b'\xc3\x87': 'Ç',
  b'\xc3\xbc': 'ü', b'\xc3\x9c': 'Ü',
}