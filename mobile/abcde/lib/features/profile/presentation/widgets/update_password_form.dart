import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class UpdatePasswordForm extends ConsumerStatefulWidget {
  final GlobalKey<FormState> formKey;
  final TextEditingController currentPasswordController;
  final TextEditingController newPasswordController;
  final TextEditingController confirmPasswordController;
  final VoidCallback? onUpdateClientPassword;
  final bool isLoading;

  const UpdatePasswordForm({
    super.key,
    required this.formKey,
    required this.currentPasswordController,
    required this.newPasswordController,
    required this.confirmPasswordController,
    required this.onUpdateClientPassword,
    required this.isLoading,
  });

  @override
  ConsumerState<UpdatePasswordForm> createState() => _UpdatePasswordFormState();
}

class _UpdatePasswordFormState extends ConsumerState<UpdatePasswordForm> {
  bool _isCurrentPasswordVisible = false;
  bool _isNewPasswordVisible = false;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: EdgeInsets.only(
        bottom: MediaQuery.of(context).viewInsets.bottom,
        left: 24,
        right: 24,
        top: 24,
      ),
      child: Form(
        key: widget.formKey,
        child: Column(
          mainAxisSize: MainAxisSize.min, // Faz a sheet se ajustar ao conteúdo
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: [
            const Text(
              'Atualizar Senha',
              textAlign: TextAlign.center,
              style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 24),
            // --- CAMPO SENHA ATUAL ---
            TextFormField(
              controller: widget.currentPasswordController,
              obscureText: !_isCurrentPasswordVisible,
              decoration: InputDecoration(
                labelText: 'Senha Atual',
                border: const OutlineInputBorder(),
                suffixIcon: IconButton(
                  icon: Icon(_isCurrentPasswordVisible ? Icons.visibility_off : Icons.visibility),
                  onPressed: () => setState(() => _isCurrentPasswordVisible = !_isCurrentPasswordVisible),
                ),
              ),
              validator: (value) => value!.isEmpty ? 'Campo obrigatório' : null,
            ),
            const SizedBox(height: 16),
            // --- CAMPO NOVA SENHA ---
            TextFormField(
              controller: widget.newPasswordController,
              obscureText: !_isNewPasswordVisible,
              decoration: InputDecoration(
                labelText: 'Nova Senha',
                border: const OutlineInputBorder(),
                suffixIcon: IconButton(
                  icon: Icon(_isNewPasswordVisible ? Icons.visibility_off : Icons.visibility),
                  onPressed: () => setState(() => _isNewPasswordVisible = !_isNewPasswordVisible),
                ),
              ),
              validator: (value) {
                if (value == null || value.isEmpty) return 'Campo obrigatório';
                if (value.length < 6) return 'A senha deve ter no mínimo 6 caracteres';
                return null;
              },
            ),
            const SizedBox(height: 16),
            // --- CAMPO CONFIRMAR NOVA SENHA ---
            TextFormField(
              controller: widget.confirmPasswordController,
              obscureText: !_isNewPasswordVisible, // Usa a mesma visibilidade da nova senha
              decoration: const InputDecoration(
                labelText: 'Confirmar Nova Senha',
                border: OutlineInputBorder(),
              ),
              validator: (value) {
                if (value != widget.newPasswordController.text) {
                  return 'As senhas não coincidem';
                }
                return null;
              },
            ),
            const SizedBox(height: 32),
            ElevatedButton(
              onPressed: widget.isLoading ? null : widget.onUpdateClientPassword,
              child: widget.isLoading
                  ? const SizedBox(
                      height: 20,
                      width: 20,
                      child: CircularProgressIndicator(strokeWidth: 2),
                    )
                  : const Text('Salvar nova senha', style: TextStyle(fontSize: 16)),
            ),
            const SizedBox(height: 16),
          ],
        ),
      ),
    );
  }
}