import 'package:abcde/features/clients/data/models/enums/client_status_enum.dart';
import 'package:flutter/material.dart';

class ClientsStatusBadge extends StatelessWidget {
  final ClientStatus status;
  const ClientsStatusBadge({super.key, required this.status});

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);
    final colorScheme = theme.colorScheme;

    late final Color backgroundColor;
    late final Color textColor;
    late final String label;

    switch (status) {
      case ClientStatus.ACTIVE:
        label = 'ATIVO';
        backgroundColor = Colors.green.shade100;
        textColor = Colors.green.shade900;
        break;
      case ClientStatus.INACTIVE:
        label = 'INATIVO';
        backgroundColor = colorScheme.errorContainer;
        textColor = colorScheme.onErrorContainer;
        break;
    }

    return Chip(
      label: Text(
        label,
        style: TextStyle(
          color: textColor,
          fontWeight: FontWeight.bold,
          fontSize: 12,
        ),
      ),
      backgroundColor: backgroundColor,
      // Removemos o padding padrão para um visual mais compacto
      padding: const EdgeInsets.symmetric(horizontal: 8),
      side: BorderSide.none,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
    );
  }
}