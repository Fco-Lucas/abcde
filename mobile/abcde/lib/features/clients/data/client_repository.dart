import 'package:abcde/core/providers/dio_provider.dart';
import 'package:abcde/features/clients/data/models/client_response_model.dart';
import 'package:abcde/features/clients/data/models/create_client_request_model.dart';
import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'client_repository.g.dart';

@riverpod
ClientRepository clientRepository(Ref ref) {
  final dio = ref.watch(dioProvider);
  return ClientRepository(dio);
}

class ClientRepository {
  final Dio _dio;

  ClientRepository(this._dio);

  Future<ClientResponseModel> createClient(CreateClientRequestModel data) async {
    try {
      final response = await _dio.post(
        "/clients",
        data: data.toJson()
      );

      final dataResponse = ClientResponseModel.fromJson(response.data);

      return dataResponse;
    } catch (e) {
      rethrow;
    }
  }
}
