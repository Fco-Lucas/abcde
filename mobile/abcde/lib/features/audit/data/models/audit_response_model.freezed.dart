// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'audit_response_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$AuditResponseModel {

 int get id; AuditActionEnum get action; String get clientId; String get clientName; String? get userId; String get userName; AuditProgramEnum get program; String get details; String get createdAt;
/// Create a copy of AuditResponseModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$AuditResponseModelCopyWith<AuditResponseModel> get copyWith => _$AuditResponseModelCopyWithImpl<AuditResponseModel>(this as AuditResponseModel, _$identity);

  /// Serializes this AuditResponseModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is AuditResponseModel&&(identical(other.id, id) || other.id == id)&&(identical(other.action, action) || other.action == action)&&(identical(other.clientId, clientId) || other.clientId == clientId)&&(identical(other.clientName, clientName) || other.clientName == clientName)&&(identical(other.userId, userId) || other.userId == userId)&&(identical(other.userName, userName) || other.userName == userName)&&(identical(other.program, program) || other.program == program)&&(identical(other.details, details) || other.details == details)&&(identical(other.createdAt, createdAt) || other.createdAt == createdAt));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,action,clientId,clientName,userId,userName,program,details,createdAt);

@override
String toString() {
  return 'AuditResponseModel(id: $id, action: $action, clientId: $clientId, clientName: $clientName, userId: $userId, userName: $userName, program: $program, details: $details, createdAt: $createdAt)';
}


}

/// @nodoc
abstract mixin class $AuditResponseModelCopyWith<$Res>  {
  factory $AuditResponseModelCopyWith(AuditResponseModel value, $Res Function(AuditResponseModel) _then) = _$AuditResponseModelCopyWithImpl;
@useResult
$Res call({
 int id, AuditActionEnum action, String clientId, String clientName, String? userId, String userName, AuditProgramEnum program, String details, String createdAt
});




}
/// @nodoc
class _$AuditResponseModelCopyWithImpl<$Res>
    implements $AuditResponseModelCopyWith<$Res> {
  _$AuditResponseModelCopyWithImpl(this._self, this._then);

  final AuditResponseModel _self;
  final $Res Function(AuditResponseModel) _then;

/// Create a copy of AuditResponseModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? id = null,Object? action = null,Object? clientId = null,Object? clientName = null,Object? userId = freezed,Object? userName = null,Object? program = null,Object? details = null,Object? createdAt = null,}) {
  return _then(_self.copyWith(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int,action: null == action ? _self.action : action // ignore: cast_nullable_to_non_nullable
as AuditActionEnum,clientId: null == clientId ? _self.clientId : clientId // ignore: cast_nullable_to_non_nullable
as String,clientName: null == clientName ? _self.clientName : clientName // ignore: cast_nullable_to_non_nullable
as String,userId: freezed == userId ? _self.userId : userId // ignore: cast_nullable_to_non_nullable
as String?,userName: null == userName ? _self.userName : userName // ignore: cast_nullable_to_non_nullable
as String,program: null == program ? _self.program : program // ignore: cast_nullable_to_non_nullable
as AuditProgramEnum,details: null == details ? _self.details : details // ignore: cast_nullable_to_non_nullable
as String,createdAt: null == createdAt ? _self.createdAt : createdAt // ignore: cast_nullable_to_non_nullable
as String,
  ));
}

}


/// Adds pattern-matching-related methods to [AuditResponseModel].
extension AuditResponseModelPatterns on AuditResponseModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _AuditResponseModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _AuditResponseModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _AuditResponseModel value)  $default,){
final _that = this;
switch (_that) {
case _AuditResponseModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _AuditResponseModel value)?  $default,){
final _that = this;
switch (_that) {
case _AuditResponseModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( int id,  AuditActionEnum action,  String clientId,  String clientName,  String? userId,  String userName,  AuditProgramEnum program,  String details,  String createdAt)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _AuditResponseModel() when $default != null:
return $default(_that.id,_that.action,_that.clientId,_that.clientName,_that.userId,_that.userName,_that.program,_that.details,_that.createdAt);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( int id,  AuditActionEnum action,  String clientId,  String clientName,  String? userId,  String userName,  AuditProgramEnum program,  String details,  String createdAt)  $default,) {final _that = this;
switch (_that) {
case _AuditResponseModel():
return $default(_that.id,_that.action,_that.clientId,_that.clientName,_that.userId,_that.userName,_that.program,_that.details,_that.createdAt);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( int id,  AuditActionEnum action,  String clientId,  String clientName,  String? userId,  String userName,  AuditProgramEnum program,  String details,  String createdAt)?  $default,) {final _that = this;
switch (_that) {
case _AuditResponseModel() when $default != null:
return $default(_that.id,_that.action,_that.clientId,_that.clientName,_that.userId,_that.userName,_that.program,_that.details,_that.createdAt);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _AuditResponseModel implements AuditResponseModel {
  const _AuditResponseModel({required this.id, required this.action, required this.clientId, required this.clientName, this.userId, required this.userName, required this.program, required this.details, required this.createdAt});
  factory _AuditResponseModel.fromJson(Map<String, dynamic> json) => _$AuditResponseModelFromJson(json);

@override final  int id;
@override final  AuditActionEnum action;
@override final  String clientId;
@override final  String clientName;
@override final  String? userId;
@override final  String userName;
@override final  AuditProgramEnum program;
@override final  String details;
@override final  String createdAt;

/// Create a copy of AuditResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$AuditResponseModelCopyWith<_AuditResponseModel> get copyWith => __$AuditResponseModelCopyWithImpl<_AuditResponseModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$AuditResponseModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _AuditResponseModel&&(identical(other.id, id) || other.id == id)&&(identical(other.action, action) || other.action == action)&&(identical(other.clientId, clientId) || other.clientId == clientId)&&(identical(other.clientName, clientName) || other.clientName == clientName)&&(identical(other.userId, userId) || other.userId == userId)&&(identical(other.userName, userName) || other.userName == userName)&&(identical(other.program, program) || other.program == program)&&(identical(other.details, details) || other.details == details)&&(identical(other.createdAt, createdAt) || other.createdAt == createdAt));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,action,clientId,clientName,userId,userName,program,details,createdAt);

@override
String toString() {
  return 'AuditResponseModel(id: $id, action: $action, clientId: $clientId, clientName: $clientName, userId: $userId, userName: $userName, program: $program, details: $details, createdAt: $createdAt)';
}


}

/// @nodoc
abstract mixin class _$AuditResponseModelCopyWith<$Res> implements $AuditResponseModelCopyWith<$Res> {
  factory _$AuditResponseModelCopyWith(_AuditResponseModel value, $Res Function(_AuditResponseModel) _then) = __$AuditResponseModelCopyWithImpl;
@override @useResult
$Res call({
 int id, AuditActionEnum action, String clientId, String clientName, String? userId, String userName, AuditProgramEnum program, String details, String createdAt
});




}
/// @nodoc
class __$AuditResponseModelCopyWithImpl<$Res>
    implements _$AuditResponseModelCopyWith<$Res> {
  __$AuditResponseModelCopyWithImpl(this._self, this._then);

  final _AuditResponseModel _self;
  final $Res Function(_AuditResponseModel) _then;

/// Create a copy of AuditResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? id = null,Object? action = null,Object? clientId = null,Object? clientName = null,Object? userId = freezed,Object? userName = null,Object? program = null,Object? details = null,Object? createdAt = null,}) {
  return _then(_AuditResponseModel(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int,action: null == action ? _self.action : action // ignore: cast_nullable_to_non_nullable
as AuditActionEnum,clientId: null == clientId ? _self.clientId : clientId // ignore: cast_nullable_to_non_nullable
as String,clientName: null == clientName ? _self.clientName : clientName // ignore: cast_nullable_to_non_nullable
as String,userId: freezed == userId ? _self.userId : userId // ignore: cast_nullable_to_non_nullable
as String?,userName: null == userName ? _self.userName : userName // ignore: cast_nullable_to_non_nullable
as String,program: null == program ? _self.program : program // ignore: cast_nullable_to_non_nullable
as AuditProgramEnum,details: null == details ? _self.details : details // ignore: cast_nullable_to_non_nullable
as String,createdAt: null == createdAt ? _self.createdAt : createdAt // ignore: cast_nullable_to_non_nullable
as String,
  ));
}


}

// dart format on
