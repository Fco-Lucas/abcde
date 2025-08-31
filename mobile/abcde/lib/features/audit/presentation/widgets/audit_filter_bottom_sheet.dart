import 'package:abcde/features/audit/data/enums/audit_action_enum.dart';
import 'package:abcde/features/audit/data/enums/audit_program_enum.dart';
import 'package:abcde/features/audit/data/models/audit_filter_model.dart';
import 'package:abcde/features/audit/presentation/controller/audit_controller.dart';
import 'package:abcde/features/audit/presentation/controller/audit_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:intl/intl.dart';

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
  // Controllers para os campos de texto
  final _clientController = TextEditingController();
  final _userController = TextEditingController();
  final _detailsController = TextEditingController();
  final _startDateController = TextEditingController();
  final _endDateController = TextEditingController();

  // Variáveis de estado para os Dropdowns e DatePickers
  AuditActionEnum? _selectedAction;
  AuditProgramEnum? _selectedProgram;
  DateTime? _selectedStartDate;
  DateTime? _selectedEndDate;

  @override
  void initState() {
    super.initState();
    // Lê o estado UMA VEZ para popular os campos com os filtros atuais.
    final currentFilters = ref.read(auditControllerProvider).maybeMap(
      data: (data) => data.filters,
      orElse: () => const AuditFilterModel(),
    );

    // Popula os campos com os filtros salvos no controller
    _selectedAction = currentFilters.action;
    _clientController.text = currentFilters.client ?? '';
    _userController.text = currentFilters.user ?? '';
    _selectedProgram = currentFilters.program;
    _detailsController.text = currentFilters.details ?? '';

    // Popula os campos de data, convertendo a string de volta para DateTime
    if (currentFilters.startDate != null) {
      _selectedStartDate = DateTime.tryParse(currentFilters.startDate!);
      if (_selectedStartDate != null) {
        _startDateController.text = DateFormat('dd/MM/yyyy').format(_selectedStartDate!);
      }
    }
    if (currentFilters.endDate != null) {
      _selectedEndDate = DateTime.tryParse(currentFilters.endDate!);
      if (_selectedEndDate != null) {
        _endDateController.text = DateFormat('dd/MM/yyyy').format(_selectedEndDate!);
      }
    }
  }

  @override
  void dispose() {
    _clientController.dispose();
    _userController.dispose();
    _detailsController.dispose();
    _startDateController.dispose();
    _endDateController.dispose();
    super.dispose();
  }

  // Função para abrir o seletor de data
  Future<void> _selectDate(BuildContext context, bool isStartDate) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: (isStartDate ? _selectedStartDate : _selectedEndDate) ?? DateTime.now(),
      firstDate: DateTime(2020),
      lastDate: DateTime(2101),
    );
    if (picked != null) {
      setState(() {
        if (isStartDate) {
          _selectedStartDate = picked;
          _startDateController.text = DateFormat('dd/MM/yyyy').format(picked);
        } else {
          _selectedEndDate = picked;
          _endDateController.text = DateFormat('dd/MM/yyyy').format(picked);
        }
      });
    }
  }

  // Constrói o novo modelo de filtro e envia para a página
  void _handleApplyFilters() {
    final newFilters = AuditFilterModel(
      action: _selectedAction,
      client: _clientController.text.isNotEmpty ? _clientController.text : null,
      user: _userController.text.isNotEmpty ? _userController.text : null,
      program: _selectedProgram,
      details: _detailsController.text.isNotEmpty ? _detailsController.text : null,
      // Converte a data para string no formato ISO para enviar para a API
      startDate: _selectedStartDate?.toIso8601String(),
      endDate: _selectedEndDate?.toIso8601String(),
    );
    widget.onApplyFilters(newFilters);
    Navigator.of(context).pop();
  }

  // Limpa todos os filtros e aplica
  void _clearFilters() {
    widget.onApplyFilters(const AuditFilterModel());
    Navigator.of(context).pop();
  }

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
            Text('Filtrar Auditoria', textAlign: TextAlign.center, style: Theme.of(context).textTheme.headlineSmall),
            const SizedBox(height: 24),

            // Dropdown para Ação
            DropdownButtonFormField<AuditActionEnum?>(
              value: _selectedAction,
              decoration: const InputDecoration(labelText: 'Ação', border: OutlineInputBorder()),
              items: [
                const DropdownMenuItem(value: null, child: Text('Todas as Ações')),
                ...AuditActionEnum.values.map((action) => DropdownMenuItem(
                  value: action,
                  // Aqui você pode criar um extension para dar nomes amigáveis
                  child: Text(_correctActionText(action)),
                )),
              ],
              onChanged: (value) => setState(() => _selectedAction = value),
            ),
            const SizedBox(height: 16),
            
            // Dropdown para Programa
            DropdownButtonFormField<AuditProgramEnum?>(
              value: _selectedProgram,
              decoration: const InputDecoration(labelText: 'Programa', border: OutlineInputBorder()),
              items: [
                const DropdownMenuItem(value: null, child: Text('Todos os Programas')),
                ...AuditProgramEnum.values.map((program) => DropdownMenuItem(
                  value: program,
                  child: Text(_correctProgramText(program)),
                )),
              ],
              onChanged: (value) => setState(() => _selectedProgram = value),
            ),
            const SizedBox(height: 16),
            
            TextFormField(controller: _clientController, decoration: const InputDecoration(labelText: 'Nome do Cliente', border: OutlineInputBorder())),
            const SizedBox(height: 16),
            TextFormField(controller: _userController, decoration: const InputDecoration(labelText: 'Nome do Usuário', border: OutlineInputBorder())),
            const SizedBox(height: 16),
            TextFormField(controller: _detailsController, decoration: const InputDecoration(labelText: 'Detalhes', border: OutlineInputBorder())),
            const SizedBox(height: 16),

            // Campos de Data
            Row(
              children: [
                Expanded(
                  child: TextFormField(
                    controller: _startDateController,
                    readOnly: true,
                    decoration: const InputDecoration(labelText: 'Data Início', border: OutlineInputBorder(), suffixIcon: Icon(Icons.calendar_today)),
                    onTap: () => _selectDate(context, true),
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: TextFormField(
                    controller: _endDateController,
                    readOnly: true,
                    decoration: const InputDecoration(labelText: 'Data Fim', border: OutlineInputBorder(), suffixIcon: Icon(Icons.calendar_today)),
                    onTap: () => _selectDate(context, false),
                  ),
                ),
              ],
            ),
            
            const SizedBox(height: 32),
            Row(
              children: [
                Expanded(
                  child: OutlinedButton(
                    onPressed: _clearFilters,
                    child: const Text('LIMPAR'),
                  ),
                ),
                const SizedBox(width: 16),
                Expanded(
                  child: ElevatedButton(
                    onPressed: _handleApplyFilters,
                    child: const Text('APLICAR FILTROS'),
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
          ],
        ),
      ),
    );
  }
}