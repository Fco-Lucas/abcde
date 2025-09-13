import 'package:flutter/material.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'shell_action_provider.g.dart';

// Este provider guarda a função do Floating Action Button da Shell.
// A página ativa (ex: AuditPage) será responsável por definir essa função.
@riverpod
class ShellAction extends _$ShellAction {
  @override
  VoidCallback? build() {
    return null; // A ação é nula por padrão
  }

  void setAction(VoidCallback? action) {
    state = action;
  }
}