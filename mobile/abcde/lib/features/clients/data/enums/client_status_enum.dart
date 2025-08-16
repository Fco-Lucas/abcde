import 'package:json_annotation/json_annotation.dart';

enum ClientStatus {
  @JsonValue("ACTIVE")
  active,
  @JsonValue("INACTIVE")
  inactive
} 