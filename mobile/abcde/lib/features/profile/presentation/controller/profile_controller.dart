import 'package:abcde/app/errors/api_exception.dart';
import 'package:abcde/features/clients/data/repositories/client_repository.dart';
import 'package:abcde/features/clients/data/models/requests/update_client_password_request_model.dart';
import 'package:abcde/features/clients/data/models/requests/update_client_request_model.dart';
import 'package:abcde/features/profile/presentation/controller/profile_action_enum.dart';
import 'package:abcde/features/profile/presentation/controller/profile_state.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'profile_controller.g.dart';

@riverpod
class ProfileController extends _$ProfileController {
  @override
  ProfileState build() {
    return const ProfileState.initial();
  }

  Future<void> updateClient(String clientId, UpdateClientRequestModel requestModel) async {
    state = const ProfileState.loading(ProfileAction.updateClient);
    final clientRepository = ref.read(clientRepositoryProvider);

    try {
      await clientRepository.updateClient(clientId, requestModel);
      
      state = const ProfileState.success("Informações atualizadas com sucesso!", ProfileAction.updateClient);
    } on ApiException catch (e) {
      state = ProfileState.error(e.message);
    } catch (e) {
      state = const ProfileState.error('Oops, algo inesperado aconteceu.');
    }
  }

  Future<void> updateClientPassword(String clientId, UpdateClientPasswordRequestModel requestModel) async {
    state = const ProfileState.loading(ProfileAction.updatePassword);
    final clientRepository = ref.read(clientRepositoryProvider);

    try {
      await clientRepository.updateClientPassword(clientId, requestModel);
      
      state = const ProfileState.success("Senha atualizada com sucesso!", ProfileAction.updatePassword);
    } on ApiException catch (e) {
      state = ProfileState.error(e.message);
    } catch (e) {
      state = const ProfileState.error('Oops, algo inesperado aconteceu.');
    }
  }
}