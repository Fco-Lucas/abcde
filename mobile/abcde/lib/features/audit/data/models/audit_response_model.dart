import 'package:abcde/features/audit/data/enums/audit_action_enum.dart';
import 'package:abcde/features/audit/data/enums/audit_program_enum.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'audit_response_model.freezed.dart';
part 'audit_response_model.g.dart';

@freezed
abstract class AuditResponseModel with _$AuditResponseModel {
  const factory AuditResponseModel({
    required int id,
    required AuditActionEnum action,
    required String clientId,
    required String clientName,
    String? userId,
    required String userName,
    required AuditProgramEnum program,
    required String details,
    required String createdAt,
  }) = _AuditResponseModel;

  factory AuditResponseModel.fromJson(Map<String, dynamic> json) =>
      _$AuditResponseModelFromJson(json);
}