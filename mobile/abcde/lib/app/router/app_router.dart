import 'package:abcde/app/widgets/main_scaffold.dart';
import 'package:abcde/features/audit/presentation/view/audit_page.dart';
import 'package:abcde/features/auth/presentation/controller/auth_controller.dart';
import 'package:abcde/features/auth/presentation/controller/auth_state.dart';
import 'package:abcde/features/auth/presentation/view/login_page.dart';
import 'package:abcde/features/auth/presentation/view/register_page.dart';
import 'package:abcde/features/clients/presentation/view/clients_page.dart';
import 'package:abcde/features/clients/users/presentation/view/client_users_page.dart';
import 'package:abcde/features/home/presentation/view/home_page.dart';
import 'package:abcde/features/profile/presentation/view/profile_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'app_router.g.dart';

@riverpod
GoRouter router(Ref ref) {
  final authState = ref.watch(authControllerProvider);

  return GoRouter(
    initialLocation: '/login',
    redirect: (BuildContext context, GoRouterState state) {
      final status = authState.when(
        initial: () => 'loading',
        authenticated: () => 'loggedIn',
        unauthenticated: () => 'loggedOut',
      );

      final isLoggedIn = status == 'loggedIn';
      final isLoading = status == 'loading';

      // Se o estado ainda está sendo verificado, não fazemos nada.
      if (isLoading) {
        return null;
      }

      final isAuthRoute = state.matchedLocation == '/login' || state.matchedLocation == '/register';

      // Se o usuário está logado e tentando ir para a tela de login,
      // o levamos para a home.
      if (isLoggedIn && isAuthRoute) {
        return '/home';
      }

      // Se o usuário NÃO está logado e tentando acessar qualquer
      // rota protegida, o levamos para o login.
      if (!isLoggedIn && !isAuthRoute) {
        return '/login';
      }

      // Em todos os outros casos, ele pode ir para onde queria.
      return null;
    },
    routes: [
      // Rotas públicas (fora da ShellRoute)
      GoRoute(path: '/login', builder: (context, state) => const LoginPage()),
      GoRoute(path: '/register', builder: (context, state) => const RegisterPage()),

      // Rotas privadas (dentro da ShellRoute)
      ShellRoute(
        builder: (context, state, child) => MainScaffold(child: child),
        // As rotas filhas são as telas que serão exibidas dentro do MainScaffold.
        routes: [
          GoRoute(path: '/home', builder: (context, state) => const HomePage()),
          GoRoute(path: '/clients', builder: (context, state) => const ClientsPage()),
          GoRoute(
            path: '/clientUsers/:clientId',
            builder: (context, state) {
              final clientId = state.pathParameters['clientId']!;
              return ClientUsersPage(clientId: clientId);
            },
          ),
          GoRoute(path: '/audit', builder: (context, state) => const AuditPage()),
          GoRoute(path: '/profile', builder: (context, state) => const ProfilePage()),
        ],
      ),
    ],
  );
}