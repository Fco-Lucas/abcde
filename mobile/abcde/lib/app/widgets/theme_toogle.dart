import 'package:abcde/app/theme/theme_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class ThemeToogle extends ConsumerWidget {
  const ThemeToogle({super.key});

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final currentThemeMode = ref.watch(themeControllerProvider);
    return IconButton(
      icon: Icon(
        currentThemeMode == ThemeMode.dark
            ? Icons.light_mode_outlined
            : Icons.dark_mode_outlined,
      ),
      onPressed: () {
        // Lê o controller e chama o método para trocar o tema
        final newTheme = currentThemeMode == ThemeMode.dark
            ? ThemeMode.light
            : ThemeMode.dark;
        ref.read(themeControllerProvider.notifier).setThemeMode(newTheme);
      },
    );
  }
}