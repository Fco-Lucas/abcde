import 'package:abcde/core/errors/api_exception.dart';
import 'package:abcde/features/clients/data/client_repository.dart';
import 'package:abcde/features/clients/data/models/client_filter_model.dart';
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
    fetchInitialClients(filters: const ClientFilterModel());
    return const ClientsState.initial();
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
}