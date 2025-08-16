import 'package:freezed_annotation/freezed_annotation.dart';

part 'update_client_password_request_model.freezed.dart';
part 'update_client_password_request_model.g.dart';

@freezed
abstract class UpdateClientPasswordRequestModel with _$UpdateClientPasswordRequestModel {
  const factory UpdateClientPasswordRequestModel({
    required String currentPassword,
    required String newPassword,
    required String confirmNewPassword
  }) = _UpdateClientPasswordRequestModel;

  // IMPORTANTE: Para enviar dados, precisamos do método toJson().
  // O Freezed gera isso para nós com este factory. Ele converte
  // nosso objeto Dart em um Map<String, dynamic>, que é o que o Dio espera.
  factory UpdateClientPasswordRequestModel.fromJson(Map<String, dynamic> json) =>
      _$UpdateClientPasswordRequestModelFromJson(json);
}