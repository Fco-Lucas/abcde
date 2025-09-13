import 'package:freezed_annotation/freezed_annotation.dart';

part 'update_client_request_model.freezed.dart';
part 'update_client_request_model.g.dart';

@freezed
abstract class UpdateClientRequestModel with _$UpdateClientRequestModel {
  const factory UpdateClientRequestModel({
    String? name,
    String? cnpj,
    String? urlToPost,
    int? imageActiveDays
  }) = _UpdateClientRequestModel;

  factory UpdateClientRequestModel.fromJson(Map<String, dynamic> json) =>
      _$UpdateClientRequestModelFromJson(json);
}