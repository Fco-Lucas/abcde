// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'clientUser_response_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$ClientuserResponseModel {

 String get id; String get clientId; String get name; String get string; PermissionResponseModel get permission; ClientUserStatus get status;
/// Create a copy of ClientuserResponseModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$ClientuserResponseModelCopyWith<ClientuserResponseModel> get copyWith => _$ClientuserResponseModelCopyWithImpl<ClientuserResponseModel>(this as ClientuserResponseModel, _$identity);

  /// Serializes this ClientuserResponseModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is ClientuserResponseModel&&(identical(other.id, id) || other.id == id)&&(identical(other.clientId, clientId) || other.clientId == clientId)&&(identical(other.name, name) || other.name == name)&&(identical(other.string, string) || other.string == string)&&(identical(other.permission, permission) || other.permission == permission)&&(identical(other.status, status) || other.status == status));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,clientId,name,string,permission,status);

@override
String toString() {
  return 'ClientuserResponseModel(id: $id, clientId: $clientId, name: $name, string: $string, permission: $permission, status: $status)';
}


}

/// @nodoc
abstract mixin class $ClientuserResponseModelCopyWith<$Res>  {
  factory $ClientuserResponseModelCopyWith(ClientuserResponseModel value, $Res Function(ClientuserResponseModel) _then) = _$ClientuserResponseModelCopyWithImpl;
@useResult
$Res call({
 String id, String clientId, String name, String string, PermissionResponseModel permission, ClientUserStatus status
});


$PermissionResponseModelCopyWith<$Res> get permission;

}
/// @nodoc
class _$ClientuserResponseModelCopyWithImpl<$Res>
    implements $ClientuserResponseModelCopyWith<$Res> {
  _$ClientuserResponseModelCopyWithImpl(this._self, this._then);

  final ClientuserResponseModel _self;
  final $Res Function(ClientuserResponseModel) _then;

/// Create a copy of ClientuserResponseModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? id = null,Object? clientId = null,Object? name = null,Object? string = null,Object? permission = null,Object? status = null,}) {
  return _then(_self.copyWith(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as String,clientId: null == clientId ? _self.clientId : clientId // ignore: cast_nullable_to_non_nullable
as String,name: null == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String,string: null == string ? _self.string : string // ignore: cast_nullable_to_non_nullable
as String,permission: null == permission ? _self.permission : permission // ignore: cast_nullable_to_non_nullable
as PermissionResponseModel,status: null == status ? _self.status : status // ignore: cast_nullable_to_non_nullable
as ClientUserStatus,
  ));
}
/// Create a copy of ClientuserResponseModel
/// with the given fields replaced by the non-null parameter values.
@override
@pragma('vm:prefer-inline')
$PermissionResponseModelCopyWith<$Res> get permission {
  
  return $PermissionResponseModelCopyWith<$Res>(_self.permission, (value) {
    return _then(_self.copyWith(permission: value));
  });
}
}


/// Adds pattern-matching-related methods to [ClientuserResponseModel].
extension ClientuserResponseModelPatterns on ClientuserResponseModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _ClientuserResponseModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _ClientuserResponseModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _ClientuserResponseModel value)  $default,){
final _that = this;
switch (_that) {
case _ClientuserResponseModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _ClientuserResponseModel value)?  $default,){
final _that = this;
switch (_that) {
case _ClientuserResponseModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( String id,  String clientId,  String name,  String string,  PermissionResponseModel permission,  ClientUserStatus status)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _ClientuserResponseModel() when $default != null:
return $default(_that.id,_that.clientId,_that.name,_that.string,_that.permission,_that.status);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( String id,  String clientId,  String name,  String string,  PermissionResponseModel permission,  ClientUserStatus status)  $default,) {final _that = this;
switch (_that) {
case _ClientuserResponseModel():
return $default(_that.id,_that.clientId,_that.name,_that.string,_that.permission,_that.status);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( String id,  String clientId,  String name,  String string,  PermissionResponseModel permission,  ClientUserStatus status)?  $default,) {final _that = this;
switch (_that) {
case _ClientuserResponseModel() when $default != null:
return $default(_that.id,_that.clientId,_that.name,_that.string,_that.permission,_that.status);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _ClientuserResponseModel implements ClientuserResponseModel {
  const _ClientuserResponseModel({required this.id, required this.clientId, required this.name, required this.string, required this.permission, required this.status});
  factory _ClientuserResponseModel.fromJson(Map<String, dynamic> json) => _$ClientuserResponseModelFromJson(json);

@override final  String id;
@override final  String clientId;
@override final  String name;
@override final  String string;
@override final  PermissionResponseModel permission;
@override final  ClientUserStatus status;

/// Create a copy of ClientuserResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$ClientuserResponseModelCopyWith<_ClientuserResponseModel> get copyWith => __$ClientuserResponseModelCopyWithImpl<_ClientuserResponseModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$ClientuserResponseModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _ClientuserResponseModel&&(identical(other.id, id) || other.id == id)&&(identical(other.clientId, clientId) || other.clientId == clientId)&&(identical(other.name, name) || other.name == name)&&(identical(other.string, string) || other.string == string)&&(identical(other.permission, permission) || other.permission == permission)&&(identical(other.status, status) || other.status == status));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,clientId,name,string,permission,status);

@override
String toString() {
  return 'ClientuserResponseModel(id: $id, clientId: $clientId, name: $name, string: $string, permission: $permission, status: $status)';
}


}

/// @nodoc
abstract mixin class _$ClientuserResponseModelCopyWith<$Res> implements $ClientuserResponseModelCopyWith<$Res> {
  factory _$ClientuserResponseModelCopyWith(_ClientuserResponseModel value, $Res Function(_ClientuserResponseModel) _then) = __$ClientuserResponseModelCopyWithImpl;
@override @useResult
$Res call({
 String id, String clientId, String name, String string, PermissionResponseModel permission, ClientUserStatus status
});


@override $PermissionResponseModelCopyWith<$Res> get permission;

}
/// @nodoc
class __$ClientuserResponseModelCopyWithImpl<$Res>
    implements _$ClientuserResponseModelCopyWith<$Res> {
  __$ClientuserResponseModelCopyWithImpl(this._self, this._then);

  final _ClientuserResponseModel _self;
  final $Res Function(_ClientuserResponseModel) _then;

/// Create a copy of ClientuserResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? id = null,Object? clientId = null,Object? name = null,Object? string = null,Object? permission = null,Object? status = null,}) {
  return _then(_ClientuserResponseModel(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as String,clientId: null == clientId ? _self.clientId : clientId // ignore: cast_nullable_to_non_nullable
as String,name: null == name ? _self.name : name // ignore: cast_nullable_to_non_nullable
as String,string: null == string ? _self.string : string // ignore: cast_nullable_to_non_nullable
as String,permission: null == permission ? _self.permission : permission // ignore: cast_nullable_to_non_nullable
as PermissionResponseModel,status: null == status ? _self.status : status // ignore: cast_nullable_to_non_nullable
as ClientUserStatus,
  ));
}

/// Create a copy of ClientuserResponseModel
/// with the given fields replaced by the non-null parameter values.
@override
@pragma('vm:prefer-inline')
$PermissionResponseModelCopyWith<$Res> get permission {
  
  return $PermissionResponseModelCopyWith<$Res>(_self.permission, (value) {
    return _then(_self.copyWith(permission: value));
  });
}
}

// dart format on
