import 'package:abcde/features/clients/users/data/enums/clientUser_status_enum.dart';
import 'package:abcde/features/permissions/data/models/responses/permission_response_model.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'clientUser_response_model.freezed.dart';
part 'clientUser_response_model.g.dart';

@freezed
abstract class ClientuserResponseModel with _$ClientuserResponseModel {
  const factory ClientuserResponseModel({
    required String id,
    required String clientId,
    required String name,
    required String string,
    required PermissionResponseModel permission,
    required ClientUserStatus status
  }) = _ClientuserResponseModel;

  // IMPORTANTE: Para enviar dados, precisamos do método toJson().
  // O Freezed gera isso para nós com este factory. Ele converte
  // nosso objeto Dart em um Map<String, dynamic>, que é o que o Dio espera.
  factory ClientuserResponseModel.fromJson(Map<String, dynamic> json) =>
      _$ClientuserResponseModelFromJson(json);
}