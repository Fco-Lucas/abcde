// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'client_data_provider.dart';

// **************************************************************************
// RiverpodGenerator
// **************************************************************************

String _$clientDataHash() => r'dc4ff4fa9f7802f3eafa21ae14b05c2a3d7638da';

/// Copied from Dart SDK
class _SystemHash {
  _SystemHash._();

  static int combine(int hash, int value) {
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + value);
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + ((0x0007ffff & hash) << 10));
    return hash ^ (hash >> 6);
  }

  static int finish(int hash) {
    // ignore: parameter_assignments
    hash = 0x1fffffff & (hash + ((0x03ffffff & hash) << 3));
    // ignore: parameter_assignments
    hash = hash ^ (hash >> 11);
    return 0x1fffffff & (hash + ((0x00003fff & hash) << 15));
  }
}

/// See also [clientData].
@ProviderFor(clientData)
const clientDataProvider = ClientDataFamily();

/// See also [clientData].
class ClientDataFamily extends Family<AsyncValue<ClientResponseModel>> {
  /// See also [clientData].
  const ClientDataFamily();

  /// See also [clientData].
  ClientDataProvider call(String clientId) {
    return ClientDataProvider(clientId);
  }

  @override
  ClientDataProvider getProviderOverride(
    covariant ClientDataProvider provider,
  ) {
    return call(provider.clientId);
  }

  static const Iterable<ProviderOrFamily>? _dependencies = null;

  @override
  Iterable<ProviderOrFamily>? get dependencies => _dependencies;

  static const Iterable<ProviderOrFamily>? _allTransitiveDependencies = null;

  @override
  Iterable<ProviderOrFamily>? get allTransitiveDependencies =>
      _allTransitiveDependencies;

  @override
  String? get name => r'clientDataProvider';
}

/// See also [clientData].
class ClientDataProvider
    extends AutoDisposeFutureProvider<ClientResponseModel> {
  /// See also [clientData].
  ClientDataProvider(String clientId)
    : this._internal(
        (ref) => clientData(ref as ClientDataRef, clientId),
        from: clientDataProvider,
        name: r'clientDataProvider',
        debugGetCreateSourceHash: const bool.fromEnvironment('dart.vm.product')
            ? null
            : _$clientDataHash,
        dependencies: ClientDataFamily._dependencies,
        allTransitiveDependencies: ClientDataFamily._allTransitiveDependencies,
        clientId: clientId,
      );

  ClientDataProvider._internal(
    super._createNotifier, {
    required super.name,
    required super.dependencies,
    required super.allTransitiveDependencies,
    required super.debugGetCreateSourceHash,
    required super.from,
    required this.clientId,
  }) : super.internal();

  final String clientId;

  @override
  Override overrideWith(
    FutureOr<ClientResponseModel> Function(ClientDataRef provider) create,
  ) {
    return ProviderOverride(
      origin: this,
      override: ClientDataProvider._internal(
        (ref) => create(ref as ClientDataRef),
        from: from,
        name: null,
        dependencies: null,
        allTransitiveDependencies: null,
        debugGetCreateSourceHash: null,
        clientId: clientId,
      ),
    );
  }

  @override
  AutoDisposeFutureProviderElement<ClientResponseModel> createElement() {
    return _ClientDataProviderElement(this);
  }

  @override
  bool operator ==(Object other) {
    return other is ClientDataProvider && other.clientId == clientId;
  }

  @override
  int get hashCode {
    var hash = _SystemHash.combine(0, runtimeType.hashCode);
    hash = _SystemHash.combine(hash, clientId.hashCode);

    return _SystemHash.finish(hash);
  }
}

@Deprecated('Will be removed in 3.0. Use Ref instead')
// ignore: unused_element
mixin ClientDataRef on AutoDisposeFutureProviderRef<ClientResponseModel> {
  /// The parameter `clientId` of this provider.
  String get clientId;
}

class _ClientDataProviderElement
    extends AutoDisposeFutureProviderElement<ClientResponseModel>
    with ClientDataRef {
  _ClientDataProviderElement(super.provider);

  @override
  String get clientId => (origin as ClientDataProvider).clientId;
}

// ignore_for_file: type=lint
// ignore_for_file: subtype_of_sealed_class, invalid_use_of_internal_member, invalid_use_of_visible_for_testing_member, deprecated_member_use_from_same_package
