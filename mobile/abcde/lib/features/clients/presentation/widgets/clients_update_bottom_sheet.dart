import 'package:abcde/features/clients/data/models/responses/client_response_model.dart';
import 'package:abcde/features/clients/data/models/requests/update_client_request_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';

class ClientsUpdateBottomSheet extends ConsumerStatefulWidget {
  const ClientsUpdateBottomSheet({
    super.key,
    required this.client,
    required this.onUpdateClient
  });

  final ClientResponseModel client;
  final Function(String clientId, UpdateClientRequestModel data) onUpdateClient;

  @override
  ConsumerState<ClientsUpdateBottomSheet> createState() => _ClientsUpdateBottomSheetState();
}

class _ClientsUpdateBottomSheetState extends ConsumerState<ClientsUpdateBottomSheet> {
  final _formKey = GlobalKey<FormState>();

  final _nameController = TextEditingController();
  final _cnpjController = TextEditingController();
  final _urlToPostController = TextEditingController();
  final _imageActiveDaysController = TextEditingController();

  @override
  void initState() {
    super.initState();

    _nameController.text = widget.client.name;
    _cnpjController.text = _formatCnpj(widget.client.cnpj);
    _urlToPostController.text = widget.client.urlToPost ?? "";
    _imageActiveDaysController.text = widget.client.imageActiveDays.toString();
  }

  @override
  void dispose() {
    _nameController.dispose();
    _cnpjController.dispose();
    _urlToPostController.dispose();
    _imageActiveDaysController.dispose();
    super.dispose();
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

  void _onUpdateClient() {
    if (_formKey.currentState?.validate() ?? false) {
      final unmaskedCnpj = _cnpjMask.getUnmaskedText();
      final data = UpdateClientRequestModel(
        name: _nameController.text.trim(),
        cnpj: unmaskedCnpj,
        urlToPost: _urlToPostController.text.trim(),
        imageActiveDays: _imageActiveDaysController.text.isNotEmpty
            ? int.tryParse(_imageActiveDaysController.text) ?? 30
            : 30,
      );
      widget.onUpdateClient(widget.client.id, data);
      Navigator.of(context).pop();
    }
  }

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
          child:Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Text(
                'Atualizar Cliente',
                textAlign: TextAlign.center,
                style: Theme.of(context).textTheme.headlineSmall,
              ),
              const SizedBox(height: 24),

              // Nome
              TextFormField(
                controller: _nameController,
                decoration: const InputDecoration(
                  labelText: 'Nome*',
                  border: OutlineInputBorder(),
                ),
                validator: (value) {
                  if (value == null || value.trim().isEmpty) return 'Nome é obrigatório';
                  return null;
                },
              ),
              const SizedBox(height: 16),

              // CNPJ
              TextFormField(
                controller: _cnpjController,
                keyboardType:
                    const TextInputType.numberWithOptions(decimal: false),
                inputFormatters: [_cnpjMask],
                decoration: const InputDecoration(
                  labelText: 'CNPJ*',
                  border: OutlineInputBorder(),
                ),
                validator: (value) {
                  final digits = _cnpjMask.getUnmaskedText();
                  if (digits.isEmpty) return 'CNPJ é obrigatório';
                  if (digits.length != 14) return 'CNPJ deve conter 14 dígitos';
                  return null;
                },
              ),
              const SizedBox(height: 16),

              // URL de Post
              TextFormField(
                controller: _urlToPostController,
                decoration: const InputDecoration(
                  labelText: 'URL de destino dos dados escaneados',
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 16),

              // Dias de imagem ativa
              TextFormField(
                controller: _imageActiveDaysController,
                keyboardType: TextInputType.number,
                decoration: const InputDecoration(
                  labelText: 'Quantidade de dias que os gabaritos ficarão salvos*',
                  border: OutlineInputBorder(),
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) return 'Quantidade de dias é obrigatória';

                  final number = int.tryParse(value);
                  if (number == null) return 'Digite um número válido';
                  
                  if (number <= 0) return 'Deve ser maior que zero';
                  return null;
                },
              ),
              const SizedBox(height: 32),

              ElevatedButton(
                onPressed: _onUpdateClient,
                child: const Text('Atualizar Cliente'),
              ),
              const SizedBox(height: 16),
            ],
          ),
        ),
      ),
    );
  }
}