import 'package:flutter/material.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';

class UpdateProfileClientForm extends StatelessWidget {
  final GlobalKey<FormState> formKey;
  final TextEditingController nameController;
  final TextEditingController cnpjController;
  final TextEditingController urlToPostController;
  final VoidCallback? onUpdateInfo;
  final MaskTextInputFormatter cnpjMask;
  final bool isLoading;

  const UpdateProfileClientForm({
    super.key,
    required this.formKey,
    required this.nameController,
    required this.cnpjController,
    required this.urlToPostController,
    required this.onUpdateInfo,
    required this.cnpjMask,
    required this.isLoading
  });

  @override
  Widget build(BuildContext context) {
    return Form(
      key: formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          TextFormField(
            controller: nameController,
            decoration: InputDecoration(
              labelText: 'Nome',
              border: const OutlineInputBorder(),
              prefixIcon: const Icon(Icons.person_outline),
            ),
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Por favor, insira o nome';
              }
              return null;
            }
          ),
          const SizedBox(height: 16),
          TextFormField(
            controller: cnpjController,
            keyboardType: TextInputType.numberWithOptions(decimal: true),
            inputFormatters: [cnpjMask],
            decoration: InputDecoration(
              labelText: 'CNPJ',
              border: const OutlineInputBorder(),
              prefixIcon: const Icon(Icons.person_outline),
            ),
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Por favor, insira o CNPJ';
              }
              if (value.length != 18) {
                return 'CNPJ inválido';
              }
              return null;
            }
          ),
          const SizedBox(height: 16),
          TextFormField(
            controller: urlToPostController,
            decoration: InputDecoration(
              labelText: 'URL de destino dos dados escaneados',
              border: const OutlineInputBorder(),
              suffixIcon: const Icon(Icons.info)
            )
          ),
          const SizedBox(height: 24),
          ElevatedButton(
            onPressed: isLoading ? null : onUpdateInfo,
            child: isLoading
                ? const SizedBox(
                    height: 20,
                    width: 20,
                    child: CircularProgressIndicator(strokeWidth: 2),
                  )
                : const Text('Concluir', style: TextStyle(fontSize: 16)),
          ),
        ],
      ),
    );
  }
}