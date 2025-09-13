// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'client_filter_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;
/// @nodoc
mixin _$ClientFilterModel {

 String? get cnpj; ClientStatus? get status;
/// Create a copy of ClientFilterModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$ClientFilterModelCopyWith<ClientFilterModel> get copyWith => _$ClientFilterModelCopyWithImpl<ClientFilterModel>(this as ClientFilterModel, _$identity);



@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is ClientFilterModel&&(identical(other.cnpj, cnpj) || other.cnpj == cnpj)&&(identical(other.status, status) || other.status == status));
}


@override
int get hashCode => Object.hash(runtimeType,cnpj,status);

@override
String toString() {
  return 'ClientFilterModel(cnpj: $cnpj, status: $status)';
}


}

/// @nodoc
abstract mixin class $ClientFilterModelCopyWith<$Res>  {
  factory $ClientFilterModelCopyWith(ClientFilterModel value, $Res Function(ClientFilterModel) _then) = _$ClientFilterModelCopyWithImpl;
@useResult
$Res call({
 String? cnpj, ClientStatus? status
});




}
/// @nodoc
class _$ClientFilterModelCopyWithImpl<$Res>
    implements $ClientFilterModelCopyWith<$Res> {
  _$ClientFilterModelCopyWithImpl(this._self, this._then);

  final ClientFilterModel _self;
  final $Res Function(ClientFilterModel) _then;

/// Create a copy of ClientFilterModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? cnpj = freezed,Object? status = freezed,}) {
  return _then(_self.copyWith(
cnpj: freezed == cnpj ? _self.cnpj : cnpj // ignore: cast_nullable_to_non_nullable
as String?,status: freezed == status ? _self.status : status // ignore: cast_nullable_to_non_nullable
as ClientStatus?,
  ));
}

}


/// Adds pattern-matching-related methods to [ClientFilterModel].
extension ClientFilterModelPatterns on ClientFilterModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _ClientFilterModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _ClientFilterModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _ClientFilterModel value)  $default,){
final _that = this;
switch (_that) {
case _ClientFilterModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _ClientFilterModel value)?  $default,){
final _that = this;
switch (_that) {
case _ClientFilterModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String? cnpj,  ClientStatus? status)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _ClientFilterModel() when $default != null:
return $default(_that.cnpj,_that.status);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String? cnpj,  ClientStatus? status)  $default,) {final _that = this;
switch (_that) {
case _ClientFilterModel():
return $default(_that.cnpj,_that.status);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String? cnpj,  ClientStatus? status)?  $default,) {final _that = this;
switch (_that) {
case _ClientFilterModel() when $default != null:
return $default(_that.cnpj,_that.status);case _:
  return null;

}
}

}

/// @nodoc


class _ClientFilterModel implements ClientFilterModel {
  const _ClientFilterModel({this.cnpj, this.status});
  

@override final  String? cnpj;
@override final  ClientStatus? status;

/// Create a copy of ClientFilterModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$ClientFilterModelCopyWith<_ClientFilterModel> get copyWith => __$ClientFilterModelCopyWithImpl<_ClientFilterModel>(this, _$identity);



@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _ClientFilterModel&&(identical(other.cnpj, cnpj) || other.cnpj == cnpj)&&(identical(other.status, status) || other.status == status));
}


@override
int get hashCode => Object.hash(runtimeType,cnpj,status);

@override
String toString() {
  return 'ClientFilterModel(cnpj: $cnpj, status: $status)';
}


}

/// @nodoc
abstract mixin class _$ClientFilterModelCopyWith<$Res> implements $ClientFilterModelCopyWith<$Res> {
  factory _$ClientFilterModelCopyWith(_ClientFilterModel value, $Res Function(_ClientFilterModel) _then) = __$ClientFilterModelCopyWithImpl;
@override @useResult
$Res call({
 String? cnpj, ClientStatus? status
});




}
/// @nodoc
class __$ClientFilterModelCopyWithImpl<$Res>
    implements _$ClientFilterModelCopyWith<$Res> {
  __$ClientFilterModelCopyWithImpl(this._self, this._then);

  final _ClientFilterModel _self;
  final $Res Function(_ClientFilterModel) _then;

/// Create a copy of ClientFilterModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? cnpj = freezed,Object? status = freezed,}) {
  return _then(_ClientFilterModel(
cnpj: freezed == cnpj ? _self.cnpj : cnpj // ignore: cast_nullable_to_non_nullable
as String?,status: freezed == status ? _self.status : status // ignore: cast_nullable_to_non_nullable
as ClientStatus?,
  ));
}


}

// dart format on
