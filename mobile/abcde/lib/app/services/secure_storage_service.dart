import 'dart:convert';

import 'package:abcde/app/models/jwt_data_model.dart';
import 'package:abcde/app/services/jwt_service.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'secure_storage_service.g.dart';

@riverpod
SecureStorageService secureStorageService(Ref ref) {
  final jwtService = ref.read(jwtServiceProvider);
  return SecureStorageService(jwtService);
}

class SecureStorageService {
  final JwtService _jwtService;
  final _secureStorage = const FlutterSecureStorage();

  SecureStorageService(this._jwtService);

  static const _authTokenKey = 'authToken';
  static const _jwtDataKey = 'jwtData';

  Future<void> saveToken(String token) async {
    // Decodifica o token usando o serviço especializado.
    final jwtData = _jwtService.decodeToken(token);

    // Salva o token bruto.
    await _secureStorage.write(key: _authTokenKey, value: token);

    // Converte o modelo de dados do usuário para uma string JSON e salva.
    await _secureStorage.write(
      key: _jwtDataKey,
      value: jsonEncode(jwtData.toJson()),
    );
  }
  
  Future<String?> getAuthToken() async {
    final token = await _secureStorage.read(key: _authTokenKey);
    final jwtData = await getJwtData();

    if (token == null || jwtData == null) {
      return null;
    }

    // O JWT 'exp' é um timestamp em segundos. Convertemos para milissegundos.
    final expiryDate = DateTime.fromMillisecondsSinceEpoch(jwtData.exp * 1000);

    if (expiryDate.isBefore(DateTime.now())) {
      await deleteAll(); // O token expirou, limpa tudo.
      return null;
    }

    return token;
  }

  /// Retorna os dados do usuário decodificados.
  Future<JwtDataModel?> getJwtData() async {
    final jwtDataString = await _secureStorage.read(key: _jwtDataKey);
    if (jwtDataString == null) {
      return null;
    }
    return JwtDataModel.fromJson(jsonDecode(jwtDataString));
  }

  /// Apaga todos os dados de autenticação.
  Future<void> deleteAll() async {
    await _secureStorage.delete(key: _authTokenKey);
    await _secureStorage.delete(key: _jwtDataKey);
  }
}