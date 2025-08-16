import 'package:flutter/material.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'theme_controller.g.dart';

@riverpod
class ThemeController extends _$ThemeController {
  // Estado inicial é o tema do sistema.
  @override
  ThemeMode build() {
    return ThemeMode.system;
  }

  // Método para alterar o tema.
  void setThemeMode(ThemeMode mode) {
    state = mode;
  }
}
