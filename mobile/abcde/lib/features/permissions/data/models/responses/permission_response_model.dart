import 'package:freezed_annotation/freezed_annotation.dart';

part 'permission_response_model.freezed.dart';
part 'permission_response_model.g.dart';

@freezed
abstract class PermissionResponseModel with _$PermissionResponseModel {
  const factory PermissionResponseModel({
    required int id,
    required String upload_files,
    required String read_files,
  }) = _PermissionResponseModel;

  // IMPORTANTE: Para enviar dados, precisamos do método toJson().
  // O Freezed gera isso para nós com este factory. Ele converte
  // nosso objeto Dart em um Map<String, dynamic>, que é o que o Dio espera.
  factory PermissionResponseModel.fromJson(Map<String, dynamic> json) =>
      _$PermissionResponseModelFromJson(json);
}