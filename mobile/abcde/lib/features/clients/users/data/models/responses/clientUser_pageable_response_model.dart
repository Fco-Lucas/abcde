import 'package:abcde/features/clients/users/data/models/responses/clientUser_response_model.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'clientUser_pageable_response_model.freezed.dart';
part 'clientUser_pageable_response_model.g.dart';

@freezed
abstract class ClientuserPageableResponseModel with _$ClientuserPageableResponseModel {
  const factory ClientuserPageableResponseModel({
    required List<ClientuserResponseModel> content,
    required bool first,
    required bool end,
    required int page,
    required int size,
    required int pageElements,
    required int totalElements,
    required int totalPages,
  }) = _ClientuserPageableResponseModel;

  factory ClientuserPageableResponseModel.fromJson(Map<String, dynamic> json) =>
      _$ClientuserPageableResponseModelFromJson(json);
}