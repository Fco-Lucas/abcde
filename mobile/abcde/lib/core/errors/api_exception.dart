import 'package:dio/dio.dart';

class ApiException extends DioException {
  final String errorMessage;

  ApiException({
    required RequestOptions requestOptions,
    Response? response,
    String? message,
  })  : errorMessage = message ?? 'Ocorreu um erro inesperado.',
        super(requestOptions: requestOptions, response: response);

  @override
  String get message => errorMessage;
}