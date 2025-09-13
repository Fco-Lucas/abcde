import 'package:abcde/features/clients/data/models/requests/create_client_request_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';

class ClientsCreateBottomSheet extends ConsumerStatefulWidget {
  const ClientsCreateBottomSheet({
    super.key,
    required this.onCreateClient,
  });

  final Function(CreateClientRequestModel) onCreateClient;

  @override
  ConsumerState<ClientsCreateBottomSheet> createState() => _ClientsCreateBottomSheetState();
}

class _ClientsCreateBottomSheetState extends ConsumerState<ClientsCreateBottomSheet> {
  final _formKey = GlobalKey<FormState>();

  final _nameController = TextEditingController();
  final _cnpjController = TextEditingController();
  final _passwordController = TextEditingController();
  final _urlToPostController = TextEditingController();
  final _imageActiveDaysController = TextEditingController();

  @override
  void initState() {
    super.initState();

    _imageActiveDaysController.text = "30";
  }

  @override
  void dispose() {
    _nameController.dispose();
    _cnpjController.dispose();
    _passwordController.dispose();
    _urlToPostController.dispose();
    _imageActiveDaysController.dispose();
    super.dispose();
  }

  final _cnpjMask = MaskTextInputFormatter(
    mask: '##.###.###/####-##',
    filter: {"#": RegExp(r'[0-9]')},
  );

  void _onCreateClient() {
    if (_formKey.currentState?.validate() ?? false) {
      final unmaskedCnpj = _cnpjMask.getUnmaskedText();
      final data = CreateClientRequestModel(
        name: _nameController.text.trim(),
        cnpj: unmaskedCnpj,
        password: _passwordController.text,
        urlToPost: _urlToPostController.text.trim(),
        imageActiveDays: _imageActiveDaysController.text.isNotEmpty
            ? int.tryParse(_imageActiveDaysController.text) ?? 30
            : 30,
      );
      widget.onCreateClient(data);
      Navigator.of(context).pop();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        bottom: MediaQuery.of(context).viewInsets.bottom,
        left: 24,
        right: 24,
        top: 24,
      ),
      child: SingleChildScrollView(
        child: Form(
          key: _formKey,
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: [
              Text(
                'Novo Cliente',
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

              // Senha
              TextFormField(
                controller: _passwordController,
                obscureText: true,
                decoration: const InputDecoration(
                  labelText: 'Senha*',
                  border: OutlineInputBorder(),
                ),
                validator: (value) {
                  if (value == null || value.isEmpty) return 'Senha é obrigatória';
                  if (value.length < 6) return 'A senha deve ter pelo menos 6 caracteres';
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
                onPressed: _onCreateClient,
                child: const Text('Cadastrar Cliente'),
              ),
              const SizedBox(height: 16),
            ],
          ),
        ),
      ),
    );
  }
}
