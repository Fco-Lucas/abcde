// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'create_clientUser_request_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_CreateClientUserRequestModel _$CreateClientUserRequestModelFromJson(
  Map<String, dynamic> json,
) => _CreateClientUserRequestModel(
  clientId: json['clientId'] as String,
  name: json['name'] as String,
  email: json['email'] as String,
  password: json['password'] as String,
  permission: (json['permission'] as num).toInt(),
);

Map<String, dynamic> _$CreateClientUserRequestModelToJson(
  _CreateClientUserRequestModel instance,
) => <String, dynamic>{
  'clientId': instance.clientId,
  'name': instance.name,
  'email': instance.email,
  'password': instance.password,
  'permission': instance.permission,
};
