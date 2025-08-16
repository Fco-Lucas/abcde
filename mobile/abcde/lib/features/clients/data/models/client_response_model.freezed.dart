// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'client_response_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$ClientResponseModel {

 String get id; String get name; String get cnpj; String? get urlToPost; int get imageActiveDays; ClientStatus get status; List get users;
/// Create a copy of ClientResponseModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$ClientResponseModelCopyWith<ClientResponseModel> get copyWith => _$ClientResponseModelCopyWithImpl<ClientResponseModel>(this as ClientResponseModel, _$identity);

  /// Serializes this ClientResponseModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is ClientResponseModel&&(identical(other.id, id) || other.id == id)&&(identical(other.name, name) || other.name == name)&&(identical(other.cnpj, cnpj) || other.cnpj == cnpj)&&(identical(other.urlToPost, urlToPost) || other.urlToPost == urlToPost)&&(identical(other.imageActiveDays, imageActiveDays) || other.imageActiveDays == imageActiveDays)&&(identical(other.status, status) || other.status == status)&&const DeepCollectionEquality().equals(other.users, users));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,name,cnpj,urlToPost,imageActiveDays,status,const DeepCollectionEquality().hash(users));

@override
String toString() {
  return 'ClientResponseModel(id: $id, name: $name, cnpj: $cnpj, urlToPost: $urlToPost, imageActiveDays: $imageActiveDays, status: $status, users: $users)';
}


}

/// @nodoc
abstract mixin class $ClientResponseModelCopyWith<$Res>  {
  factory $ClientResponseModelCopyWith(ClientResponseModel value, $Res Function(ClientResponseModel) _then) = _$ClientResponseModelCopyWithImpl;
@useResult
$Res call({
 String id, String name, String cnpj, String? urlToPost, int imageActiveDays, ClientStatus status, List users
});




}
/// @nodoc
class _$ClientResponseModelCopyWithImpl<$Res>
    implements $ClientResponseModelCopyWith<$Res> {
  _$ClientResponseModelCopyWithImpl(this._self, this._then);

  final ClientResponseModel _self;
  final $Res Function(ClientResponseModel) _then;

/// Create a copy of ClientResponseModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? id = null,Object? name = null,Object? cnpj = null,Object? urlToPost = freezed,Object? imageActiveDays = null,Object? status = null,Object? users = null,}) {
  return _then(_self.copyWith(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as String,name: null == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String,cnpj: null == cnpj ? _self.cnpj : cnpj // ignore: cast_nullable_to_non_nullable
as String,urlToPost: freezed == urlToPost ? _self.urlToPost : urlToPost // ignore: cast_nullable_to_non_nullable
as String?,imageActiveDays: null == imageActiveDays ? _self.imageActiveDays : imageActiveDays // ignore: cast_nullable_to_non_nullable
as int,status: null == status ? _self.status : status // ignore: cast_nullable_to_non_nullable
as ClientStatus,users: null == users ? _self.users : users // ignore: cast_nullable_to_non_nullable
as List,
  ));
}

}


/// Adds pattern-matching-related methods to [ClientResponseModel].
extension ClientResponseModelPatterns on ClientResponseModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _ClientResponseModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _ClientResponseModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _ClientResponseModel value)  $default,){
final _that = this;
switch (_that) {
case _ClientResponseModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _ClientResponseModel value)?  $default,){
final _that = this;
switch (_that) {
case _ClientResponseModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String id,  String name,  String cnpj,  String? urlToPost,  int imageActiveDays,  ClientStatus status,  List users)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _ClientResponseModel() when $default != null:
return $default(_that.id,_that.name,_that.cnpj,_that.urlToPost,_that.imageActiveDays,_that.status,_that.users);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String id,  String name,  String cnpj,  String? urlToPost,  int imageActiveDays,  ClientStatus status,  List users)  $default,) {final _that = this;
switch (_that) {
case _ClientResponseModel():
return $default(_that.id,_that.name,_that.cnpj,_that.urlToPost,_that.imageActiveDays,_that.status,_that.users);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String id,  String name,  String cnpj,  String? urlToPost,  int imageActiveDays,  ClientStatus status,  List users)?  $default,) {final _that = this;
switch (_that) {
case _ClientResponseModel() when $default != null:
return $default(_that.id,_that.name,_that.cnpj,_that.urlToPost,_that.imageActiveDays,_that.status,_that.users);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _ClientResponseModel implements ClientResponseModel {
  const _ClientResponseModel({required this.id, required this.name, required this.cnpj, this.urlToPost, required this.imageActiveDays, required this.status, required final  List users}): _users = users;
  factory _ClientResponseModel.fromJson(Map<String, dynamic> json) => _$ClientResponseModelFromJson(json);

@override final  String id;
@override final  String name;
@override final  String cnpj;
@override final  String? urlToPost;
@override final  int imageActiveDays;
@override final  ClientStatus status;
 final  List _users;
@override List get users {
  if (_users is EqualUnmodifiableListView) return _users;
  // ignore: implicit_dynamic_type
  return EqualUnmodifiableListView(_users);
}


/// Create a copy of ClientResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$ClientResponseModelCopyWith<_ClientResponseModel> get copyWith => __$ClientResponseModelCopyWithImpl<_ClientResponseModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$ClientResponseModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _ClientResponseModel&&(identical(other.id, id) || other.id == id)&&(identical(other.name, name) || other.name == name)&&(identical(other.cnpj, cnpj) || other.cnpj == cnpj)&&(identical(other.urlToPost, urlToPost) || other.urlToPost == urlToPost)&&(identical(other.imageActiveDays, imageActiveDays) || other.imageActiveDays == imageActiveDays)&&(identical(other.status, status) || other.status == status)&&const DeepCollectionEquality().equals(other._users, _users));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,name,cnpj,urlToPost,imageActiveDays,status,const DeepCollectionEquality().hash(_users));

@override
String toString() {
  return 'ClientResponseModel(id: $id, name: $name, cnpj: $cnpj, urlToPost: $urlToPost, imageActiveDays: $imageActiveDays, status: $status, users: $users)';
}


}

/// @nodoc
abstract mixin class _$ClientResponseModelCopyWith<$Res> implements $ClientResponseModelCopyWith<$Res> {
  factory _$ClientResponseModelCopyWith(_ClientResponseModel value, $Res Function(_ClientResponseModel) _then) = __$ClientResponseModelCopyWithImpl;
@override @useResult
$Res call({
 String id, String name, String cnpj, String? urlToPost, int imageActiveDays, ClientStatus status, List users
});




}
/// @nodoc
class __$ClientResponseModelCopyWithImpl<$Res>
    implements _$ClientResponseModelCopyWith<$Res> {
  __$ClientResponseModelCopyWithImpl(this._self, this._then);

  final _ClientResponseModel _self;
  final $Res Function(_ClientResponseModel) _then;

/// Create a copy of ClientResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? id = null,Object? name = null,Object? cnpj = null,Object? urlToPost = freezed,Object? imageActiveDays = null,Object? status = null,Object? users = null,}) {
  return _then(_ClientResponseModel(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as String,name: null == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String,cnpj: null == cnpj ? _self.cnpj : cnpj // ignore: cast_nullable_to_non_nullable
as String,urlToPost: freezed == urlToPost ? _self.urlToPost : urlToPost // ignore: cast_nullable_to_non_nullable
as String?,imageActiveDays: null == imageActiveDays ? _self.imageActiveDays : imageActiveDays // ignore: cast_nullable_to_non_nullable
as int,status: null == status ? _self.status : status // ignore: cast_nullable_to_non_nullable
as ClientStatus,users: null == users ? _self._users : users // ignore: cast_nullable_to_non_nullable
as List,
  ));
}


}

// dart format on
