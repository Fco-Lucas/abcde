import 'package:abcde/features/clients/data/models/client_response_model.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'client_pageable_response_model.freezed.dart';
part 'client_pageable_response_model.g.dart';

@freezed
abstract class ClientPageableResponseModel with _$ClientPageableResponseModel {
  const factory ClientPageableResponseModel({
    required List<ClientResponseModel> content,
    required bool first,
    required bool end,
    required int page,
    required int size,
    required int pageElements,
    required int totalElements,
    required int totalPages,
  }) = _ClientPageableResponseModel;

  factory ClientPageableResponseModel.fromJson(Map<String, dynamic> json) =>
      _$ClientPageableResponseModelFromJson(json);
}