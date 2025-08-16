import 'package:abcde/features/auth/presentation/controller/auth_controller.dart';
import 'package:abcde/features/auth/presentation/controller/auth_state.dart';
import 'package:abcde/features/auth/presentation/view/login_page.dart';
import 'package:abcde/features/auth/presentation/view/register_page.dart';
import 'package:abcde/features/home/presentation/view/home_page.dart';
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
      GoRoute(
        path: '/login',
        builder: (context, state) => const LoginPage(),
      ),

      GoRoute(
        path: '/register',
        builder: (context, state) => const RegisterPage(),
      ),

      // Rotas protegidas
      GoRoute(
        path: '/home',
        builder: (context, state) => const HomePage(),
      ),
    ],
  );
}