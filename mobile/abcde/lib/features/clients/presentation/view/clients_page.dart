import 'package:abcde/core/providers/fab_visibility_provider.dart';
import 'package:abcde/core/providers/shell_action_provider.dart';
import 'package:abcde/features/clients/data/models/client_filter_model.dart';
import 'package:abcde/features/clients/presentation/controller/clients_controller.dart';
import 'package:abcde/features/clients/presentation/controller/clients_state.dart';
import 'package:abcde/features/clients/presentation/widgets/clients_card.dart';
import 'package:abcde/features/clients/presentation/widgets/clients_filter_bottom_sheet.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class ClientsPage extends ConsumerStatefulWidget {
  const ClientsPage({super.key});

  @override
  ConsumerState<ClientsPage> createState() => _ClientsPageState();
}

class _ClientsPageState extends ConsumerState<ClientsPage> {
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
      ref.read(clientsControllerProvider.notifier).fetchNextPage();
    }
  }

  @override
  void dispose() {
    _scrollController.removeListener(_onScroll);
    _scrollController.dispose();
    super.dispose();
  }

  // Lógica de aplicar os filtros
  void _applyFilters(ClientFilterModel newFilters) {
    ref.read(clientsControllerProvider.notifier).fetchInitialClients(filters: newFilters);
  }

  // Lógica de exibir a bottom sheet dos filtros
  void _showFilterSheet() {
    ref.read(fabVisibilityProvider.notifier).setVisibility(false);

    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (_) => ClientsFilterBottomSheet(onApplyFilters: _applyFilters)
    ).whenComplete(() {
      ref.read(fabVisibilityProvider.notifier).setVisibility(true);
    });
  }

  @override
  Widget build(BuildContext context) {
    final clientsState = ref.watch(clientsControllerProvider);

    // Pega os filtros atuais do estado para usar no onRefresh.
    final currentFilters = clientsState.maybeWhen(
      data: (_, __, filters, ___, ____) => filters,
      orElse: () => const ClientFilterModel(), // Usa filtros vazios como padrão
    );

    return clientsState.when(
      initial: () => const Center(child: CircularProgressIndicator()),
      loading: () => const Center(child: CircularProgressIndicator()),
      data: (clients, hasMorePages, filters, isLoadingMore, paginationError) {
        if (clients.isEmpty) {
          return const Center(child: Text("Nenhum cliente encontrado."));
        }

        return RefreshIndicator(
          // Usa os filtros atuais ao puxar para atualizar.
          onRefresh: () => ref.read(clientsControllerProvider.notifier).fetchInitialClients(filters: filters),
          child: ListView.builder(
            controller: _scrollController,
            padding: const EdgeInsets.all(8.0),
            itemCount: clients.length + (isLoadingMore ? 1 : 0),
            itemBuilder: (context, index) {
              // Exibe um loader se chegar no fim
              if (index == clients.length) {
                return const Center(
                  child: Padding(
                    padding: EdgeInsets.all(16.0),
                    child: CircularProgressIndicator(),
                  ),
                );
              }
              final client = clients[index];
              return ClientsCard(client: client);
            },
          ),
        );
      }, 
      error: (message) => Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text("Erro: $message", textAlign: TextAlign.center),
            const SizedBox(height: 16),
            ElevatedButton(
              // Tenta novamente com os filtros que estavam ativos.
              onPressed: () => ref.read(clientsControllerProvider.notifier).fetchInitialClients(filters: currentFilters),
              child: const Text('Tentar Novamente'),
            )
          ],
        ),
      ),
    );
  }
}