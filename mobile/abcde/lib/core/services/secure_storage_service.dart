import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'secure_storage_service.g.dart';

@riverpod
SecureStorageService secureStorageService(Ref ref) {
  return SecureStorageService();
}

class SecureStorageService {
  // instância do storage
  final _secureStorage = const FlutterSecureStorage();

  static const _authTokenKey = 'authToken';
  static const _authTokenExpiryKey = 'authTokenExpiry';

  Future<void> saveToken(String token) async {
    // Define a data de expiração. Ex: 1 dias a partir de agora.
    final expiryDate = DateTime.now().add(const Duration(days: 1));

    // Salva tanto o token quanto a data de expiração (convertida para String)
    await _secureStorage.write(key: _authTokenKey, value: token);
    await _secureStorage.write(key: _authTokenExpiryKey, value: expiryDate.toIso8601String());
  }

  Future<String?> getToken() async {
    final token = await _secureStorage.read(key: _authTokenKey);
    final expiryDateString = await _secureStorage.read(key: _authTokenExpiryKey);

    // Se não houver token ou data de expiração, retorna nulo
    if (token == null || expiryDateString == null) {
      return null;
    }

    // Converte a data de volta para um objeto DateTime
    final expiryDate = DateTime.parse(expiryDateString);

    // Verifica se a data de expiração já passou
    if (expiryDate.isBefore(DateTime.now())) {
      // Se o token expirou, apaga os dados e retorna nulo
      await deleteToken();
      return null;
    }

    // Se tudo estiver certo, retorna o token
    return token;
  }

  Future<void> deleteToken() async {
    await _secureStorage.delete(key: _authTokenKey);
    await _secureStorage.delete(key: _authTokenExpiryKey);
  }
}