import 'package:flutter/material.dart';

class SplashPage extends StatelessWidget {
  const SplashPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Material(
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            // Substitua pelo caminho da sua logo
            Image.asset('assets/images/app_icon.png', width: 150),
            SizedBox(height: 32),
            CircularProgressIndicator(),
          ],
        ),
      ),
    );
  }
}