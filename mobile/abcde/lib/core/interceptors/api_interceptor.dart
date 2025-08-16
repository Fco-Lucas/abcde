import 'package:dio/dio.dart';

import '../errors/api_exception.dart'; // Importa nossa exceção

class CustomInterceptor extends Interceptor {
  // O onError é chamado automaticamente pelo Dio quando uma requisição falha
  @override
  void onError(DioException err, ErrorInterceptorHandler handler) {
    // 1. Verificamos se o erro tem uma resposta da API
    if (err.response != null) {
      final responseData = err.response!.data;
      String? apiErrorMessage;

      // 2. Verificamos se a resposta contém nossa chave 'message'
      if (responseData is Map<String, dynamic> && responseData.containsKey('message')) {
        apiErrorMessage = responseData['message'];
      }
      
      // 3. Criamos nossa exceção personalizada com a mensagem da API
      final customException = ApiException(
        requestOptions: err.requestOptions,
        response: err.response,
        message: apiErrorMessage, // Passa a mensagem da API ou o padrão
      );
      
      // 4. Passamos nossa exceção personalizada para frente
      return handler.next(customException);
    }
    
    // Se não houver resposta, apenas passamos o erro original para frente
    super.onError(err, handler);
  }
}