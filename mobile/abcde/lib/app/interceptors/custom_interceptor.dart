import 'package:dio/dio.dart';

import '../errors/api_exception.dart';

class CustomInterceptor extends Interceptor {
  // O onError é chamado automaticamente pelo Dio quando uma requisição falha
  @override
  void onError(DioException err, ErrorInterceptorHandler handler) {
    // Verifica se o erro tem uma resposta da API
    if (err.response != null && err.response!.data != null) {
      final responseData = err.response!.data;
      String apiErrorMessage = 'Ocorreu um erro desconhecido.';

      print("Resposta do servidor: ${responseData}");

      // Verifica se a resposta contém a chave 'message' no JSON recebido
      if (responseData is Map<String, dynamic> && responseData.containsKey('message')) {
        apiErrorMessage = responseData['message'];
      }
      
      // Cria a exceção personalizada com a mensagem da API
      final customException = ApiException(
        requestOptions: err.requestOptions,
        response: err.response,
        message: apiErrorMessage,
        originalError: err,
      );
      
      // Passa a exceção personalizada para frente
      return handler.next(customException);
    }
    
    // Se não houver resposta, apenas passa o erro original para frente
    super.onError(err, handler);
  }
}