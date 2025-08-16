import 'package:abcde/features/audit/data/models/audit_filter_model.dart';
import 'package:abcde/features/audit/presentation/controller/audit_controller.dart';
import 'package:abcde/features/audit/presentation/controller/audit_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class AuditFilterBottomSheet extends ConsumerStatefulWidget {
  final Function(AuditFilterModel) onApplyFilters;

  const AuditFilterBottomSheet({
    required this.onApplyFilters,
    super.key,
  });

  @override
  ConsumerState<AuditFilterBottomSheet> createState() => _AuditFilterBottomSheetState();
}

class _AuditFilterBottomSheetState extends ConsumerState<AuditFilterBottomSheet> {
  final _userController = TextEditingController();
  final _detailsController = TextEditingController();

  @override
  void initState() {
    super.initState();
    // Lê o estado UMA VEZ para popular os campos com os filtros atuais.
    final currentFilters = ref.read(auditControllerProvider).maybeMap(
      data: (data) => data.filters,
      orElse: () => const AuditFilterModel(),
    );
    _userController.text = currentFilters.user ?? '';
    _detailsController.text = currentFilters.details ?? '';
  }

  @override
  void dispose() {
    _userController.dispose();
    _detailsController.dispose();
    super.dispose();
  }

  void _handleApplyFilters() {
    final newFilters = AuditFilterModel(
      user: _userController.text.isNotEmpty ? _userController.text : null,
      details: _detailsController.text.isNotEmpty ? _detailsController.text : null,
    );
    // CHAMA O CALLBACK DO PAI, em vez de chamar o controller diretamente.
    widget.onApplyFilters(newFilters);
    Navigator.of(context).pop();
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        bottom: MediaQuery.of(context).viewInsets.bottom,
        left: 24, right: 24, top: 24,
      ),
      child: SingleChildScrollView(
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const Text('Filtrar Auditoria', textAlign: TextAlign.center, style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
            const SizedBox(height: 24),
            TextFormField(
              controller: _userController,
              decoration: const InputDecoration(labelText: 'Nome do Usuário', border: OutlineInputBorder()),
            ),
            const SizedBox(height: 16),
            TextFormField(
              controller: _detailsController,
              decoration: const InputDecoration(labelText: 'Detalhes', border: OutlineInputBorder()),
            ),
            const SizedBox(height: 32),
            ElevatedButton(
              onPressed: _handleApplyFilters,
              child: const Text('APLICAR FILTROS'),
            ),
            const SizedBox(height: 16),
          ],
        ),
      ),
    );
  }
}