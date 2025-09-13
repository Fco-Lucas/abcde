// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'create_clientUser_request_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$CreateClientUserRequestModel {

 String get clientId; String get name; String get email; String get password; int get permission;
/// Create a copy of CreateClientUserRequestModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$CreateClientUserRequestModelCopyWith<CreateClientUserRequestModel> get copyWith => _$CreateClientUserRequestModelCopyWithImpl<CreateClientUserRequestModel>(this as CreateClientUserRequestModel, _$identity);

  /// Serializes this CreateClientUserRequestModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is CreateClientUserRequestModel&&(identical(other.clientId, clientId) || other.clientId == clientId)&&(identical(other.name, name) || other.name == name)&&(identical(other.email, email) || other.email == email)&&(identical(other.password, password) || other.password == password)&&(identical(other.permission, permission) || other.permission == permission));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,clientId,name,email,password,permission);

@override
String toString() {
  return 'CreateClientUserRequestModel(clientId: $clientId, name: $name, email: $email, password: $password, permission: $permission)';
}


}

/// @nodoc
abstract mixin class $CreateClientUserRequestModelCopyWith<$Res>  {
  factory $CreateClientUserRequestModelCopyWith(CreateClientUserRequestModel value, $Res Function(CreateClientUserRequestModel) _then) = _$CreateClientUserRequestModelCopyWithImpl;
@useResult
$Res call({
 String clientId, String name, String email, String password, int permission
});




}
/// @nodoc
class _$CreateClientUserRequestModelCopyWithImpl<$Res>
    implements $CreateClientUserRequestModelCopyWith<$Res> {
  _$CreateClientUserRequestModelCopyWithImpl(this._self, this._then);

  final CreateClientUserRequestModel _self;
  final $Res Function(CreateClientUserRequestModel) _then;

/// Create a copy of CreateClientUserRequestModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? clientId = null,Object? name = null,Object? email = null,Object? password = null,Object? permission = null,}) {
  return _then(_self.copyWith(
clientId: null == clientId ? _self.clientId : clientId // ignore: cast_nullable_to_non_nullable
as String,name: null == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String,email: null == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String,password: null == password ? _self.password : password // ignore: cast_nullable_to_non_nullable
as String,permission: null == permission ? _self.permission : permission // ignore: cast_nullable_to_non_nullable
as int,
  ));
}

}


/// Adds pattern-matching-related methods to [CreateClientUserRequestModel].
extension CreateClientUserRequestModelPatterns on CreateClientUserRequestModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _CreateClientUserRequestModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _CreateClientUserRequestModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _CreateClientUserRequestModel value)  $default,){
final _that = this;
switch (_that) {
case _CreateClientUserRequestModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _CreateClientUserRequestModel value)?  $default,){
final _that = this;
switch (_that) {
case _CreateClientUserRequestModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String clientId,  String name,  String email,  String password,  int permission)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _CreateClientUserRequestModel() when $default != null:
return $default(_that.clientId,_that.name,_that.email,_that.password,_that.permission);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String clientId,  String name,  String email,  String password,  int permission)  $default,) {final _that = this;
switch (_that) {
case _CreateClientUserRequestModel():
return $default(_that.clientId,_that.name,_that.email,_that.password,_that.permission);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String clientId,  String name,  String email,  String password,  int permission)?  $default,) {final _that = this;
switch (_that) {
case _CreateClientUserRequestModel() when $default != null:
return $default(_that.clientId,_that.name,_that.email,_that.password,_that.permission);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _CreateClientUserRequestModel implements CreateClientUserRequestModel {
  const _CreateClientUserRequestModel({required this.clientId, required this.name, required this.email, required this.password, required this.permission});
  factory _CreateClientUserRequestModel.fromJson(Map<String, dynamic> json) => _$CreateClientUserRequestModelFromJson(json);

@override final  String clientId;
@override final  String name;
@override final  String email;
@override final  String password;
@override final  int permission;

/// Create a copy of CreateClientUserRequestModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$CreateClientUserRequestModelCopyWith<_CreateClientUserRequestModel> get copyWith => __$CreateClientUserRequestModelCopyWithImpl<_CreateClientUserRequestModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$CreateClientUserRequestModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _CreateClientUserRequestModel&&(identical(other.clientId, clientId) || other.clientId == clientId)&&(identical(other.name, name) || other.name == name)&&(identical(other.email, email) || other.email == email)&&(identical(other.password, password) || other.password == password)&&(identical(other.permission, permission) || other.permission == permission));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,clientId,name,email,password,permission);

@override
String toString() {
  return 'CreateClientUserRequestModel(clientId: $clientId, name: $name, email: $email, password: $password, permission: $permission)';
}


}

/// @nodoc
abstract mixin class _$CreateClientUserRequestModelCopyWith<$Res> implements $CreateClientUserRequestModelCopyWith<$Res> {
  factory _$CreateClientUserRequestModelCopyWith(_CreateClientUserRequestModel value, $Res Function(_CreateClientUserRequestModel) _then) = __$CreateClientUserRequestModelCopyWithImpl;
@override @useResult
$Res call({
 String clientId, String name, String email, String password, int permission
});




}
/// @nodoc
class __$CreateClientUserRequestModelCopyWithImpl<$Res>
    implements _$CreateClientUserRequestModelCopyWith<$Res> {
  __$CreateClientUserRequestModelCopyWithImpl(this._self, this._then);

  final _CreateClientUserRequestModel _self;
  final $Res Function(_CreateClientUserRequestModel) _then;

/// Create a copy of CreateClientUserRequestModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? clientId = null,Object? name = null,Object? email = null,Object? password = null,Object? permission = null,}) {
  return _then(_CreateClientUserRequestModel(
clientId: null == clientId ? _self.clientId : clientId // ignore: cast_nullable_to_non_nullable
as String,name: null == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String,email: null == email ? _self.email : email // ignore: cast_nullable_to_non_nullable
as String,password: null == password ? _self.password : password // ignore: cast_nullable_to_non_nullable
as String,permission: null == permission ? _self.permission : permission // ignore: cast_nullable_to_non_nullable
as int,
  ));
}


}

// dart format on
