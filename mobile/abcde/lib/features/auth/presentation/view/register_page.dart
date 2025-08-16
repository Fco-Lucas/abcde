import 'package:abcde/features/auth/presentation/controller/register_controller.dart';
import 'package:abcde/features/auth/presentation/controller/register_state.dart';
import 'package:abcde/features/auth/presentation/widgets/register_form.dart';
import 'package:abcde/features/auth/presentation/widgets/register_header.dart';
import 'package:abcde/features/clients/data/models/create_client_request_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';

class RegisterPage extends ConsumerStatefulWidget {
  const RegisterPage({super.key});

  @override
  ConsumerState<RegisterPage> createState() => _RegisterPageState();
}

class _RegisterPageState extends ConsumerState<RegisterPage> {
  final _formKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _cnpjController = TextEditingController();
  final _passwordController = TextEditingController();
  bool _isPasswordVisible = false;

  final _cnpjMask = MaskTextInputFormatter(
    mask: '##.###.###/####-##',
    filter: {"#": RegExp(r'[0-9]')},
  );

  @override
  void dispose() {
    _nameController.dispose();
    _cnpjController.dispose();
    _passwordController.dispose();
    super.dispose();
  }

  void _togglePasswordVisibility() {
    setState(() {
      _isPasswordVisible = !_isPasswordVisible;
    });
  }

  void _submitRegister() {
    if (_formKey.currentState!.validate()) {
      final cnpjNoMask = _cnpjMask.getUnmaskedText();

      final requestModel = CreateClientRequestModel(
        name: _nameController.text, 
        cnpj: cnpjNoMask,
        password: _passwordController.text, 
        urlToPost: "", 
        imageActiveDays: 30
      );
      ref.read(registerControllerProvider.notifier).createClient(requestModel);
    }
  }

  @override
  Widget build(BuildContext context) {
    ref.listen(registerControllerProvider, (_, state) {
      state.whenOrNull(
        error: (message) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(message), backgroundColor: Colors.red),
          );
        },
        success: () {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text("Cliente cadastrado com sucesso!"), backgroundColor: Colors.green),
          );
          context.go("/login");
        }
      );
    });

    final registerState = ref.watch(registerControllerProvider);
    final bool isLoading = registerState.maybeWhen(
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
                const RegisterHeader(),
                const SizedBox(height: 20),
                Card(
                  elevation: 4,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(16),
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(24.0),
                    child: Column(
                      children: [
                        RegisterForm(
                          formKey: _formKey,
                          nameController: _nameController,
                          cnpjController: _cnpjController,
                          passwordController: _passwordController,
                          isPasswordVisible: _isPasswordVisible,
                          isLoading: isLoading,
                          cnpjMask: _cnpjMask,
                          onRegister: _submitRegister,
                          onVisibilityToggle: _togglePasswordVisibility,
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            const Text('Já possui uma conta?'),
                            TextButton(
                              onPressed: () => context.go('/login'),
                              child: const Text('Realize login'),
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}