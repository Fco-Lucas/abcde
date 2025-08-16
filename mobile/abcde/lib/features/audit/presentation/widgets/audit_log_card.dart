import 'package:abcde/features/audit/data/models/audit_response_model.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class AuditLogCard extends StatelessWidget {
  const AuditLogCard({required this.log, super.key});
  final AuditResponseModel log;

  @override
  Widget build(BuildContext context) {
    // Formata a data para um formato legível
    final formattedDate = DateFormat('dd/MM/yyyy HH:mm').format(DateTime.parse(log.createdAt));

    return Card(
      margin: const EdgeInsets.symmetric(vertical: 6, horizontal: 8),
      elevation: 2,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            RichText(
              text: TextSpan(
                style: DefaultTextStyle.of(context).style.copyWith(fontSize: 16),
                children: [
                  TextSpan(text: log.userName, style: const TextStyle(fontWeight: FontWeight.bold)),
                  TextSpan(text: ' (${log.action.name}) em '),
                  TextSpan(text: log.program.name, style: const TextStyle(fontWeight: FontWeight.bold)),
                ],
              ),
            ),
            const SizedBox(height: 8),
            Text(
              log.details,
              style: TextStyle(color: Colors.grey.shade700),
            ),
            const SizedBox(height: 12),
            Align(
              alignment: Alignment.centerRight,
              child: Text(
                formattedDate,
                style: TextStyle(fontSize: 12, color: Colors.grey.shade600),
              ),
            ),
          ],
        ),
      ),
    );
  }
}