import 'package:abcde/core/errors/api_exception.dart';
import 'package:abcde/features/clients/data/client_repository.dart';
import 'package:abcde/features/clients/data/models/enums/client_page_actions_enum.dart';
import 'package:abcde/features/clients/data/models/enums/client_status_enum.dart';
import 'package:abcde/features/clients/data/models/responses/client_filter_model.dart';
import 'package:abcde/features/clients/data/models/responses/client_response_model.dart';
import 'package:abcde/features/clients/data/models/requests/create_client_request_model.dart';
import 'package:abcde/features/clients/data/models/requests/update_client_request_model.dart';
import 'package:abcde/features/clients/presentation/controller/clients_action_state.dart';
import 'package:abcde/features/clients/presentation/controller/clients_state.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'clients_controller.g.dart';

@riverpod
class ClientsController extends _$ClientsController {
  // Guarda a página atual para a paginação.
  int _currentPage = 0;

  @override
  ClientsState build() {
    // Ao iniciar o controller, busca a primeira página de logs.
    fetchInitialClients(filters: const ClientFilterModel(cnpj: "", status: ClientStatus.ACTIVE));
    return const ClientsState.initial();
  }

  Future<void> createClient(CreateClientRequestModel data) async {
    state.maybeMap(
      data: (currentState) async {
        // Informa a UI que a ação de criação está ocorrendo
        state = currentState.copyWith(actionState: const ClientActionState.loading(ClientPageActions.create));

        try {
          final clientRepository = ref.read(clientRepositoryProvider);
          
          final ClientResponseModel newClient = await clientRepository.createClient(data);

          final updatedClients = [newClient, ...currentState.clients];

          state = currentState.copyWith(
            clients: updatedClients,
            actionState: const ClientActionState.success('Cliente cadastrado com sucesso!'),
          );
        } on ApiException catch (e) {
          state = currentState.copyWith(actionState: ClientActionState.error(e.errorMessage));
        } catch (e) {
          state = currentState.copyWith(actionState: const ClientActionState.error('Ocorreu um erro inesperado.'));
        }
      },
      orElse: () {}
    );
  }

  // Busca a primeira página de clientes. Usado para a carga inicial ou ao aplicar novos filtros.
  Future<void> fetchInitialClients({
    required ClientFilterModel filters
  }) async {
    state = const ClientsState.loading();

    _currentPage = 0;
    try {
      final clientRepository = ref.read(clientRepositoryProvider);
      final response = await clientRepository.getAllPageable(
        page: 0,
        size: 20,
        cnpj: filters.cnpj,
        status: filters.status,
      );
      state = ClientsState.data(
        clients: response.content,
        hasMorePages: !response.end,
        filters: filters, // Salva os filtros no estado
      );
    } on ApiException catch (e) {
      state = ClientsState.error(e.errorMessage);
    } catch (e) {
      state = const ClientsState.error('Ocorreu um erro inesperado.');
    }
  }

  /// Busca a próxima página de clientes. Usado para o scroll infinito.
  Future<void> fetchNextPage() async {
    await state.maybeMap(
      data: (currentState) async {
        if (currentState.isLoadingMore || !currentState.hasMorePages) {
          return;
        }

        // Informa a UI que estamos carregando mais itens.
        state = currentState.copyWith(isLoadingMore: true, paginationError: null);

        try {
          _currentPage++;
          final clientRepository = ref.read(clientRepositoryProvider);
          final response = await clientRepository.getAllPageable(
            page: _currentPage,
            size: 20,
            cnpj: currentState.filters.cnpj,
            status: currentState.filters.status,
          );

          // Anexa os novos clientes à lista existente.
          final newClients = [...currentState.clients, ...response.content];

          state = currentState.copyWith(
            clients: newClients,
            hasMorePages: !response.end,
            isLoadingMore: false,
          );
        } on ApiException catch (e) {
          state = currentState.copyWith(
            isLoadingMore: false,
            paginationError: e.errorMessage,
          );
        } catch (e) {
          state = currentState.copyWith(
            isLoadingMore: false,
            paginationError: 'Ocorreu um erro inesperado ao carregar mais itens.',
          );
        }
      },
      // O 'orElse' é obrigatório e será executado para todos os outros estados
      // (initial, loading, error), nos quais não queremos fazer nada.
      orElse: () {},
    );
  }

  Future<void> updateClient(String clientId, UpdateClientRequestModel data) async {
    state.maybeMap(
      data: (currentState) async {
        // Informa a UI que a ação de 'update' está em andamento
        state = currentState.copyWith(actionState: const ClientActionState.loading(ClientPageActions.update));

        try {
          final clientRepository = ref.read(clientRepositoryProvider);
          
          await clientRepository.updateClient(clientId, data);

          // Recarrega os dados da primeira página com os filtros atuais
          await fetchInitialClients(filters: currentState.filters);

          // Informa a UI que a ação de atualizar foi um sucesso
          state.maybeMap(
            data: (updatedState) {
              state = updatedState.copyWith(
                actionState: const ClientActionState.success('Cliente atualizado com sucesso!'),
              );
            },
            orElse: () {},
          );
        } on ApiException catch (e) {
          state = currentState.copyWith(actionState: ClientActionState.error(e.errorMessage));
        } catch (e) {
          state = currentState.copyWith(actionState: const ClientActionState.error('Ocorreu um erro inesperado.'));
        }
      },
      orElse: () {}
    );
  }

  Future<void> deleteClient(String clientId) async {
    state.maybeMap(
      data: (currentState) async {
        // Informa a UI que a ação de 'delete' está em andamento
        state = currentState.copyWith(actionState: const ClientActionState.loading(ClientPageActions.delete));

        try {
          final clientRepository = ref.read(clientRepositoryProvider);
          await clientRepository.deleteClient(clientId);

          // Recarrega os dados da primeira página com os filtros atuais
          await fetchInitialClients(filters: currentState.filters);

          // Informa a UI que a ação de atualizar foi um sucesso
          state.maybeMap(
            data: (updatedState) {
              state = updatedState.copyWith(
                actionState: const ClientActionState.success('Cliente excluído com sucesso!'),
              );
            },
            orElse: () {},
          );
        } on ApiException catch (e) {
          state = currentState.copyWith(actionState: ClientActionState.error(e.errorMessage));
        } catch (e) {
          state = currentState.copyWith(actionState: const ClientActionState.error('Ocorreu um erro inesperado.'));
        }
      },
      orElse: () {},
    );
  }

  Future<void> restorePassword(String clientId) async {
    state.maybeMap(
      data: (currentState) async {
        state = currentState.copyWith(actionState: const ClientActionState.loading(ClientPageActions.restorePassword));

        try {
          final clientRepository = ref.read(clientRepositoryProvider);
          
          await clientRepository.restoreClientPassword(clientId);

          state = currentState.copyWith(
            actionState: const ClientActionState.success('Senha do cliente restaurada com sucesso!'),
          );
        } on ApiException catch (e) {
          state = currentState.copyWith(actionState: ClientActionState.error(e.errorMessage));
        } catch (e) {
          state = currentState.copyWith(actionState: const ClientActionState.error('Ocorreu um erro inesperado.'));
        }
      },
      orElse: () {}
    );
  }

  // É útil ter um método para resetar o estado da ação
  void resetActionState() {
    state.maybeMap(
      data: (currentState) {
        state = currentState.copyWith(actionState: const ClientActionState.initial());
      },
      orElse: () {},
    );
  }
}