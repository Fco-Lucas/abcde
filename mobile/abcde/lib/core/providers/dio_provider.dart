import 'dart:io';

import 'package:abcde/core/interceptors/api_interceptor.dart';
import 'package:abcde/core/interceptors/auth_interceptor.dart';
import 'package:abcde/core/services/secure_storage_service.dart';
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
    connectTimeout: const Duration(seconds: 5),
    receiveTimeout: const Duration(seconds: 3),
  );

  final dio = Dio(options);

  final storageService = ref.watch(secureStorageServiceProvider);

  dio.interceptors.addAll([
    CustomInterceptor(),
    AuthInterceptor(storageService)
  ]);

  return dio;
}