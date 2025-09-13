import 'package:abcde/app/providers/jwt_data_provider.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class HomePage extends ConsumerStatefulWidget {
  const HomePage({super.key});

  @override
  ConsumerState<HomePage> createState() => _HomePageState();
}

class _HomePageState extends ConsumerState<HomePage> {
  @override
  Widget build(BuildContext context) {
    final jwtDataAsyncValue = ref.watch(jwtDataProvider);
    final jwtData = jwtDataAsyncValue.value;

    return Scaffold(
      body: Center(
        child: Text(jwtData.toString()),
      ),
    );
  }
}