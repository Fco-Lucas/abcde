// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'update_client_request_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_UpdateClientRequestModel _$UpdateClientRequestModelFromJson(
  Map<String, dynamic> json,
) => _UpdateClientRequestModel(
  name: json['name'] as String?,
  cnpj: json['cnpj'] as String?,
  urlToPost: json['urlToPost'] as String?,
  imageActiveDays: (json['imageActiveDays'] as num?)?.toInt(),
);

Map<String, dynamic> _$UpdateClientRequestModelToJson(
  _UpdateClientRequestModel instance,
) => <String, dynamic>{
  'name': instance.name,
  'cnpj': instance.cnpj,
  'urlToPost': instance.urlToPost,
  'imageActiveDays': instance.imageActiveDays,
};
