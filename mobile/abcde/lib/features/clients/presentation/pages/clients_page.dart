import 'package:abcde/app/providers/fab_visibility_provider.dart';
import 'package:abcde/app/providers/jwt_data_provider.dart';
import 'package:abcde/app/providers/shell_action_provider.dart';
import 'package:abcde/app/utils/dialog_utils.dart';
import 'package:abcde/app/widgets/empty_state_widget.dart';
import 'package:abcde/app/widgets/error_state_widget.dart';
import 'package:abcde/features/clients/data/enums/client_page_actions_enum.dart';
import 'package:abcde/features/clients/data/enums/client_status_enum.dart';
import 'package:abcde/features/clients/data/models/requests/create_client_request_model.dart';
import 'package:abcde/features/clients/data/models/requests/update_client_request_model.dart';
import 'package:abcde/features/clients/data/models/responses/client_filter_model.dart';
import 'package:abcde/features/clients/data/models/responses/client_response_model.dart';
import 'package:abcde/features/clients/presentation/controller/clients_action_state.dart';
import 'package:abcde/features/clients/presentation/controller/clients_controller.dart';
import 'package:abcde/features/clients/presentation/controller/clients_state.dart';
import 'package:abcde/features/clients/presentation/widgets/clients_card.dart';
import 'package:abcde/features/clients/presentation/widgets/clients_create_bottom_sheet.dart';
import 'package:abcde/features/clients/presentation/widgets/clients_filter_bottom_sheet.dart';
import 'package:abcde/features/clients/presentation/widgets/clients_update_bottom_sheet.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

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
    // Informa o que o fab da página faz
    WidgetsBinding.instance.addPostFrameCallback((_) {
      ref.read(shellActionProvider.notifier).setAction(_showCreateSheet);
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

  void _onCreateClient(CreateClientRequestModel data) {
    ref.read(clientsControllerProvider.notifier).createClient(data);
  }

  // Lógica de exibir a bottom sheet para criar um cliente
  void _showCreateSheet() {
    ref.read(fabVisibilityProvider.notifier).setVisibility(false);

    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (_) => ClientsCreateBottomSheet(onCreateClient: _onCreateClient)
    ).whenComplete(() {
      ref.read(fabVisibilityProvider.notifier).setVisibility(true);
    });
  }

  // Lógica de desativar/excluir um cliente
  void _onDeleteClient(BuildContext context, ClientResponseModel client) async {
    final confirmed = await DialogUtils.showConfirmationDialog(
      context: context,
      title: 'Confirmar Desativação',
      content: 'Tem certeza de que deseja desativar o cliente "${client.name}"?',
      confirmText: 'DESATIVAR',
    );

    if(confirmed == false) return;

    ref.read(clientsControllerProvider.notifier).deleteClient(client.id);
  }

  // Lógica de atualizar as informações de um cliente
  void _onUpdateClient(String clientId, UpdateClientRequestModel data) {
    ref.read(clientsControllerProvider.notifier).updateClient(clientId, data);
  }

  void _onShowUpdateSheet(ClientResponseModel client) {
    ref.read(fabVisibilityProvider.notifier).setVisibility(false);
    showModalBottomSheet(
      context: context,
      isScrollControlled: true,
      shape: const RoundedRectangleBorder(
        borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
      ),
      builder: (_) => ClientsUpdateBottomSheet(client: client, onUpdateClient: _onUpdateClient)
    ).whenComplete(() {
      ref.read(fabVisibilityProvider.notifier).setVisibility(true);
    });
  }

  void _onRestoreClientPassword(BuildContext context, ClientResponseModel client) async {
    final confirmed = await DialogUtils.showConfirmationDialog(
      context: context, 
      title: "Tem certeza?", 
      content: 'Você realmente deseja restaurar a senha do cliente "${client.name}"'
    );
    if(confirmed == false) return;
    ref.read(clientsControllerProvider.notifier).restorePassword(client.id);
  }

  // Lógica de visualizar os usuários do cliente
  void _onShowUsers(String clientId) {
    context.go("/clientUsers/$clientId");
  }

  // Função auxiliar para obter o texto correto para o loader de ação
  String _getLoadingTextForAction(ClientPageActions action) {
    switch (action) {
      case ClientPageActions.create:
        return 'Cadastrando cliente...';
      case ClientPageActions.delete:
        return 'Excluindo cliente...';
      case ClientPageActions.update:
        return 'Atualizando cliente...';
      default:
        return 'Processando...';
    }
  }

  @override
  Widget build(BuildContext context) {
    ref.listen<ClientsState>(clientsControllerProvider, (previous, next) {
      // --- LÓGICA PARA O LOADER DE PÁGINA INTEIRA ---
      // Se o estado anterior NÃO era de carregamento e o próximo É, mostre o loader.
      final wasPageLoading = previous?.maybeMap(loading: (_) => true, orElse: () => false) ?? false;
      final isPageLoading = next.maybeMap(loading: (_) => true, orElse: () => false);
      if (isPageLoading && !wasPageLoading) {
        DialogUtils.showLoadingDialog(ref, text: 'Buscando clientes...');
      }
      // Se o estado anterior ERA de carregamento e o próximo NÃO É, esconda o loader.
      else if (!isPageLoading && wasPageLoading) {
        DialogUtils.hideLoadingDialog(ref);
      }

      // --- LÓGICA PARA OS EFEITOS DE AÇÃO (Excluir, Criar, etc.) ---
      final actionState = next.maybeMap(data: (d) => d.actionState, orElse: () => null);
      if (actionState == null) return;

      actionState.whenOrNull(
        loading: (action) {
          // Mostra o loader de ação somente se ele já não estiver sendo mostrado pelo loader de página
          if (!isPageLoading) {
             DialogUtils.showLoadingDialog(ref, text: _getLoadingTextForAction(action));
          }
        },
        success: (message) {
          DialogUtils.hideLoadingDialog(ref);
          ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(message), backgroundColor: Colors.green));
          ref.read(clientsControllerProvider.notifier).resetActionState();
        },
        error: (message) {
          DialogUtils.hideLoadingDialog(ref);
          ScaffoldMessenger.of(context).showSnackBar(SnackBar(content: Text(message), backgroundColor: Colors.red));
          ref.read(clientsControllerProvider.notifier).resetActionState();
        },
      );
    });
    
    final clientsState = ref.watch(clientsControllerProvider);
    final userDataAsync = ref.watch(jwtDataProvider);

    return userDataAsync.when(
      loading: () => const Center(child: CircularProgressIndicator()),
      error: (err, stack) => const ErrorStateWidget(message: "Erro ao obter informações do usuário autenticado"),
      data: (userData) {
        if (userData == null) return ErrorStateWidget(message: 'Sua sessão é inválida. Por favor, faça login novamente.');
        
        final String authUserId = userData.id;

        // Pega os filtros atuais do estado para usar no onRefresh.
        final currentFilters = clientsState.maybeWhen(
          data: (_, __, filters, ___, ____, _____) => filters,
          orElse: () => const ClientFilterModel(cnpj: "", status: ClientStatus.ACTIVE), // Usa filtros como padrão
        );

        return clientsState.when(
          initial: () => const Center(child: CircularProgressIndicator(),),
          loading: () => const Center(child: CircularProgressIndicator(),),
          data: (clients, hasMorePages, filters, actionState, isLoadingMore, paginationError) {
            return Column(
              children: [
                Padding(
                  padding: const EdgeInsets.fromLTRB(16,8,16,8),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.end,
                    children: [
                      ElevatedButton.icon(
                        onPressed: _showFilterSheet,
                        icon: const Icon(Icons.filter_list, size: 20),
                        label: const Text('Filtrar'),
                        style: ElevatedButton.styleFrom(
                          padding: const EdgeInsets.symmetric(horizontal: 12, vertical: 8),
                          textStyle: const TextStyle(fontSize: 14)
                        ),
                      ),
                    ],
                  ),
                ),
                clients.isEmpty 
                ? const EmptyStateWidget(
                    message: 'Nenhum cliente encontrado para os filtros aplicados.',
                    icon: Icons.business_center_sharp,
                  )
                : Expanded(
                    child: RefreshIndicator(
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
                          final bool isAuthClient = client.id == authUserId;

                          return ClientsCard(
                            isAuthClient: isAuthClient,
                            client: client, 
                            onUpdate: () => _onShowUpdateSheet(client),
                            onDelete: () => _onDeleteClient(context, client), 
                            onRestorePassword: () => _onRestoreClientPassword(context, client),
                            onShowUsers: () => _onShowUsers(client.id)
                          );
                        },
                      ),
                    ),
                )
              ],
            );
          }, 
          error: (message) => ErrorStateWidget(
            message: message,
            onRetry: () => ref.read(clientsControllerProvider.notifier).fetchInitialClients(filters: currentFilters),
          ),
        );
      }, 
    );
  }
}