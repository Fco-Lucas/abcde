import 'package:abcde/app/errors/api_exception.dart';
import 'package:abcde/features/audit/data/audit_repository.dart';
import 'package:abcde/features/audit/data/models/audit_filter_model.dart';
import 'package:abcde/features/audit/presentation/controller/audit_state.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'audit_controller.g.dart';

@riverpod
class AuditController extends _$AuditController {
  // Guarda a página atual para a paginação.
  int _currentPage = 0;

  @override
  AuditState build() {
    // Ao iniciar o controller, busca a primeira página de logs.
    fetchInitialLogs(filters: const AuditFilterModel());
    return const AuditState.initial();
  }

  /// Busca a primeira página de logs. Usado para a carga inicial ou ao aplicar novos filtros.
  Future<void> fetchInitialLogs({
    required AuditFilterModel filters
  }) async {
    state = const AuditState.loading();
    _currentPage = 0;

    try {
      final auditRepository = ref.read(auditRepositoryProvider);
      final response = await auditRepository.getAllPageable(
        page: 0,
        size: 20,
        action: filters.action,
        client: filters.client,
        user: filters.user,
        program: filters.program,
        details: filters.details,
        startDate: filters.startDate,
        endDate: filters.endDate,
      );
      state = AuditState.data(
        logs: response.content,
        hasMorePages: !response.end,
        filters: filters, // Salva os filtros no estado
      );
    } on ApiException catch (e) {
      state = AuditState.error(e.message);
    } catch (e) {
      state = const AuditState.error('Ocorreu um erro inesperado.');
    }
  }

  /// Busca a próxima página de logs. Usado para o scroll infinito.
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
          final auditRepository = ref.read(auditRepositoryProvider);
          final response = await auditRepository.getAllPageable(
            page: _currentPage,
            size: 20,
            user: currentState.filters.user,
            details: currentState.filters.details,
          );

          // Anexa os novos logs à lista existente.
          final newLogs = [...currentState.logs, ...response.content];

          state = currentState.copyWith(
            logs: newLogs,
            hasMorePages: !response.end,
            isLoadingMore: false,
          );
        } on ApiException catch (e) {
          state = currentState.copyWith(
            isLoadingMore: false,
            paginationError: e.message,
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
