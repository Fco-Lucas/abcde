// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'update_client_password_request_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$UpdateClientPasswordRequestModel {

 String get currentPassword; String get newPassword; String get confirmNewPassword;
/// Create a copy of UpdateClientPasswordRequestModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$UpdateClientPasswordRequestModelCopyWith<UpdateClientPasswordRequestModel> get copyWith => _$UpdateClientPasswordRequestModelCopyWithImpl<UpdateClientPasswordRequestModel>(this as UpdateClientPasswordRequestModel, _$identity);

  /// Serializes this UpdateClientPasswordRequestModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is UpdateClientPasswordRequestModel&&(identical(other.currentPassword, currentPassword) || other.currentPassword == currentPassword)&&(identical(other.newPassword, newPassword) || other.newPassword == newPassword)&&(identical(other.confirmNewPassword, confirmNewPassword) || other.confirmNewPassword == confirmNewPassword));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,currentPassword,newPassword,confirmNewPassword);

@override
String toString() {
  return 'UpdateClientPasswordRequestModel(currentPassword: $currentPassword, newPassword: $newPassword, confirmNewPassword: $confirmNewPassword)';
}


}

/// @nodoc
abstract mixin class $UpdateClientPasswordRequestModelCopyWith<$Res>  {
  factory $UpdateClientPasswordRequestModelCopyWith(UpdateClientPasswordRequestModel value, $Res Function(UpdateClientPasswordRequestModel) _then) = _$UpdateClientPasswordRequestModelCopyWithImpl;
@useResult
$Res call({
 String currentPassword, String newPassword, String confirmNewPassword
});




}
/// @nodoc
class _$UpdateClientPasswordRequestModelCopyWithImpl<$Res>
    implements $UpdateClientPasswordRequestModelCopyWith<$Res> {
  _$UpdateClientPasswordRequestModelCopyWithImpl(this._self, this._then);

  final UpdateClientPasswordRequestModel _self;
  final $Res Function(UpdateClientPasswordRequestModel) _then;

/// Create a copy of UpdateClientPasswordRequestModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? currentPassword = null,Object? newPassword = null,Object? confirmNewPassword = null,}) {
  return _then(_self.copyWith(
currentPassword: null == currentPassword ? _self.currentPassword : currentPassword // ignore: cast_nullable_to_non_nullable
as String,newPassword: null == newPassword ? _self.newPassword : newPassword // ignore: cast_nullable_to_non_nullable
as String,confirmNewPassword: null == confirmNewPassword ? _self.confirmNewPassword : confirmNewPassword // ignore: cast_nullable_to_non_nullable
as String,
  ));
}

}


/// Adds pattern-matching-related methods to [UpdateClientPasswordRequestModel].
extension UpdateClientPasswordRequestModelPatterns on UpdateClientPasswordRequestModel {
/// A variant of `map` that fallback to returning `orElse`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _UpdateClientPasswordRequestModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _UpdateClientPasswordRequestModel() when $default != null:
return $default(_that);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// Callbacks receives the raw object, upcasted.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case final Subclass2 value:
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _UpdateClientPasswordRequestModel value)  $default,){
final _that = this;
switch (_that) {
case _UpdateClientPasswordRequestModel():
return $default(_that);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `map` that fallback to returning `null`.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case final Subclass value:
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _UpdateClientPasswordRequestModel value)?  $default,){
final _that = this;
switch (_that) {
case _UpdateClientPasswordRequestModel() when $default != null:
return $default(_that);case _:
  return null;

}
}
/// A variant of `when` that fallback to an `orElse` callback.
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return orElse();
/// }
/// ```

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String currentPassword,  String newPassword,  String confirmNewPassword)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _UpdateClientPasswordRequestModel() when $default != null:
return $default(_that.currentPassword,_that.newPassword,_that.confirmNewPassword);case _:
  return orElse();

}
}
/// A `switch`-like method, using callbacks.
///
/// As opposed to `map`, this offers destructuring.
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case Subclass2(:final field2):
///     return ...;
/// }
/// ```

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String currentPassword,  String newPassword,  String confirmNewPassword)  $default,) {final _that = this;
switch (_that) {
case _UpdateClientPasswordRequestModel():
return $default(_that.currentPassword,_that.newPassword,_that.confirmNewPassword);case _:
  throw StateError('Unexpected subclass');

}
}
/// A variant of `when` that fallback to returning `null`
///
/// It is equivalent to doing:
/// ```dart
/// switch (sealedClass) {
///   case Subclass(:final field):
///     return ...;
///   case _:
///     return null;
/// }
/// ```

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String currentPassword,  String newPassword,  String confirmNewPassword)?  $default,) {final _that = this;
switch (_that) {
case _UpdateClientPasswordRequestModel() when $default != null:
return $default(_that.currentPassword,_that.newPassword,_that.confirmNewPassword);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _UpdateClientPasswordRequestModel implements UpdateClientPasswordRequestModel {
  const _UpdateClientPasswordRequestModel({required this.currentPassword, required this.newPassword, required this.confirmNewPassword});
  factory _UpdateClientPasswordRequestModel.fromJson(Map<String, dynamic> json) => _$UpdateClientPasswordRequestModelFromJson(json);

@override final  String currentPassword;
@override final  String newPassword;
@override final  String confirmNewPassword;

/// Create a copy of UpdateClientPasswordRequestModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$UpdateClientPasswordRequestModelCopyWith<_UpdateClientPasswordRequestModel> get copyWith => __$UpdateClientPasswordRequestModelCopyWithImpl<_UpdateClientPasswordRequestModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$UpdateClientPasswordRequestModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _UpdateClientPasswordRequestModel&&(identical(other.currentPassword, currentPassword) || other.currentPassword == currentPassword)&&(identical(other.newPassword, newPassword) || other.newPassword == newPassword)&&(identical(other.confirmNewPassword, confirmNewPassword) || other.confirmNewPassword == confirmNewPassword));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,currentPassword,newPassword,confirmNewPassword);

@override
String toString() {
  return 'UpdateClientPasswordRequestModel(currentPassword: $currentPassword, newPassword: $newPassword, confirmNewPassword: $confirmNewPassword)';
}


}

/// @nodoc
abstract mixin class _$UpdateClientPasswordRequestModelCopyWith<$Res> implements $UpdateClientPasswordRequestModelCopyWith<$Res> {
  factory _$UpdateClientPasswordRequestModelCopyWith(_UpdateClientPasswordRequestModel value, $Res Function(_UpdateClientPasswordRequestModel) _then) = __$UpdateClientPasswordRequestModelCopyWithImpl;
@override @useResult
$Res call({
 String currentPassword, String newPassword, String confirmNewPassword
});




}
/// @nodoc
class __$UpdateClientPasswordRequestModelCopyWithImpl<$Res>
    implements _$UpdateClientPasswordRequestModelCopyWith<$Res> {
  __$UpdateClientPasswordRequestModelCopyWithImpl(this._self, this._then);

  final _UpdateClientPasswordRequestModel _self;
  final $Res Function(_UpdateClientPasswordRequestModel) _then;

/// Create a copy of UpdateClientPasswordRequestModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? currentPassword = null,Object? newPassword = null,Object? confirmNewPassword = null,}) {
  return _then(_UpdateClientPasswordRequestModel(
currentPassword: null == currentPassword ? _self.currentPassword : currentPassword // ignore: cast_nullable_to_non_nullable
as String,newPassword: null == newPassword ? _self.newPassword : newPassword // ignore: cast_nullable_to_non_nullable
as String,confirmNewPassword: null == confirmNewPassword ? _self.confirmNewPassword : confirmNewPassword // ignore: cast_nullable_to_non_nullable
as String,
  ));
}


}

// dart format on
