// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'update_client_password_request_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_UpdateClientPasswordRequestModel _$UpdateClientPasswordRequestModelFromJson(
  Map<String, dynamic> json,
) => _UpdateClientPasswordRequestModel(
  currentPassword: json['currentPassword'] as String,
  newPassword: json['newPassword'] as String,
  confirmNewPassword: json['confirmNewPassword'] as String,
);

Map<String, dynamic> _$UpdateClientPasswordRequestModelToJson(
  _UpdateClientPasswordRequestModel instance,
) => <String, dynamic>{
  'currentPassword': instance.currentPassword,
  'newPassword': instance.newPassword,
  'confirmNewPassword': instance.confirmNewPassword,
};
