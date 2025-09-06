import 'package:abcde/features/clients/data/models/client_response_model.dart';
import 'package:abcde/features/clients/presentation/widgets/clients_status_badge.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

enum ClientAction { edit, delete }

class ClientsCard extends ConsumerWidget {
  const ClientsCard({
    super.key, 
    required this.client,
    required this.onUpdate,
    required this.onDelete,
    required this.onShowUsers
  });

  final ClientResponseModel client;
  final VoidCallback onUpdate;
  final VoidCallback onDelete;
  final VoidCallback onShowUsers;

  void _handleMenuAction(ClientAction action) {
    switch (action) {
      case ClientAction.edit:
        onUpdate();
        break;
      case ClientAction.delete:
        onDelete();
        break;
    }
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final theme = Theme.of(context);
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 6, horizontal: 8),
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: ListTile(
        contentPadding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
        // Ícone ou Avatar à esquerda
        leading: CircleAvatar(
          backgroundColor: theme.colorScheme.primaryContainer,
          child: Text(
            client.name.substring(0, 1).toUpperCase(),
            style: TextStyle(
              fontWeight: FontWeight.bold,
              color: theme.colorScheme.onPrimaryContainer,
            ),
          ),
        ),
        // Título principal
        title: Text(
          client.name,
          style: const TextStyle(fontWeight: FontWeight.bold),
        ),
        subtitle: Row(
          spacing: 10,
          children: [
            Text(
              client.cnpj,
              style: TextStyle(color: theme.colorScheme.onSurfaceVariant),
            ),
            ClientsStatusBadge(status: client.status),
          ],
        ),
        // AÇÃO PRIMÁRIA: Toque no corpo do card
        onTap: onShowUsers,
        // AÇÕES SECUNDÁRIAS: Ícone de três pontos à direita
        trailing: PopupMenuButton<ClientAction>(
          onSelected: (action) => _handleMenuAction(action),
          itemBuilder: (context) => [
            const PopupMenuItem<ClientAction>(
              value: ClientAction.edit,
              child: ListTile(
                leading: Icon(Icons.edit_outlined),
                title: Text('Editar'),
              ),
            ),
            PopupMenuItem<ClientAction>(
              value: ClientAction.delete,
              child: ListTile(
                leading: Icon(Icons.delete_outline, color: theme.colorScheme.error),
                title: Text('Desativar', style: TextStyle(color: theme.colorScheme.error)),
              ),
            ),
          ],
        ),
      ),
    );
  }
}