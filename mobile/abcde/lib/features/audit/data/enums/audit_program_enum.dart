import 'package:json_annotation/json_annotation.dart';

enum AuditProgramEnum {
  @JsonValue("CLIENT")
  client,
  @JsonValue("CLIENT_USER")
  clientUser,
  @JsonValue("LOT")
  lot,
  @JsonValue("LOT_IMAGE")
  lotImage,
  @JsonValue("AUTH")
  auth
} 