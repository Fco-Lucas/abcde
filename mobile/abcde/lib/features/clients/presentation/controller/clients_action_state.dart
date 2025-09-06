import 'package:abcde/features/clients/data/enums/client_page_actions_enum.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'clients_action_state.freezed.dart';

@freezed
sealed class ClientActionState with _$ClientActionState {
  // O estado inicial ou de repouso das ações.
  const factory ClientActionState.initial() = _ActionInitial;
  // O estado de carregamento, que sabe QUAL ação está em andamento.
  const factory ClientActionState.loading(ClientPageActions action) = _ActionLoading;
  // O estado de sucesso, que pode carregar uma mensagem.
  const factory ClientActionState.success(String message) = _ActionSuccess;
  // O estado de erro.
  const factory ClientActionState.error(String message) = _ActionError;
}
