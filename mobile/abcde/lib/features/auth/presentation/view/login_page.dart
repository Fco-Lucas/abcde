import 'package:abcde/features/auth/data/models/login_request_model.dart';
import 'package:abcde/features/auth/presentation/controller/login_controller.dart';
import 'package:abcde/features/auth/presentation/controller/login_state.dart';
import 'package:abcde/features/auth/presentation/widgets/login_form.dart';
import 'package:abcde/features/auth/presentation/widgets/login_header.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';

class LoginPage extends ConsumerStatefulWidget {
  const LoginPage({super.key});

  @override
  ConsumerState<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends ConsumerState<LoginPage> {
  final _formKey = GlobalKey<FormState>();
  final _loginController = TextEditingController();
  final _passwordController = TextEditingController();
  bool _isPasswordVisible = false;

  final _cnpjMask = MaskTextInputFormatter(
    mask: '##.###.###/####-##',
    filter: {"#": RegExp(r'[0-9]')},
  );

  @override
  void dispose() {
    _loginController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  void _togglePasswordVisibility() {
    setState(() {
      _isPasswordVisible = !_isPasswordVisible;
    });
  }

  void _submitLogin() {
    if (_formKey.currentState!.validate()) {
      String loginValue = _loginController.text;
      final isCnpj = loginValue.replaceAll(RegExp(r'[^0-9]'), '').isNotEmpty && !loginValue.contains('@');

      // Se for um CNPJ, pega o valor sem a máscara, senão, usa o valor como está.
      if (isCnpj) {
        loginValue = loginValue.replaceAll(RegExp(r'[^0-9]'), '');
      }

      final requestModel = LoginRequestModel(
        login: loginValue,
        password: _passwordController.text,
      );
      
      ref.read(loginControllerProvider.notifier).login(requestModel);
    }
  }

  String? _validateLogin(String? value) {
    if (value == null || value.isEmpty) {
      return 'Por favor, insira seu e-mail ou CNPJ';
    }
    final isCnpj = value.replaceAll(RegExp(r'[^0-9]'), '').isNotEmpty && !value.contains('@');
    if (isCnpj) {
      if (value.length != 18) return 'CNPJ inválido';
    } else {
      final emailRegex = RegExp(r'^[^@]+@[^@]+\.[^@]+');
      if (!emailRegex.hasMatch(value)) return 'E-mail inválido';
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    ref.listen(loginControllerProvider, (_, state) {
      state.whenOrNull(
        error: (message) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(message), backgroundColor: Colors.red),
          );
        }
      );
    });

    final loginState = ref.watch(loginControllerProvider);
    final bool isLoading = loginState.maybeWhen(
      loading: () => true,
      orElse: () => false,
    );

    return Scaffold(
      body: SafeArea(
        child: Center(
          child: SingleChildScrollView(
            padding: const EdgeInsets.all(24.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.stretch,
              children: [
                const LoginHeader(),
                const SizedBox(height: 20),
                Card(
                  elevation: 4,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(16),
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(24.0),
                    // Passando todos os dados e funções para o widget filho
                    child: Column(
                      children: [
                        LoginForm(
                          formKey: _formKey,
                          loginController: _loginController,
                          passwordController: _passwordController,
                          isPasswordVisible: _isPasswordVisible,
                          isLoading: isLoading,
                          cnpjMask: _cnpjMask,
                          onLogin: _submitLogin,
                          onVisibilityToggle: _togglePasswordVisibility,
                          loginValidator: _validateLogin,
                          onLoginChanged: (_) => setState(() {}), // Passa o setState
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            const Text('Não tem uma conta?'),
                            TextButton(
                              onPressed: () => context.go('/register'),
                              child: const Text('Cadastre-se'),
                            ),
                          ],
                        ),
                      ],
                    )
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}