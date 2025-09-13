// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'create_client_request_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$CreateClientRequestModel {

 String get name; String get cnpj; String get password; String get urlToPost; int get imageActiveDays;
/// Create a copy of CreateClientRequestModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$CreateClientRequestModelCopyWith<CreateClientRequestModel> get copyWith => _$CreateClientRequestModelCopyWithImpl<CreateClientRequestModel>(this as CreateClientRequestModel, _$identity);

  /// Serializes this CreateClientRequestModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is CreateClientRequestModel&&(identical(other.name, name) || other.name == name)&&(identical(other.cnpj, cnpj) || other.cnpj == cnpj)&&(identical(other.password, password) || other.password == password)&&(identical(other.urlToPost, urlToPost) || other.urlToPost == urlToPost)&&(identical(other.imageActiveDays, imageActiveDays) || other.imageActiveDays == imageActiveDays));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,name,cnpj,password,urlToPost,imageActiveDays);

@override
String toString() {
  return 'CreateClientRequestModel(name: $name, cnpj: $cnpj, password: $password, urlToPost: $urlToPost, imageActiveDays: $imageActiveDays)';
}


}

/// @nodoc
abstract mixin class $CreateClientRequestModelCopyWith<$Res>  {
  factory $CreateClientRequestModelCopyWith(CreateClientRequestModel value, $Res Function(CreateClientRequestModel) _then) = _$CreateClientRequestModelCopyWithImpl;
@useResult
$Res call({
 String name, String cnpj, String password, String urlToPost, int imageActiveDays
});




}
/// @nodoc
class _$CreateClientRequestModelCopyWithImpl<$Res>
    implements $CreateClientRequestModelCopyWith<$Res> {
  _$CreateClientRequestModelCopyWithImpl(this._self, this._then);

  final CreateClientRequestModel _self;
  final $Res Function(CreateClientRequestModel) _then;

/// Create a copy of CreateClientRequestModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? name = null,Object? cnpj = null,Object? password = null,Object? urlToPost = null,Object? imageActiveDays = null,}) {
  return _then(_self.copyWith(
name: null == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String,cnpj: null == cnpj ? _self.cnpj : cnpj // ignore: cast_nullable_to_non_nullable
as String,password: null == password ? _self.password : password // ignore: cast_nullable_to_non_nullable
as String,urlToPost: null == urlToPost ? _self.urlToPost : urlToPost // ignore: cast_nullable_to_non_nullable
as String,imageActiveDays: null == imageActiveDays ? _self.imageActiveDays : imageActiveDays // ignore: cast_nullable_to_non_nullable
as int,
  ));
}

}


/// Adds pattern-matching-related methods to [CreateClientRequestModel].
extension CreateClientRequestModelPatterns on CreateClientRequestModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _CreateClientRequestModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _CreateClientRequestModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _CreateClientRequestModel value)  $default,){
final _that = this;
switch (_that) {
case _CreateClientRequestModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _CreateClientRequestModel value)?  $default,){
final _that = this;
switch (_that) {
case _CreateClientRequestModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String name,  String cnpj,  String password,  String urlToPost,  int imageActiveDays)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _CreateClientRequestModel() when $default != null:
return $default(_that.name,_that.cnpj,_that.password,_that.urlToPost,_that.imageActiveDays);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String name,  String cnpj,  String password,  String urlToPost,  int imageActiveDays)  $default,) {final _that = this;
switch (_that) {
case _CreateClientRequestModel():
return $default(_that.name,_that.cnpj,_that.password,_that.urlToPost,_that.imageActiveDays);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String name,  String cnpj,  String password,  String urlToPost,  int imageActiveDays)?  $default,) {final _that = this;
switch (_that) {
case _CreateClientRequestModel() when $default != null:
return $default(_that.name,_that.cnpj,_that.password,_that.urlToPost,_that.imageActiveDays);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _CreateClientRequestModel implements CreateClientRequestModel {
  const _CreateClientRequestModel({required this.name, required this.cnpj, required this.password, required this.urlToPost, required this.imageActiveDays});
  factory _CreateClientRequestModel.fromJson(Map<String, dynamic> json) => _$CreateClientRequestModelFromJson(json);

@override final  String name;
@override final  String cnpj;
@override final  String password;
@override final  String urlToPost;
@override final  int imageActiveDays;

/// Create a copy of CreateClientRequestModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$CreateClientRequestModelCopyWith<_CreateClientRequestModel> get copyWith => __$CreateClientRequestModelCopyWithImpl<_CreateClientRequestModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$CreateClientRequestModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _CreateClientRequestModel&&(identical(other.name, name) || other.name == name)&&(identical(other.cnpj, cnpj) || other.cnpj == cnpj)&&(identical(other.password, password) || other.password == password)&&(identical(other.urlToPost, urlToPost) || other.urlToPost == urlToPost)&&(identical(other.imageActiveDays, imageActiveDays) || other.imageActiveDays == imageActiveDays));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,name,cnpj,password,urlToPost,imageActiveDays);

@override
String toString() {
  return 'CreateClientRequestModel(name: $name, cnpj: $cnpj, password: $password, urlToPost: $urlToPost, imageActiveDays: $imageActiveDays)';
}


}

/// @nodoc
abstract mixin class _$CreateClientRequestModelCopyWith<$Res> implements $CreateClientRequestModelCopyWith<$Res> {
  factory _$CreateClientRequestModelCopyWith(_CreateClientRequestModel value, $Res Function(_CreateClientRequestModel) _then) = __$CreateClientRequestModelCopyWithImpl;
@override @useResult
$Res call({
 String name, String cnpj, String password, String urlToPost, int imageActiveDays
});




}
/// @nodoc
class __$CreateClientRequestModelCopyWithImpl<$Res>
    implements _$CreateClientRequestModelCopyWith<$Res> {
  __$CreateClientRequestModelCopyWithImpl(this._self, this._then);

  final _CreateClientRequestModel _self;
  final $Res Function(_CreateClientRequestModel) _then;

/// Create a copy of CreateClientRequestModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? name = null,Object? cnpj = null,Object? password = null,Object? urlToPost = null,Object? imageActiveDays = null,}) {
  return _then(_CreateClientRequestModel(
name: null == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String,cnpj: null == cnpj ? _self.cnpj : cnpj // ignore: cast_nullable_to_non_nullable
as String,password: null == password ? _self.password : password // ignore: cast_nullable_to_non_nullable
as String,urlToPost: null == urlToPost ? _self.urlToPost : urlToPost // ignore: cast_nullable_to_non_nullable
as String,imageActiveDays: null == imageActiveDays ? _self.imageActiveDays : imageActiveDays // ignore: cast_nullable_to_non_nullable
as int,
  ));
}


}

// dart format on
