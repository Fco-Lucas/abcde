import 'package:abcde/core/errors/api_exception.dart';
import 'package:abcde/features/auth/presentation/controller/register_state.dart';
import 'package:abcde/features/clients/data/client_repository.dart';
import 'package:abcde/features/clients/data/models/create_client_request_model.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'register_controller.g.dart';

@riverpod
class RegisterController extends _$RegisterController {
  @override
  RegisterState build() {
    return const RegisterState.initial();
  }

  Future<void> createClient(CreateClientRequestModel requestModel) async {
    state = const RegisterState.loading();
    final clientRepository = ref.read(clientRepositoryProvider);

    try {
      print(requestModel);
      await clientRepository.createClient(requestModel);
      
      // AVISO GLOBAL: Notifica o AuthController para se reavaliar
      ref.invalidate(clientRepositoryProvider);
      
      state = const RegisterState.success();
    } on ApiException catch (e) {
      // Agora podemos capturar nossa exceção personalizada!
      state = RegisterState.error(e.errorMessage);
    } catch (e) {
      // Um fallback para outros tipos de erro
      state = const RegisterState.error('Oops, algo inesperado aconteceu.');
    }
  }
}