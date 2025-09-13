// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'update_client_request_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$UpdateClientRequestModel {

 String? get name; String? get cnpj; String? get urlToPost; int? get imageActiveDays;
/// Create a copy of UpdateClientRequestModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$UpdateClientRequestModelCopyWith<UpdateClientRequestModel> get copyWith => _$UpdateClientRequestModelCopyWithImpl<UpdateClientRequestModel>(this as UpdateClientRequestModel, _$identity);

  /// Serializes this UpdateClientRequestModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is UpdateClientRequestModel&&(identical(other.name, name) || other.name == name)&&(identical(other.cnpj, cnpj) || other.cnpj == cnpj)&&(identical(other.urlToPost, urlToPost) || other.urlToPost == urlToPost)&&(identical(other.imageActiveDays, imageActiveDays) || other.imageActiveDays == imageActiveDays));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,name,cnpj,urlToPost,imageActiveDays);

@override
String toString() {
  return 'UpdateClientRequestModel(name: $name, cnpj: $cnpj, urlToPost: $urlToPost, imageActiveDays: $imageActiveDays)';
}


}

/// @nodoc
abstract mixin class $UpdateClientRequestModelCopyWith<$Res>  {
  factory $UpdateClientRequestModelCopyWith(UpdateClientRequestModel value, $Res Function(UpdateClientRequestModel) _then) = _$UpdateClientRequestModelCopyWithImpl;
@useResult
$Res call({
 String? name, String? cnpj, String? urlToPost, int? imageActiveDays
});




}
/// @nodoc
class _$UpdateClientRequestModelCopyWithImpl<$Res>
    implements $UpdateClientRequestModelCopyWith<$Res> {
  _$UpdateClientRequestModelCopyWithImpl(this._self, this._then);

  final UpdateClientRequestModel _self;
  final $Res Function(UpdateClientRequestModel) _then;

/// Create a copy of UpdateClientRequestModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? name = freezed,Object? cnpj = freezed,Object? urlToPost = freezed,Object? imageActiveDays = freezed,}) {
  return _then(_self.copyWith(
name: freezed == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String?,cnpj: freezed == cnpj ? _self.cnpj : cnpj // ignore: cast_nullable_to_non_nullable
as String?,urlToPost: freezed == urlToPost ? _self.urlToPost : urlToPost // ignore: cast_nullable_to_non_nullable
as String?,imageActiveDays: freezed == imageActiveDays ? _self.imageActiveDays : imageActiveDays // ignore: cast_nullable_to_non_nullable
as int?,
  ));
}

}


/// Adds pattern-matching-related methods to [UpdateClientRequestModel].
extension UpdateClientRequestModelPatterns on UpdateClientRequestModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _UpdateClientRequestModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _UpdateClientRequestModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _UpdateClientRequestModel value)  $default,){
final _that = this;
switch (_that) {
case _UpdateClientRequestModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _UpdateClientRequestModel value)?  $default,){
final _that = this;
switch (_that) {
case _UpdateClientRequestModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String? name,  String? cnpj,  String? urlToPost,  int? imageActiveDays)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _UpdateClientRequestModel() when $default != null:
return $default(_that.name,_that.cnpj,_that.urlToPost,_that.imageActiveDays);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String? name,  String? cnpj,  String? urlToPost,  int? imageActiveDays)  $default,) {final _that = this;
switch (_that) {
case _UpdateClientRequestModel():
return $default(_that.name,_that.cnpj,_that.urlToPost,_that.imageActiveDays);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String? name,  String? cnpj,  String? urlToPost,  int? imageActiveDays)?  $default,) {final _that = this;
switch (_that) {
case _UpdateClientRequestModel() when $default != null:
return $default(_that.name,_that.cnpj,_that.urlToPost,_that.imageActiveDays);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _UpdateClientRequestModel implements UpdateClientRequestModel {
  const _UpdateClientRequestModel({this.name, this.cnpj, this.urlToPost, this.imageActiveDays});
  factory _UpdateClientRequestModel.fromJson(Map<String, dynamic> json) => _$UpdateClientRequestModelFromJson(json);

@override final  String? name;
@override final  String? cnpj;
@override final  String? urlToPost;
@override final  int? imageActiveDays;

/// Create a copy of UpdateClientRequestModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$UpdateClientRequestModelCopyWith<_UpdateClientRequestModel> get copyWith => __$UpdateClientRequestModelCopyWithImpl<_UpdateClientRequestModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$UpdateClientRequestModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _UpdateClientRequestModel&&(identical(other.name, name) || other.name == name)&&(identical(other.cnpj, cnpj) || other.cnpj == cnpj)&&(identical(other.urlToPost, urlToPost) || other.urlToPost == urlToPost)&&(identical(other.imageActiveDays, imageActiveDays) || other.imageActiveDays == imageActiveDays));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,name,cnpj,urlToPost,imageActiveDays);

@override
String toString() {
  return 'UpdateClientRequestModel(name: $name, cnpj: $cnpj, urlToPost: $urlToPost, imageActiveDays: $imageActiveDays)';
}


}

/// @nodoc
abstract mixin class _$UpdateClientRequestModelCopyWith<$Res> implements $UpdateClientRequestModelCopyWith<$Res> {
  factory _$UpdateClientRequestModelCopyWith(_UpdateClientRequestModel value, $Res Function(_UpdateClientRequestModel) _then) = __$UpdateClientRequestModelCopyWithImpl;
@override @useResult
$Res call({
 String? name, String? cnpj, String? urlToPost, int? imageActiveDays
});




}
/// @nodoc
class __$UpdateClientRequestModelCopyWithImpl<$Res>
    implements _$UpdateClientRequestModelCopyWith<$Res> {
  __$UpdateClientRequestModelCopyWithImpl(this._self, this._then);

  final _UpdateClientRequestModel _self;
  final $Res Function(_UpdateClientRequestModel) _then;

/// Create a copy of UpdateClientRequestModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? name = freezed,Object? cnpj = freezed,Object? urlToPost = freezed,Object? imageActiveDays = freezed,}) {
  return _then(_UpdateClientRequestModel(
name: freezed == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String?,cnpj: freezed == cnpj ? _self.cnpj : cnpj // ignore: cast_nullable_to_non_nullable
as String?,urlToPost: freezed == urlToPost ? _self.urlToPost : urlToPost // ignore: cast_nullable_to_non_nullable
as String?,imageActiveDays: freezed == imageActiveDays ? _self.imageActiveDays : imageActiveDays // ignore: cast_nullable_to_non_nullable
as int?,
  ));
}


}

// dart format on
