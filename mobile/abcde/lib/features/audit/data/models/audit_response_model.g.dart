// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'audit_response_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_AuditResponseModel _$AuditResponseModelFromJson(Map<String, dynamic> json) =>
    _AuditResponseModel(
      id: (json['id'] as num).toInt(),
      action: $enumDecode(_$AuditActionEnumEnumMap, json['action']),
      clientId: json['clientId'] as String,
      clientName: json['clientName'] as String,
      userId: json['userId'] as String?,
      userName: json['userName'] as String,
      program: $enumDecode(_$AuditProgramEnumEnumMap, json['program']),
      details: json['details'] as String,
      createdAt: json['createdAt'] as String,
    );

Map<String, dynamic> _$AuditResponseModelToJson(_AuditResponseModel instance) =>
    <String, dynamic>{
      'id': instance.id,
      'action': _$AuditActionEnumEnumMap[instance.action]!,
      'clientId': instance.clientId,
      'clientName': instance.clientName,
      'userId': instance.userId,
      'userName': instance.userName,
      'program': _$AuditProgramEnumEnumMap[instance.program]!,
      'details': instance.details,
      'createdAt': instance.createdAt,
    };

const _$AuditActionEnumEnumMap = {
  AuditActionEnum.CREATE: 'CREATE',
  AuditActionEnum.UPDATE: 'UPDATE',
  AuditActionEnum.DELETE: 'DELETE',
  AuditActionEnum.LOGIN: 'LOGIN',
  AuditActionEnum.PROCESSED: 'PROCESSED',
  AuditActionEnum.DOWNLOADTXT: 'DOWNLOADTXT',
};

const _$AuditProgramEnumEnumMap = {
  AuditProgramEnum.CLIENT: 'CLIENT',
  AuditProgramEnum.CLIENTUSER: 'CLIENTUSER',
  AuditProgramEnum.LOT: 'LOT',
  AuditProgramEnum.LOTIMAGE: 'LOTIMAGE',
  AuditProgramEnum.AUTH: 'AUTH',
};
