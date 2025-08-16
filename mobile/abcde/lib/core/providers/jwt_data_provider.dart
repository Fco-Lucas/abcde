import 'package:abcde/core/models/jwt_data_model.dart';
import 'package:abcde/core/services/secure_storage_service.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'jwt_data_provider.g.dart';

@riverpod
Future<JwtDataModel?> jwtData(Ref ref) async {
  // O provider "assiste" ao secureStorageServiceProvider.
  final storageService = ref.watch(secureStorageServiceProvider);
  
  // Ele chama o método para obter os dados e os retorna.
  // O Riverpod cuidará de retornar o Future e gerenciar os estados.
  return storageService.getJwtData();
}