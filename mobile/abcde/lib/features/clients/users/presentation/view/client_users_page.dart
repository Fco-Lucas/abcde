import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class ClientUsersPage extends ConsumerStatefulWidget {
  final String clientId;
  
  const ClientUsersPage({
    super.key,
    required this.clientId
  });

  @override
  ConsumerState<ClientUsersPage> createState() => _ClientUsersPageState();
}

class _ClientUsersPageState extends ConsumerState<ClientUsersPage> {
  @override
  Widget build(BuildContext context) {
    return const Placeholder();
  }
}