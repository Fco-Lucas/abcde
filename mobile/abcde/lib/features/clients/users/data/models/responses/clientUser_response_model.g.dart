// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'clientUser_response_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_ClientuserResponseModel _$ClientuserResponseModelFromJson(
  Map<String, dynamic> json,
) => _ClientuserResponseModel(
  id: json['id'] as String,
  clientId: json['clientId'] as String,
  name: json['name'] as String,
  string: json['string'] as String,
  permission: PermissionResponseModel.fromJson(
    json['permission'] as Map<String, dynamic>,
  ),
  status: $enumDecode(_$ClientUserStatusEnumMap, json['status']),
);

Map<String, dynamic> _$ClientuserResponseModelToJson(
  _ClientuserResponseModel instance,
) => <String, dynamic>{
  'id': instance.id,
  'clientId': instance.clientId,
  'name': instance.name,
  'string': instance.string,
  'permission': instance.permission,
  'status': _$ClientUserStatusEnumMap[instance.status]!,
};

const _$ClientUserStatusEnumMap = {
  ClientUserStatus.ACTIVE: 'ACTIVE',
  ClientUserStatus.INACTIVE: 'INACTIVE',
};
