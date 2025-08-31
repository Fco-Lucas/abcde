import 'package:abcde/features/audit/data/enums/audit_action_enum.dart';
import 'package:abcde/features/audit/data/enums/audit_program_enum.dart';
import 'package:abcde/features/audit/data/models/audit_response_model.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class AuditLogCard extends StatelessWidget {
  const AuditLogCard({required this.log, super.key});
  final AuditResponseModel log;

  String _correctActionText(AuditActionEnum action) {
    final String correctAction = switch (action) {
      AuditActionEnum.CREATE => "Criação",
      AuditActionEnum.UPDATE => "Atualização",
      AuditActionEnum.DELETE => "Exclusão",
      AuditActionEnum.LOGIN => "Login",
      AuditActionEnum.PROCESSED => "Upload de imagens",
      AuditActionEnum.DOWNLOADTXT => "Baixar .txt",
    };

    return correctAction;
  }

  String _correctProgramText(AuditProgramEnum program) {
    final String correctProgram = switch (program) {
      AuditProgramEnum.CLIENT => "Clientes",
      AuditProgramEnum.CLIENTUSER => "Usuários dos clientes",
      AuditProgramEnum.LOT => "Lotes",
      AuditProgramEnum.LOTIMAGE => "Gabaritos",
      AuditProgramEnum.AUTH => "Autenticação",
    };

    return correctProgram;
  }

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
                  TextSpan(text: "Cliente: ", style: const TextStyle(fontWeight: FontWeight.bold)),
                  TextSpan(text: log.clientName),
                ],
              ),
            ),
            const SizedBox(height: 2),
            RichText(
              text: TextSpan(
                style: DefaultTextStyle.of(context).style.copyWith(fontSize: 16),
                children: [
                  TextSpan(text: "Usuário: ", style: const TextStyle(fontWeight: FontWeight.bold)),
                  TextSpan(text: log.userName),
                ],
              ),
            ),
            const SizedBox(height: 2),
            RichText(
              text: TextSpan(
                style: DefaultTextStyle.of(context).style.copyWith(fontSize: 16),
                children: [
                  TextSpan(text: "Programa: ", style: const TextStyle(fontWeight: FontWeight.bold)),
                  TextSpan(text: "${_correctProgramText(log.program)} / "),
                  TextSpan(text: _correctActionText(log.action)),
                ],
              ),
            ),
            const SizedBox(height: 2),
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