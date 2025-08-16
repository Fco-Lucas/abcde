import 'package:abcde/core/providers/fab_visibility_provider.dart';
import 'package:abcde/core/providers/shell_action_provider.dart';
import 'package:abcde/features/audit/data/models/audit_filter_model.dart';
import 'package:abcde/features/audit/presentation/controller/audit_controller.dart';
import 'package:abcde/features/audit/presentation/controller/audit_state.dart';
import 'package:abcde/features/audit/presentation/widgets/audit_filter_bottom_sheet.dart';
import 'package:abcde/features/audit/presentation/widgets/audit_log_card.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class AuditPage extends ConsumerStatefulWidget {
  const AuditPage({super.key});

  @override
  ConsumerState<AuditPage> createState() => _AuditPageState();
}

class _AuditPageState extends ConsumerState<AuditPage> {
  final ScrollController _scrollController = ScrollController();

  @override
  void initState() {
    super.initState();
    _scrollController.addListener(_onScroll);
    WidgetsBinding.instance.addPostFrameCallback((_) {
      ref.read(shellActionProvider.notifier).setAction(_showFilterSheet);
    });
  }

  void _onScroll() {
    if (_scrollController.position.pixels >= _scrollController.position.maxScrollExtent - 200) {
      ref.read(auditControllerProvider.notifier).fetchNextPage();
    }
  }

  @override
  void dispose() {
    _scrollController.removeListener(_onScroll);
    _scrollController.dispose();
    ref.read(shellActionProvider.notifier).setAction(null);
    super.dispose();
  }

  // A PÁGINA controla a lógica de aplicar os filtros.
  void _applyFilters(AuditFilterModel newFilters) {
    ref.read(auditControllerProvider.notifier).fetchInitialLogs(filters: newFilters);
  }

  // A PÁGINA controla a lógica de exibir a sheet.
  void _showFilterSheet() {
    ref.read(fabVisibilityProvider.notifier).setVisibility(false);

    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (_) => AuditFilterBottomSheet(
        onApplyFilters: _applyFilters,
      ),
    ).whenComplete(() {
      ref.read(fabVisibilityProvider.notifier).setVisibility(true);
    });
  }

  @override
  Widget build(BuildContext context) {
    final auditState = ref.watch(auditControllerProvider);

    // Pega os filtros atuais do estado para usar no onRefresh.
    final currentFilters = auditState.maybeWhen(
      data: (_, __, filters, ___, ____) => filters,
      orElse: () => const AuditFilterModel(), // Usa filtros vazios como padrão
    );

    return auditState.when(
      initial: () => const Center(child: CircularProgressIndicator()),
      loading: () => const Center(child: CircularProgressIndicator()),
      error: (message) => Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text("Erro: $message", textAlign: TextAlign.center),
            const SizedBox(height: 16),
            ElevatedButton(
              // Tenta novamente com os filtros que estavam ativos.
              onPressed: () => ref.read(auditControllerProvider.notifier).fetchInitialLogs(filters: currentFilters),
              child: const Text('Tentar Novamente'),
            )
          ],
        ),
      ),
      data: (logs, hasMorePages, filters, isLoadingMore, paginationError) {
        if (logs.isEmpty) {
          return const Center(child: Text("Nenhum registro de auditoria encontrado."));
        }
        return RefreshIndicator(
          // Usa os filtros atuais ao puxar para atualizar.
          onRefresh: () => ref.read(auditControllerProvider.notifier).fetchInitialLogs(filters: filters),
          child: ListView.builder(
            controller: _scrollController,
            padding: const EdgeInsets.all(8.0),
            itemCount: logs.length + (isLoadingMore ? 1 : 0),
            itemBuilder: (context, index) {
              if (index == logs.length) {
                return const Center(
                  child: Padding(
                    padding: EdgeInsets.all(16.0),
                    child: CircularProgressIndicator(),
                  ),
                );
              }
              final log = logs[index];
              return AuditLogCard(log: log);
            },
          ),
        );
      },
    );
  }
}
