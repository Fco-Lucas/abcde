// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'client_response_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_ClientResponseModel _$ClientResponseModelFromJson(Map<String, dynamic> json) =>
    _ClientResponseModel(
      id: json['id'] as String,
      name: json['name'] as String,
      cnpj: json['cnpj'] as String,
      urlToPost: json['urlToPost'] as String,
      imageActiveDays: (json['imageActiveDays'] as num).toInt(),
      status: $enumDecode(_$ClientStatusEnumMap, json['status']),
      users: json['users'] as List<dynamic>,
    );

Map<String, dynamic> _$ClientResponseModelToJson(
  _ClientResponseModel instance,
) => <String, dynamic>{
  'id': instance.id,
  'name': instance.name,
  'cnpj': instance.cnpj,
  'urlToPost': instance.urlToPost,
  'imageActiveDays': instance.imageActiveDays,
  'status': _$ClientStatusEnumMap[instance.status]!,
  'users': instance.users,
};

const _$ClientStatusEnumMap = {
  ClientStatus.active: 'ACTIVE',
  ClientStatus.inactive: 'INACTIVE',
};
