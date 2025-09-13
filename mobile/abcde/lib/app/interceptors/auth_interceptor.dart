import 'package:abcde/app/services/secure_storage_service.dart';
import 'package:dio/dio.dart';

class AuthInterceptor extends Interceptor {
  final SecureStorageService _storageService;

  AuthInterceptor(this._storageService);

  @override
  void onRequest(
    RequestOptions options,
    RequestInterceptorHandler handler,
  ) async {
    final token = await _storageService.getAuthToken();

    if (token != null) options.headers['Authorization'] = 'Bearer $token';

    return handler.next(options);
  }
}