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
import 'package:abcde/features/splash/splash_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'app_router.g.dart';

@riverpod
GoRouter router(Ref ref) {
  final authState = ref.watch(authControllerProvider);

  return GoRouter(
    initialLocation: '/splash',
    redirect: (BuildContext context, GoRouterState state) {
      final status = authState.when(
        initial: () => 'loading',
        authenticated: () => 'loggedIn',
        unauthenticated: () => 'loggedOut',
      );

      final isLoggedIn = status == 'loggedIn';
      final isLoading = status == 'loading';
      final onSplashPage = state.matchedLocation == '/splash';

      // Se ainda estiver carregando, o usuário deve permanecer na splash page.
      if (isLoading) {
        return onSplashPage ? null : '/splash';
      }

      final onAuthRoute = state.matchedLocation == '/login' || state.matchedLocation == '/register';

      // Se o carregamento terminou e o usuário está logado:
      if (isLoggedIn) {
        // Se ele estava na splash ou em uma rota de auth, vai para a home.
        if (onSplashPage || onAuthRoute) return '/home';
      } 
      // Se o carregamento terminou e o usuário NÃO está logado:
      else {
        // Se ele estava na splash ou em uma rota protegida, vai para o login.
        if (onSplashPage || !onAuthRoute) return '/login';
      }

      // 4. Em todos os outros casos, não faz nada.
      return null;
    },
    routes: [
      // Rotas públicas (fora da ShellRoute)
      GoRoute(path: '/splash', builder: (context, state) => const SplashPage()),
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