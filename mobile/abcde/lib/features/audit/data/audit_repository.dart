import 'package:abcde/app/providers/dio_provider.dart';
import 'package:abcde/features/audit/data/enums/audit_action_enum.dart';
import 'package:abcde/features/audit/data/enums/audit_program_enum.dart';
import 'package:abcde/features/audit/data/models/audit_pageable_response_model.dart';
import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'audit_repository.g.dart';

@riverpod
AuditRepository auditRepository(Ref ref) {
  final dio = ref.watch(dioProvider);
  // CORREÇÃO: Retornar a classe correta.
  return AuditRepository(dio);
}

class AuditRepository {
  final Dio _dio;
  AuditRepository(this._dio);

  /// Busca uma página de registros de auditoria com filtros opcionais.
  Future<AuditPageableResponseModel> getAllPageable({
    required int page,
    required int size,
    AuditActionEnum? action,
    String? client,
    String? user,
    AuditProgramEnum? program,
    String? details,
    String? startDate,
    String? endDate,
  }) async {
    final queryParameters = {
      'page': page,
      'size': size,
      'action': action?.name,
      'client': client,
      'user': user,
      'program': program?.name,
      'details': details,
      'startDate': startDate,
      'endDate': endDate,
    };

    try {
      final response = await _dio.get(
        "/logs",
        queryParameters: queryParameters,
      );

      return AuditPageableResponseModel.fromJson(response.data);
    } catch (e) {
      // Fallback para outros tipos de erro.
      rethrow;
    }
  }
}
