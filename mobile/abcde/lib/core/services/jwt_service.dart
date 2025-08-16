import 'package:abcde/core/models/jwt_data_model.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:jwt_decoder/jwt_decoder.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'jwt_service.g.dart';

@riverpod
JwtService jwtService(Ref ref) {
  return JwtService();
}

class JwtService {
  /// Decodifica um token JWT e retorna um modelo com os dados do payload.
  JwtDataModel decodeToken(String token) {
    final Map<String, dynamic> decodedPayload = JwtDecoder.decode(token);
    return JwtDataModel.fromJson(decodedPayload);
  }
}
