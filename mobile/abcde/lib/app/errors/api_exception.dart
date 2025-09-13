import 'package:dio/dio.dart';

class ApiException extends DioException {
  @override
  final String message;

  ApiException({
    required RequestOptions requestOptions,
    Response? response,
    required this.message,
    DioException? originalError,
  }) : super(
      requestOptions: requestOptions,
      response: response,
      message: message,
      error: originalError,
    );
}