import 'package:flutter/material.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';

class LoginForm extends StatelessWidget {
  const LoginForm({
    super.key,
    required this.formKey,
    required this.loginController,
    required this.passwordController,
    required this.isPasswordVisible,
    required this.onVisibilityToggle,
    required this.onLogin,
    required this.cnpjMask,
    required this.isLoading,
    required this.loginValidator,
    required this.onLoginChanged,
  });

  final GlobalKey<FormState> formKey;
  final TextEditingController loginController;
  final TextEditingController passwordController;
  final bool isPasswordVisible;
  final VoidCallback onVisibilityToggle;
  final VoidCallback? onLogin;
  final MaskTextInputFormatter cnpjMask;
  final bool isLoading;
  final FormFieldValidator<String> loginValidator;
  final ValueChanged<String> onLoginChanged;

  @override
  Widget build(BuildContext context) {
    return Form(
      key: formKey,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: [
          const Text(
            "Gabaritos | ABCDE",
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 22,
              fontWeight: FontWeight.bold,
            ),
          ),
          Text(
            "Acesse sua conta",
            textAlign: TextAlign.center,
            style: TextStyle(
              fontSize: 16,
              color: Colors.grey[600],
            ),
          ),
          const SizedBox(height: 10),
          TextFormField(
            controller: loginController,
            keyboardType: TextInputType.emailAddress,
            inputFormatters: [
              if (loginController.text.replaceAll(RegExp(r'[^0-9]'), '').isNotEmpty && !loginController.text.contains('@'))
                cnpjMask
            ],
            decoration: const InputDecoration(
              labelText: 'E-mail ou CNPJ',
              border: OutlineInputBorder(),
              prefixIcon: Icon(Icons.person_outline),
            ),
            validator: loginValidator, // Usa a função passada pelo pai
            onChanged: onLoginChanged, // Usa a função passada pelo pai
          ),
          const SizedBox(height: 16),
          TextFormField(
            controller: passwordController,
            obscureText: !isPasswordVisible,
            decoration: InputDecoration(
              labelText: 'Senha',
              border: const OutlineInputBorder(),
              prefixIcon: const Icon(Icons.lock_outline),
              suffixIcon: IconButton(
                icon: Icon(isPasswordVisible ? Icons.visibility_off : Icons.visibility),
                onPressed: onVisibilityToggle,
              ),
            ),
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Por favor, insira sua senha';
              }
              return null;
            },
          ),
          const SizedBox(height: 24),
          ElevatedButton(
            onPressed: isLoading ? null : onLogin,
            child: isLoading
                ? const SizedBox(
                    height: 20,
                    width: 20,
                    child: CircularProgressIndicator(strokeWidth: 2),
                  )
                : const Text('Entrar', style: TextStyle(fontSize: 16)),
          ),
        ],
      ),
    );
  }
}