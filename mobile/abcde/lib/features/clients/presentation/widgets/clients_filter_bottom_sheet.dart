import 'package:abcde/features/clients/data/enums/client_status_enum.dart';
import 'package:abcde/features/clients/data/models/responses/client_filter_model.dart';
import 'package:abcde/features/clients/presentation/controller/clients_controller.dart';
import 'package:abcde/features/clients/presentation/controller/clients_state.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';

class ClientsFilterBottomSheet extends ConsumerStatefulWidget {
  final Function(ClientFilterModel) onApplyFilters;

  const ClientsFilterBottomSheet({
    super.key,
    required this.onApplyFilters,  
  });

  @override
  ConsumerState<ClientsFilterBottomSheet> createState() => _ClientsFilterBottomSheetState();
}

class _ClientsFilterBottomSheetState extends ConsumerState<ClientsFilterBottomSheet> {
  final _formKey = GlobalKey<FormState>();

  // Controllers para os campos de texto
  final _cnpjController = TextEditingController();

  // Variáveis de estado para os Dropdowns
  ClientStatus? _selectedStatus;

  @override
  void initState() {
    super.initState();

    // Lê o estado UMA VEZ para popular os campos com os filtros atuais.
    final currentFilters = ref.read(clientsControllerProvider).maybeMap(
      data: (data) => data.filters,
      orElse: () => const ClientFilterModel(),
    );

    // Popula os campos com os filtros salvos no controller
    if (currentFilters.cnpj != null && currentFilters.cnpj!.isNotEmpty) {
      _cnpjMask.formatEditUpdate(
        TextEditingValue.empty,
        TextEditingValue(text: currentFilters.cnpj!),
      );
      _cnpjController.text = _cnpjMask.getMaskedText();
    } else {
      _cnpjController.text = '';
    }
    _selectedStatus = currentFilters.status;
  }

  @override
  void dispose() {
    _cnpjController.dispose();
    super.dispose();
  }

  // Constrói o novo modelo de filtro e envia para a página
  void _handleApplyFilters() {
    if (_formKey.currentState?.validate() ?? false) {
      final correctCnpj = _cnpjMask.getUnmaskedText();
      final newFilters = ClientFilterModel(
        cnpj: correctCnpj.isNotEmpty ? correctCnpj : null,
        status: _selectedStatus,
      );
      widget.onApplyFilters(newFilters);
      Navigator.of(context).pop();
    }
  }

  // Limpa todos os filtros e aplica
  void _clearFilters() {
    widget.onApplyFilters(const ClientFilterModel());
    Navigator.of(context).pop();
  }

  String _correctStatusText(ClientStatus status) {
    final String correctStatus = switch (status) {
      ClientStatus.ACTIVE => "Ativos",
      ClientStatus.INACTIVE => "Inativos",
    };

    return correctStatus;
  }

  final _cnpjMask = MaskTextInputFormatter(
    mask: '##.###.###/####-##',
    filter: {"#": RegExp(r'[0-9]')},
  );
  
  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        bottom: MediaQuery.of(context).viewInsets.bottom,
        left: 24, right: 24, top: 24,
      ),
      child: SingleChildScrollView(
        child: Form(
          key: _formKey,
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Text('Filtrar Clientes', textAlign: TextAlign.center, style: Theme.of(context).textTheme.headlineSmall),
              const SizedBox(height: 24),

              
              TextFormField(
              controller: _cnpjController,
              keyboardType: TextInputType.numberWithOptions(decimal: true),
              inputFormatters: [_cnpjMask],
              decoration: InputDecoration(
                labelText: 'CNPJ',
                border: const OutlineInputBorder(),
              ),
              validator: (value) {
                final digits = _cnpjMask.getUnmaskedText();
                if (digits.isNotEmpty && digits.length != 14) return 'CNPJ deve conter 14 dígitos';
                return null;
              },
            ),
              const SizedBox(height: 16),

              // Dropdown para Ação
              DropdownButtonFormField<ClientStatus?>(
                value: _selectedStatus,
                decoration: const InputDecoration(labelText: 'Situação', border: OutlineInputBorder()),
                items: [
                  const DropdownMenuItem(value: null, child: Text('Todas as situações')),
                  ...ClientStatus.values.map((status) => DropdownMenuItem(
                    value: status,
                    child: Text(_correctStatusText(status)),
                  )),
                ],
                onChanged: (value) => setState(() => _selectedStatus = value),
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
      ),
    );
  }
}