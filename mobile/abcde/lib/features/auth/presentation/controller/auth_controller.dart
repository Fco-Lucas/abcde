
import 'package:abcde/features/auth/data/auth_repository.dart';
import 'package:abcde/features/auth/presentation/controller/auth_state.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'auth_controller.g.dart';

@riverpod
class AuthController extends _$AuthController {
  @override
  AuthState build() {
    _checkAuthStatus();
    return const AuthState.initial();
  }

  Future<void> _checkAuthStatus() async {
    final authRepository = ref.read(authRepositoryProvider);
    final token = await authRepository.getToken();
    if (token != null) {
      state = const AuthState.authenticated();
    } else {
      state = const AuthState.unauthenticated();
    }
  }

  // Método para fazer logout
  Future<void> logout() async {
    final authRepository = ref.read(authRepositoryProvider);
    await authRepository.logout(); // Deleta o token
    state = const AuthState.unauthenticated(); // Atualiza o estado
  }
}