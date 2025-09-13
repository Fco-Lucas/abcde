import 'package:riverpod_annotation/riverpod_annotation.dart';

part 'fab_visibility_provider.g.dart';

@riverpod
class FabVisibility extends _$FabVisibility {
  @override
  bool build() {
    return true;
  }

  void setVisibility(bool isVisible) {
    state = isVisible;
  }
}