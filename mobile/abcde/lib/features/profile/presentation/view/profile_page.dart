import 'package:abcde/app/providers/jwt_data_provider.dart';
import 'package:abcde/features/clients/data/models/responses/client_response_model.dart';
import 'package:abcde/features/clients/data/models/requests/update_client_password_request_model.dart';
import 'package:abcde/features/clients/data/models/requests/update_client_request_model.dart';
import 'package:abcde/features/clients/providers/client_data_provider.dart';
import 'package:abcde/features/profile/presentation/controller/profile_action_enum.dart';
import 'package:abcde/features/profile/presentation/controller/profile_controller.dart';
import 'package:abcde/features/profile/presentation/controller/profile_state.dart';
import 'package:abcde/features/profile/presentation/widgets/update_password_form.dart';
import 'package:abcde/features/profile/presentation/widgets/update_profile_client_form.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:mask_text_input_formatter/mask_text_input_formatter.dart';

class ProfilePage extends ConsumerStatefulWidget {
  const ProfilePage({super.key});

  @override
  ConsumerState<ProfilePage> createState() => _ProfilePageState();
}

class _ProfilePageState extends ConsumerState<ProfilePage> {
  // Formulário de Perfil
  final _profileFormKey = GlobalKey<FormState>();
  final _nameController = TextEditingController();
  final _cnpjController = TextEditingController();
  final _urlToPostController = TextEditingController();
  final _cnpjMask = MaskTextInputFormatter(mask: '##.###.###/####-##', filter: {"#": RegExp(r'[0-9]')});

  // Formulário de Senha
  final _updatePasswordFormKey = GlobalKey<FormState>();
  final _currentPasswordController = TextEditingController();
  final _newPasswordController = TextEditingController();
  final _confirmPasswordController = TextEditingController();

  // Flag para garantir que os controllers só sejam populados uma vez.
  bool _areControllersInitialized = false;

  void _initializeControllers(ClientResponseModel client) {
    _nameController.text = client.name;
    _cnpjController.text = client.cnpj;
    _urlToPostController.text = client.urlToPost ?? "";
    _cnpjMask.formatEditUpdate(TextEditingValue.empty, TextEditingValue(text: client.cnpj));
    _areControllersInitialized = true;
  }

  void _updateClientInfo(String clientId) {
    if (_profileFormKey.currentState!.validate()) {
      final requestModel = UpdateClientRequestModel(
        name: _nameController.text,
        cnpj: _cnpjMask.getUnmaskedText(),
        urlToPost: _urlToPostController.text,
      );
      ref.read(profileControllerProvider.notifier).updateClient(clientId, requestModel);
    }
  }

  _onUpdateClientPassword(String clientId) {
    if (_updatePasswordFormKey.currentState!.validate()) {
      UpdateClientPasswordRequestModel requestModel = UpdateClientPasswordRequestModel(
        currentPassword: _currentPasswordController.text, 
        newPassword: _newPasswordController.text, 
        confirmNewPassword: _confirmPasswordController.text
      );

      ref.read(profileControllerProvider.notifier).updateClientPassword(clientId, requestModel);
    }
  }

  @override
  void dispose() {
    _currentPasswordController.dispose();
    _newPasswordController.dispose();
    _confirmPasswordController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final jwtData = ref.watch(jwtDataProvider).value;

    if (jwtData == null) return const Scaffold(body: Center(child: CircularProgressIndicator()));

    final authUserRole = jwtData.role;
    final authUserId = jwtData.id;

    // Se for um usuário comum, mostra uma UI diferente.
    if (authUserRole == 'CLIENT_USER') {
      return const Scaffold(
        body: SafeArea(
          child: Padding(
            padding: EdgeInsets.all(24.0),
            child: Text('Área do Usuário do Cliente - Em construção.'),
          ),
        ),
      );
    }

    ref.listen(profileControllerProvider, (_, state) {
      state.whenOrNull(
        error: (message) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(message), backgroundColor: Colors.red),
          );
        },
        success: (message, action) {
          ScaffoldMessenger.of(context).showSnackBar(
            SnackBar(content: Text(message), backgroundColor: Colors.green),
          );

          if(action == ProfileAction.updatePassword) {
            // Fecha a BottomSheet
            Navigator.of(context).pop();
          }
        },
      );
    });

    final clientDataAsync = ref.watch(clientDataProvider(authUserId));
    final profileState = ref.watch(profileControllerProvider);
    final isUpdatingProfile = profileState.maybeWhen(
      loading: (action) => action == ProfileAction.updateClient,
      orElse: () => false,
    );
    final isUpdatingPassword = profileState.maybeWhen(
      loading: (action) => action == ProfileAction.updatePassword,
      orElse: () => false,
    );

    return Scaffold(
      body: SafeArea(
        child: clientDataAsync.when(
          loading: () => const Center(child: CircularProgressIndicator()),
          error: (err, stack) => Center(child: Text('Erro ao carregar perfil: $err')),
          data: (client) {
            // Popula os controllers com os dados da API na primeira construção.
            if (!_areControllersInitialized) {
              _initializeControllers(client);
            }

            // LayoutBuilder para garantir que os cards tenham a mesma largura
            return LayoutBuilder(
              builder: (context, constraints) {
                return SingleChildScrollView(
                  padding: const EdgeInsets.all(24.0),
                  child: Column(
                    children: [
                      // O formulário agora é um widget separado que recebe os dados.
                      Card(
                        elevation: 4,
                        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
                        child: Padding(
                          padding: const EdgeInsets.all(24.0),
                          child: Column(
                            children: [
                              const Text("Atualizar informações do perfil", style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold)),
                              const SizedBox(height: 16),
                              // A página agora passa os dados diretamente para o formulário "burro".
                              UpdateProfileClientForm(
                                formKey: _profileFormKey,
                                nameController: _nameController,
                                cnpjController: _cnpjController,
                                urlToPostController: _urlToPostController,
                                onUpdateInfo: () => _updateClientInfo(client.id),
                                cnpjMask: _cnpjMask,
                                isLoading: isUpdatingProfile,
                              ),
                            ],
                          ),
                        ),
                      ),
                      const SizedBox(height: 16),
                      // Card para atualização de senha.
                      SizedBox(
                        width: constraints.maxWidth, // Garante a mesma largura
                        child: Card(
                          elevation: 4,
                          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
                          child: Padding(
                            padding: const EdgeInsets.all(24.0),
                            child: ElevatedButton(
                              onPressed: () {
                                showModalBottomSheet(
                                  context: context,
                                  // Permite que a sheet cresça e não seja coberta pelo teclado
                                  isScrollControlled: true,
                                  // Cantos arredondados
                                  shape: const RoundedRectangleBorder(
                                    borderRadius: BorderRadius.vertical(top: Radius.circular(20)),
                                  ),
                                  builder: (context) {
                                    return UpdatePasswordForm(
                                      formKey: _updatePasswordFormKey,
                                      currentPasswordController: _currentPasswordController,
                                      newPasswordController: _newPasswordController,
                                      confirmPasswordController: _confirmPasswordController,
                                      onUpdateClientPassword: () => _onUpdateClientPassword(client.id),
                                      isLoading: isUpdatingPassword
                                    );
                                  },
                                );
                              },
                              child: const Text('Atualizar senha de acesso', style: TextStyle(fontSize: 16)),
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                );
              },
            );
          },
        ),
      ),
    );
  }
}