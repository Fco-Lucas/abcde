import 'package:abcde/app/router/app_router.dart'; // Corrija o import para o seu caminho
import 'package:abcde/app/theme/app_theme.dart';
import 'package:abcde/app/theme/theme_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

void main() {
  runApp(const ProviderScope(child: MyApp()));
}

class MyApp extends ConsumerWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final router = ref.watch(routerProvider);
    final themeMode = ref.watch(themeControllerProvider);
    
    return MaterialApp.router(
      routerConfig: router,
      title: "ABCDE",
      theme: lightTheme,      // Tema para o modo claro
      darkTheme: darkTheme,   // Tema para o modo escuro
      themeMode: themeMode,   // Diz qual tema usar (claro, escuro ou do sistema)
    );
  }
}