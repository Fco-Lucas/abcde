import 'package:abcde/features/clients/data/models/client_filter_model.dart';
import 'package:abcde/features/clients/data/models/client_response_model.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'clients_state.freezed.dart';

@freezed
sealed class ClientsState with _$ClientsState {
  const factory ClientsState.initial() = _Initial;
  const factory ClientsState.loading() = _Loading;
  const factory ClientsState.data({
    // A lista de clientes que já foram carregados.
    required List<ClientResponseModel> clients,
    // Flag para saber se ainda existem mais páginas para buscar no servidor.
    required bool hasMorePages,
    required ClientFilterModel filters,
    // Flag para controlar o CircularProgressIndicator no final da lista
    // ao buscar as páginas seguintes (ex: página 2, 3, ...).
    @Default(false) bool isLoadingMore,
    // Para armazenar um erro que possa ocorrer ao buscar as páginas seguintes,
    // sem perder os logs que já foram carregados.
    String? paginationError,
  }) = _Data;
  const factory ClientsState.error(String message) = _Error;
}