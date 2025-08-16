import 'package:abcde/features/profile/presentation/controller/profile_action_enum.dart';
import 'package:freezed_annotation/freezed_annotation.dart';

part 'profile_state.freezed.dart';

@freezed
sealed class ProfileState with _$ProfileState {
  const factory ProfileState.initial() = _Initial;
  const factory ProfileState.loading(ProfileAction action) = _Loading;
  const factory ProfileState.success(String message, ProfileAction action) = _Success;
  const factory ProfileState.error(String message) = _Error;
}