import 'package:abcde/features/audit/data/models/audit_response_model.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'audit_pageable_response_model.freezed.dart';
part 'audit_pageable_response_model.g.dart';

@freezed
abstract class AuditPageableResponseModel with _$AuditPageableResponseModel {
  const factory AuditPageableResponseModel({
    required List<AuditResponseModel> content,
    required bool first,
    required bool end,
    required int page,
    required int size,
    required int pageElements,
    required int totalElements,
    required int totalPages,
  }) = _AuditPageableResponseModel;

  factory AuditPageableResponseModel.fromJson(Map<String, dynamic> json) =>
      _$AuditPageableResponseModelFromJson(json);
}