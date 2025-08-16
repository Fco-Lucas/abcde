import 'package:abcde/core/providers/jwt_data_provider.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';

class MainScaffold extends ConsumerWidget {
  const MainScaffold({required this.child, super.key});
  final Widget child;

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final jwtData = ref.watch(jwtDataProvider).value;

    int calculateSelectedIndex(BuildContext context) {
      final String location = GoRouterState.of(context).matchedLocation;
      final role = jwtData?.role;

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
      final role = jwtData?.role;
      switch (index) {
        case 0:
          context.go('/home');
          break;
        case 1:
          if (role == 'COMPUTEX') {
            context.go('/clients');
          } else if (role == 'CLIENT') {
            final clientId = jwtData?.id;
            if (clientId != null) {
              context.go('/clientUsers/$clientId');
            }
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
      final role = jwtData?.role;
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

    return Scaffold(
      body: child,
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        currentIndex: calculateSelectedIndex(context),
        onTap: onItemTapped,
        items: buildNavItems(),
      ),
    );
  }
}