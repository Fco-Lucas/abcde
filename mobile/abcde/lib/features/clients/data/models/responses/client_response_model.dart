import 'package:abcde/features/clients/data/enums/client_status_enum.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'client_response_model.freezed.dart';
part 'client_response_model.g.dart';

@freezed
abstract class ClientResponseModel with _$ClientResponseModel {
  const factory ClientResponseModel({
    required String id,
    required String name,
    required String cnpj,
    String? urlToPost,
    required int imageActiveDays,
    required ClientStatus status,
    required List users
  }) = _ClientResponseModel;

  // IMPORTANTE: Para enviar dados, precisamos do método toJson().
  // O Freezed gera isso para nós com este factory. Ele converte
  // nosso objeto Dart em um Map<String, dynamic>, que é o que o Dio espera.
  factory ClientResponseModel.fromJson(Map<String, dynamic> json) =>
      _$ClientResponseModelFromJson(json);
}