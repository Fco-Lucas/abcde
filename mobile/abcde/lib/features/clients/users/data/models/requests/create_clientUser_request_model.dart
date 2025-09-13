import 'package:freezed_annotation/freezed_annotation.dart';

part 'create_clientUser_request_model.freezed.dart';
part 'create_clientUser_request_model.g.dart';

@freezed
abstract class CreateClientUserRequestModel with _$CreateClientUserRequestModel {
  const factory CreateClientUserRequestModel({
    required String clientId,
    required String name,
    required String email,
    required String password,
    required int permission,
  }) = _CreateClientUserRequestModel;

  factory CreateClientUserRequestModel.fromJson(Map<String, dynamic> json) =>
      _$CreateClientUserRequestModelFromJson(json);
}