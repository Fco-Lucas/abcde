// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'permission_response_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_PermissionResponseModel _$PermissionResponseModelFromJson(
  Map<String, dynamic> json,
) => _PermissionResponseModel(
  id: (json['id'] as num).toInt(),
  upload_files: json['upload_files'] as String,
  read_files: json['read_files'] as String,
);

Map<String, dynamic> _$PermissionResponseModelToJson(
  _PermissionResponseModel instance,
) => <String, dynamic>{
  'id': instance.id,
  'upload_files': instance.upload_files,
  'read_files': instance.read_files,
};
