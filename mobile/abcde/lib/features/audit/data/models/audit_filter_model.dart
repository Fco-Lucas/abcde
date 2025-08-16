import 'package:freezed_annotation/freezed_annotation.dart';

part 'audit_filter_model.freezed.dart';

@freezed
abstract class AuditFilterModel with _$AuditFilterModel {
  const factory AuditFilterModel({
    String? user,
    String? details,
  }) = _AuditFilterModel;
}