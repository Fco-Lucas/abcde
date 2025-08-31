import 'package:abcde/core/providers/dio_provider.dart';
import 'package:abcde/features/clients/data/enums/client_status_enum.dart';
import 'package:abcde/features/clients/data/models/client_pageable_response_model.dart';
import 'package:abcde/features/clients/data/models/client_response_model.dart';
import 'package:abcde/features/clients/data/models/create_client_request_model.dart';
import 'package:abcde/features/clients/data/models/update_client_password_request_model.dart';
import 'package:abcde/features/clients/data/models/update_client_request_model.dart';
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

  Future<ClientPageableResponseModel> getAllPageable({
    required int page,
    required int size,
    String? cnpj,
    ClientStatus? status,
  }) async {
    final queryParameters = {
      'page': page,
      'size': size,
      'cnpj': cnpj,
      'status': status,
    };

    try {
      final response = await _dio.get(
        "/clients",
        queryParameters: queryParameters,
      );

      return ClientPageableResponseModel.fromJson(response.data);
    } catch (e) {
      // Fallback para outros tipos de erro.
      rethrow;
    }
  }

  Future<ClientResponseModel> getClientById(String clientId) async {
    try {
      final response = await _dio.get(
        "/clients/$clientId",
      );

      final dataResponse = ClientResponseModel.fromJson(response.data);

      return dataResponse;
    } catch (e) {
      rethrow;
    }
  }

  Future<ClientResponseModel> getClientByCnpj(String clientCnpj) async {
    try {
      final response = await _dio.get(
        "/clients/getByCnpj/$clientCnpj",
      );

      final dataResponse = ClientResponseModel.fromJson(response.data);

      return dataResponse;
    } catch (e) {
      rethrow;
    }
  }

  Future<void> updateClient(String clientId, UpdateClientRequestModel data) async {
    try {
      await _dio.patch(
        "/clients/$clientId",
        data: data.toJson()
      );
    } catch (e) {
      rethrow;
    }
  }

  Future<void> updateClientPassword(String clientId, UpdateClientPasswordRequestModel data) async {
    try {
      await _dio.post(
        "/clients/updatePassword/$clientId",
        data: data.toJson()
      );
    } catch (e) {
      rethrow;
    }
  }
}
