
import 'package:freezed_annotation/freezed_annotation.dart';

part 'jwt_data_model.freezed.dart';
part 'jwt_data_model.g.dart';

@freezed
abstract class JwtDataModel with _$JwtDataModel {
  const factory JwtDataModel({
    @JsonKey(name: 'sub') required String subject,
    required int iat, // Issued At
    required int exp, // Expiration Time
    required String id,
    required String role,
  }) = _JwtDataModel;

  factory JwtDataModel.fromJson(Map<String, dynamic> json) =>
      _$JwtDataModelFromJson(json);
}