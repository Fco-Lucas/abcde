import 'package:abcde/features/clients/data/enums/client_status_enum.dart';
import 'package:abcde/features/clients/data/models/client_response_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class ClientsCard extends ConsumerWidget {
  final ClientResponseModel client;
  const ClientsCard({super.key, required this.client});

  String _correctStatusText(ClientStatus status) {
    final String correctStatus = switch (status) {
      ClientStatus.ACTIVE => "Ativo",
      ClientStatus.INACTIVE => "Inativo",
    };

    return correctStatus;
  }

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return Card(
      margin: const EdgeInsets.symmetric(vertical: 6, horizontal: 8),
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              client.name,
              style: TextStyle(fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 2),
            Text(
              client.cnpj,
              style: TextStyle(color: Colors.grey.shade700),
            ),
            const SizedBox(height: 12),
            Align(
              alignment: Alignment.centerRight,
              child: Text(
                _correctStatusText(client.status),
                style: TextStyle(fontSize: 12, color: Colors.grey.shade600),
              ),
            ),
          ],
        ),
      ),
    );
  }
}