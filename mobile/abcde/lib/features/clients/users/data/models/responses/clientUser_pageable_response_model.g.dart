// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'clientUser_pageable_response_model.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

_ClientuserPageableResponseModel _$ClientuserPageableResponseModelFromJson(
  Map<String, dynamic> json,
) => _ClientuserPageableResponseModel(
  content: (json['content'] as List<dynamic>)
      .map((e) => ClientuserResponseModel.fromJson(e as Map<String, dynamic>))
      .toList(),
  first: json['first'] as bool,
  end: json['end'] as bool,
  page: (json['page'] as num).toInt(),
  size: (json['size'] as num).toInt(),
  pageElements: (json['pageElements'] as num).toInt(),
  totalElements: (json['totalElements'] as num).toInt(),
  totalPages: (json['totalPages'] as num).toInt(),
);

Map<String, dynamic> _$ClientuserPageableResponseModelToJson(
  _ClientuserPageableResponseModel instance,
) => <String, dynamic>{
  'content': instance.content,
  'first': instance.first,
  'end': instance.end,
  'page': instance.page,
  'size': instance.size,
  'pageElements': instance.pageElements,
  'totalElements': instance.totalElements,
  'totalPages': instance.totalPages,
};
