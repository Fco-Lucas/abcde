import 'package:abcde/app/models/jwt_data_model.dart';
import 'package:abcde/app/services/secure_storage_service.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'jwt_data_provider.g.dart';

@riverpod
Future<JwtDataModel?> jwtData(Ref ref) async {
  final storageService = ref.watch(secureStorageServiceProvider);
  return storageService.getJwtData();
}