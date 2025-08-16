import 'package:abcde/features/audit/data/models/audit_filter_model.dart';
import 'package:abcde/features/audit/data/models/audit_response_model.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'audit_state.freezed.dart';

@freezed
sealed class AuditState with _$AuditState {
  const factory AuditState.initial() = _Initial;
  const factory AuditState.loading() = _Loading;
  const factory AuditState.data({
    // A lista de logs que já foram carregados.
    required List<AuditResponseModel> logs,
    // Flag para saber se ainda existem mais páginas para buscar no servidor.
    required bool hasMorePages,
    required AuditFilterModel filters,
    // Flag para controlar o CircularProgressIndicator no final da lista
    // ao buscar as páginas seguintes (ex: página 2, 3, ...).
    @Default(false) bool isLoadingMore,
    // Para armazenar um erro que possa ocorrer ao buscar as páginas seguintes,
    // sem perder os logs que já foram carregados.
    String? paginationError,
  }) = _Data;
  const factory AuditState.error(String message) = _Error;
}