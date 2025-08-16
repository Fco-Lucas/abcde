import 'package:abcde/features/clients/data/client_repository.dart';
import 'package:abcde/features/clients/data/models/client_response_model.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'client_data_provider.g.dart';

@riverpod
Future<ClientResponseModel> clientData(Ref ref, String clientId) async {
  final clientRepository = ref.watch(clientRepositoryProvider);
  return clientRepository.getClientById(clientId);
}