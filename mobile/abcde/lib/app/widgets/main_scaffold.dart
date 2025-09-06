import 'package:abcde/app/widgets/theme_toogle.dart';
import 'package:abcde/core/providers/fab_visibility_provider.dart';
import 'package:abcde/core/providers/jwt_data_provider.dart';
import 'package:abcde/core/providers/shell_action_provider.dart';
import 'package:abcde/features/auth/presentation/controller/auth_controller.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

class MainScaffold extends ConsumerWidget {
  const MainScaffold({required this.child, super.key});
  final Widget child;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final jwtDataAsync = ref.watch(jwtDataProvider);
    final jwtData = jwtDataAsync.value;

    if (jwtData == null) {
      return const Scaffold(
        body: Center(
          child: CircularProgressIndicator(),
        ),
      );
    }

    String calculateTitle(BuildContext context) {
      final String location = GoRouterState.of(context).matchedLocation;
      if (location.startsWith('/home')) return 'Início';
      if (location.startsWith('/clients')) return "Clientes";
      if (location.startsWith('/clientUsers')) return 'Usuários do cliente';
      if (location.startsWith('/audit')) return 'Auditoria';
      if (location.startsWith('/profile')) return 'Perfil';
      return 'Início';
    }

    int calculateSelectedIndex(BuildContext context) {
      final String location = GoRouterState.of(context).matchedLocation;
      final role = jwtData.role;

      if (location.startsWith('/home')) return 0;
      if (role == 'COMPUTEX') {
        if (location.startsWith('/clients')) return 1;
        if (location.startsWith('/audit')) return 2;
        if (location.startsWith('/profile')) return 3;
      } else if (role == 'CLIENT') {
        if (location.startsWith('/clientUsers')) return 1;
        if (location.startsWith('/profile')) return 2;
      } else if (role == 'CLIENT_USER') {
        if (location.startsWith('/profile')) return 1;
      }
      return 0;
    }

    void onItemTapped(int index) {
      final role = jwtData.role;
      switch (index) {
        case 0:
          context.go('/home');
          break;
        case 1:
          if (role == 'COMPUTEX') {
            context.go('/clients');
          } else if (role == 'CLIENT') {
            final clientId = jwtData.id;
            context.go('/clientUsers/$clientId');
          } else if (role == 'CLIENT_USER') {
            context.go('/profile');
          }
          break;
        case 2:
          if (role == 'COMPUTEX') {
            context.go('/audit');
          } else if (role == 'CLIENT') {
            context.go('/profile');
          }
          break;
        case 3:
          if (role == 'COMPUTEX') {
            context.go('/profile');
          }
          break;
      }
    }

    List<BottomNavigationBarItem> buildNavItems() {
      // Seu código aqui para construir os itens continua perfeito.
      final role = jwtData.role;
      if (role == 'COMPUTEX') {
        return const [
          BottomNavigationBarItem(icon: Icon(Icons.home_outlined), activeIcon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.business_outlined), activeIcon: Icon(Icons.business), label: 'Clientes'),
          BottomNavigationBarItem(icon: Icon(Icons.bar_chart_outlined), activeIcon: Icon(Icons.bar_chart), label: 'Auditoria'),
          BottomNavigationBarItem(icon: Icon(Icons.person_outline), activeIcon: Icon(Icons.person), label: 'Perfil'),
        ];
      } else if (role == 'CLIENT') {
        return const [
          BottomNavigationBarItem(icon: Icon(Icons.home_outlined), activeIcon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.people_outline), activeIcon: Icon(Icons.people), label: 'Usuários'),
          BottomNavigationBarItem(icon: Icon(Icons.person_outline), activeIcon: Icon(Icons.person), label: 'Perfil'),
        ];
      } else if (role == 'CLIENT_USER') {
        return const [
          BottomNavigationBarItem(icon: Icon(Icons.home_outlined), activeIcon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.person_outline), activeIcon: Icon(Icons.person), label: 'Perfil'),
        ];
      }
      return [];
    }

    Widget? buildFloatingActionButton() {
      final String location = GoRouterState.of(context).matchedLocation;
      final action = ref.watch(shellActionProvider);
      final isFabVisible = ref.watch(fabVisibilityProvider);

      if (location.startsWith('/audit') && isFabVisible && action != null) {
        return FloatingActionButton(
          onPressed: action, // <-- Simplesmente chama a ação do provider
          child: const Icon(Icons.filter_list),
        );
      }

      if (location.startsWith('/clients') && isFabVisible && action != null) {
        return FloatingActionButton(
          onPressed: action, // <-- Simplesmente chama a ação do provider
          child: const Icon(Icons.add),
        );
      }

      return null; // Retorna nulo para todas as outras telas
    }

    return Scaffold(
      appBar: AppBar(
        title: Text(calculateTitle(context)),
        actions: [
          ThemeToogle(),
          IconButton(
            onPressed: () {
              ref.read(authControllerProvider.notifier).logout();
            }, 
            icon: Icon(Icons.logout)
          )
        ],
      ),
      body: child,
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        currentIndex: calculateSelectedIndex(context),
        onTap: onItemTapped,
        items: buildNavItems(),
      ),
      floatingActionButton: buildFloatingActionButton(),
    );
  }
}