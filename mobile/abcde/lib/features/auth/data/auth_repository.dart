import 'package:abcde/core/providers/dio_provider.dart';
import 'package:abcde/core/services/secure_storage_service.dart';
import 'package:abcde/features/auth/data/models/login_request_model.dart';
import 'package:abcde/features/auth/data/models/login_response_model.dart';
import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'auth_repository.g.dart';

@riverpod
AuthRepository authRepository(Ref ref) {
  final dio = ref.watch(dioProvider);
  final storageService = ref.watch(secureStorageServiceProvider);
  return AuthRepository(dio, storageService);
}

class AuthRepository {
  final Dio _dio;
  final SecureStorageService _storageService;

  AuthRepository(this._dio, this._storageService);

  Future<void> login(LoginRequestModel requestModel) async {
    try {
      final response = await _dio.post(
        "/auth",
        data: requestModel.toJson()
      );

      final loginResponse = LoginResponseModel.fromJson(response.data);

      await _storageService.saveToken(loginResponse.token);   
    } catch (e) {
      rethrow;
    }
  }

  Future<String?> getToken() async {
    return await _storageService.getToken();
  }

  Future<void> logout() async {
    await _storageService.deleteToken();
  }
}