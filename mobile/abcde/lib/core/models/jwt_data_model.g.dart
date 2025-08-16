// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'jwt_data_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_JwtDataModel _$JwtDataModelFromJson(Map<String, dynamic> json) =>
    _JwtDataModel(
      subject: json['sub'] as String,
      iat: (json['iat'] as num).toInt(),
      exp: (json['exp'] as num).toInt(),
      id: json['id'] as String,
      role: json['role'] as String,
    );

Map<String, dynamic> _$JwtDataModelToJson(_JwtDataModel instance) =>
    <String, dynamic>{
      'sub': instance.subject,
      'iat': instance.iat,
      'exp': instance.exp,
      'id': instance.id,
      'role': instance.role,
    };
