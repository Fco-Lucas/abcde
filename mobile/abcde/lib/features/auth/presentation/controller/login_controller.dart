import 'package:abcde/core/errors/api_exception.dart';
import 'package:abcde/features/auth/data/auth_repository.dart';
import 'package:abcde/features/auth/data/models/login_request_model.dart';
import 'package:abcde/features/auth/presentation/controller/auth_controller.dart';
import 'package:abcde/features/auth/presentation/controller/login_state.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'login_controller.g.dart';

@riverpod
class LoginController extends _$LoginController {
  @override
  LoginState build() {
    return const LoginState.initial();
  }

  Future<void> login(LoginRequestModel requestModel) async {
    state = const LoginState.loading();
    final authRepository = ref.read(authRepositoryProvider);

    try {
      await authRepository.login(requestModel);
      
      // AVISO GLOBAL: Notifica o AuthController para se reavaliar
      ref.invalidate(authControllerProvider);
      
      state = const LoginState.success();
    } on ApiException catch (e) {
      // Agora podemos capturar nossa exceção personalizada!
      state = LoginState.error(e.errorMessage);
    } catch (e) {
      // Um fallback para outros tipos de erro
      state = const LoginState.error('Oops, algo inesperado aconteceu.');
    }
  }
}