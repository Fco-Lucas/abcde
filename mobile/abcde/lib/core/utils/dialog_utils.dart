import 'package:abcde/core/providers/navigator_key_provider.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class DialogUtils {
  // Construtor privado para que ninguém possa instanciar esta classe
  DialogUtils._();

  /// Exibe um diálogo de carregamento modal.
  /// Opcionalmente, exibe uma mensagem.
  /// NÃO PODE SER FECHADO PELO USUÁRIO.
  static void showLoadingDialog(WidgetRef ref, {String text = 'Carregando...'}) {
    final context = ref.read(navigatorKeyProvider).currentContext;
    if (context == null) return; // Segurança caso o contexto não esteja pronto

    showDialog(
      context: context,
      // Impede que o usuário feche o diálogo tocando fora
      barrierDismissible: false,
      builder: (BuildContext context) {
        return PopScope( // Impede o botão de voltar do Android de fechar
          canPop: false,
          child: Dialog(
            child: Padding(
              padding: const EdgeInsets.all(20.0),
              child: Row(
                mainAxisSize: MainAxisSize.min,
                children: [
                  const CircularProgressIndicator(),
                  const SizedBox(width: 20),
                  Text(text),
                ],
              ),
            ),
          ),
        );
      },
    );
  }

  /// Fecha o diálogo de carregamento que está aberto.
  static void hideLoadingDialog(WidgetRef ref) {
    final context = ref.read(navigatorKeyProvider).currentContext;
    if (context != null && Navigator.of(context).canPop()) {
      Navigator.of(context).pop();
    }
  }

  /// Exibe um diálogo de confirmação genérico.
  /// Retorna `true` se o usuário confirmar, `false` ou `null` caso contrário.
  static Future<bool?> showConfirmationDialog({
    required BuildContext context,
    required String title,
    required String content,
    String confirmText = 'CONFIRMAR',
    String cancelText = 'CANCELAR',
  }) {
    return showDialog<bool>(
      context: context,
      barrierDismissible: false,
      builder: (BuildContext context) {
        final theme = Theme.of(context);
        return AlertDialog(
          title: Text(title),
          content: Text(content),
          actions: <Widget>[
            TextButton(
              child: Text(cancelText),
              onPressed: () {
                Navigator.of(context).pop(false); // Retorna false ao cancelar
              },
            ),
            TextButton(
              style: TextButton.styleFrom(foregroundColor: theme.colorScheme.error),
              child: Text(confirmText),
              onPressed: () {
                Navigator.of(context).pop(true); // Retorna true ao confirmar
              },
            ),
          ],
        );
      },
    );
  }
}
