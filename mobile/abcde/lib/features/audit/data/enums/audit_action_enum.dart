import 'package:json_annotation/json_annotation.dart';

enum AuditActionEnum {
  @JsonValue("CREATE")
  create,
  @JsonValue("UPDATE")
  update,
  @JsonValue("DELETE")
  delete,
  @JsonValue("LOGIN")
  login,
  @JsonValue("PROCESSED")
  processed,
  @JsonValue("DOWNLOADTXT")
  downloadTxt
} 