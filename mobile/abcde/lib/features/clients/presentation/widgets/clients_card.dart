import 'package:abcde/features/clients/data/models/responses/client_response_model.dart';
import 'package:abcde/features/clients/presentation/widgets/clients_status_badge.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';

enum ClientAction { edit, delete, restorePassword }

class ClientsCard extends ConsumerWidget {
  ClientsCard({
    super.key, 
    required this.client,
    required this.onUpdate,
    required this.onDelete,
    required this.onRestorePassword,
    required this.onShowUsers
  });

  final ClientResponseModel client;
  final VoidCallback onUpdate;
  final VoidCallback onDelete;
  final VoidCallback onRestorePassword;
  final VoidCallback onShowUsers;

  void _handleMenuAction(ClientAction action) {
    switch (action) {
      case ClientAction.edit:
        onUpdate();
        break;
      case ClientAction.delete:
        onDelete();
        break;
      case ClientAction.restorePassword:
        onRestorePassword();
        break;
    }
  }

  final _cnpjMask = MaskTextInputFormatter(
    mask: '##.###.###/####-##',
    filter: {"#": RegExp(r'[0-9]')},
  );

  String _formatCnpj(String cnpj) {
    _cnpjMask.formatEditUpdate(
      TextEditingValue.empty,
      TextEditingValue(text: cnpj),
    );

    return _cnpjMask.getMaskedText();
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
        subtitle: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              _formatCnpj(client.cnpj),
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
                title: Text('Atualizar'),
              ),
            ),
            PopupMenuItem<ClientAction>(
              value: ClientAction.delete,
              child: ListTile(
                leading: Icon(Icons.delete_outline, color: theme.colorScheme.error),
                title: Text('Desativar', style: TextStyle(color: theme.colorScheme.error)),
              ),
            ),
            const PopupMenuItem<ClientAction>(
              value: ClientAction.restorePassword,
              child: ListTile(
                leading: Icon(Icons.restore),
                title: Text('Restaurar senha'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}