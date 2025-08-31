import 'package:abcde/features/audit/data/enums/audit_action_enum.dart';
import 'package:abcde/features/audit/data/enums/audit_program_enum.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'audit_filter_model.freezed.dart';

@freezed
abstract class AuditFilterModel with _$AuditFilterModel {
  const factory AuditFilterModel({
    AuditActionEnum? action,
    String? client,
    String? user,
    AuditProgramEnum? program,
    String? details,
    String? startDate,
    String? endDate,
  }) = _AuditFilterModel;
}