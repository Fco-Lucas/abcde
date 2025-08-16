import 'package:flutter/material.dart';

class UpdateProfileClientUserForm extends StatelessWidget {
  final GlobalKey<FormState> formKey;
  final TextEditingController nameController;
  final TextEditingController emailController;
  final VoidCallback? onUpdateInfo;
  final bool isLoading;

  const UpdateProfileClientUserForm({
    super.key,
    required this.formKey,
    required this.nameController,
    required this.emailController,
    required this.onUpdateInfo,
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
            controller: emailController,
            keyboardType: TextInputType.emailAddress,
            decoration: InputDecoration(
              labelText: 'E-mail',
              border: const OutlineInputBorder(),
              prefixIcon: const Icon(Icons.person_outline),
            ),
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Por favor, insira o e-mail';
              }
              return null;
            }
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