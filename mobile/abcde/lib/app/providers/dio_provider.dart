import 'dart:io';

import 'package:abcde/app/interceptors/auth_interceptor.dart';
import 'package:abcde/app/interceptors/custom_interceptor.dart';
import 'package:abcde/app/services/secure_storage_service.dart';
import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'dio_provider.g.dart';

const String androidLocalHost = "http://10.0.2.2:8181/api/v1";
// const String androidLocalHost = "http://192.168.0.12:8181/api/v1";
const String iosLocalHost = "http://localhost:8181/api/v1";

@riverpod
Dio dio(Ref ref) {
  final baseUrl = Platform.isAndroid ? androidLocalHost : iosLocalHost;

  final options = BaseOptions(
    baseUrl: baseUrl,
    connectTimeout: const Duration(seconds: 10),
    receiveTimeout: const Duration(seconds: 10),
  );

  final dio = Dio(options);

  final storageService = ref.read(secureStorageServiceProvider);

  // Adiciona os interceptors à instância do Dio.
  dio.interceptors.addAll([
    AuthInterceptor(storageService),
    CustomInterceptor(),
  ]);

  return dio;
}