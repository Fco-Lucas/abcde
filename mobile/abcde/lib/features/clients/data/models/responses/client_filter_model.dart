import 'package:abcde/features/clients/data/models/enums/client_status_enum.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'client_filter_model.freezed.dart';

@freezed
abstract class ClientFilterModel with _$ClientFilterModel {
  const factory ClientFilterModel({
    String? cnpj,
    ClientStatus? status,
  }) = _ClientFilterModel;
}