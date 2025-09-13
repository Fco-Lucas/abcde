// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'create_client_request_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_CreateClientRequestModel _$CreateClientRequestModelFromJson(
  Map<String, dynamic> json,
) => _CreateClientRequestModel(
  name: json['name'] as String,
  cnpj: json['cnpj'] as String,
  password: json['password'] as String,
  urlToPost: json['urlToPost'] as String,
  imageActiveDays: (json['imageActiveDays'] as num).toInt(),
);

Map<String, dynamic> _$CreateClientRequestModelToJson(
  _CreateClientRequestModel instance,
) => <String, dynamic>{
  'name': instance.name,
  'cnpj': instance.cnpj,
  'password': instance.password,
  'urlToPost': instance.urlToPost,
  'imageActiveDays': instance.imageActiveDays,
};
