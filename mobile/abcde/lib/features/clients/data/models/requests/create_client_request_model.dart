import 'package:freezed_annotation/freezed_annotation.dart';

part 'create_client_request_model.freezed.dart';
part 'create_client_request_model.g.dart';

@freezed
abstract class CreateClientRequestModel with _$CreateClientRequestModel {
  const factory CreateClientRequestModel({
    required String name,
    required String cnpj,
    required String password,
    required String urlToPost,
    required int imageActiveDays
  }) = _CreateClientRequestModel;

  // IMPORTANTE: Para enviar dados, precisamos do método toJson().
  // O Freezed gera isso para nós com este factory. Ele converte
  // nosso objeto Dart em um Map<String, dynamic>, que é o que o Dio espera.
  factory CreateClientRequestModel.fromJson(Map<String, dynamic> json) =>
      _$CreateClientRequestModelFromJson(json);
}