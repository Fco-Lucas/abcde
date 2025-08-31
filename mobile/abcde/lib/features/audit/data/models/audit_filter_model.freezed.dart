// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'audit_filter_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;
/// @nodoc
mixin _$AuditFilterModel {

 AuditActionEnum? get action; String? get client; String? get user; AuditProgramEnum? get program; String? get details; String? get startDate; String? get endDate;
/// Create a copy of AuditFilterModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$AuditFilterModelCopyWith<AuditFilterModel> get copyWith => _$AuditFilterModelCopyWithImpl<AuditFilterModel>(this as AuditFilterModel, _$identity);



@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is AuditFilterModel&&(identical(other.action, action) || other.action == action)&&(identical(other.client, client) || other.client == client)&&(identical(other.user, user) || other.user == user)&&(identical(other.program, program) || other.program == program)&&(identical(other.details, details) || other.details == details)&&(identical(other.startDate, startDate) || other.startDate == startDate)&&(identical(other.endDate, endDate) || other.endDate == endDate));
}


@override
int get hashCode => Object.hash(runtimeType,action,client,user,program,details,startDate,endDate);

@override
String toString() {
  return 'AuditFilterModel(action: $action, client: $client, user: $user, program: $program, details: $details, startDate: $startDate, endDate: $endDate)';
}


}

/// @nodoc
abstract mixin class $AuditFilterModelCopyWith<$Res>  {
  factory $AuditFilterModelCopyWith(AuditFilterModel value, $Res Function(AuditFilterModel) _then) = _$AuditFilterModelCopyWithImpl;
@useResult
$Res call({
 AuditActionEnum? action, String? client, String? user, AuditProgramEnum? program, String? details, String? startDate, String? endDate
});




}
/// @nodoc
class _$AuditFilterModelCopyWithImpl<$Res>
    implements $AuditFilterModelCopyWith<$Res> {
  _$AuditFilterModelCopyWithImpl(this._self, this._then);

  final AuditFilterModel _self;
  final $Res Function(AuditFilterModel) _then;

/// Create a copy of AuditFilterModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? action = freezed,Object? client = freezed,Object? user = freezed,Object? program = freezed,Object? details = freezed,Object? startDate = freezed,Object? endDate = freezed,}) {
  return _then(_self.copyWith(
action: freezed == action ? _self.action : action // ignore: cast_nullable_to_non_nullable
as AuditActionEnum?,client: freezed == client ? _self.client : client // ignore: cast_nullable_to_non_nullable
as String?,user: freezed == user ? _self.user : user // ignore: cast_nullable_to_non_nullable
as String?,program: freezed == program ? _self.program : program // ignore: cast_nullable_to_non_nullable
as AuditProgramEnum?,details: freezed == details ? _self.details : details // ignore: cast_nullable_to_non_nullable
as String?,startDate: freezed == startDate ? _self.startDate : startDate // ignore: cast_nullable_to_non_nullable
as String?,endDate: freezed == endDate ? _self.endDate : endDate // ignore: cast_nullable_to_non_nullable
as String?,
  ));
}

}


/// Adds pattern-matching-related methods to [AuditFilterModel].
extension AuditFilterModelPatterns on AuditFilterModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _AuditFilterModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _AuditFilterModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _AuditFilterModel value)  $default,){
final _that = this;
switch (_that) {
case _AuditFilterModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _AuditFilterModel value)?  $default,){
final _that = this;
switch (_that) {
case _AuditFilterModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( AuditActionEnum? action,  String? client,  String? user,  AuditProgramEnum? program,  String? details,  String? startDate,  String? endDate)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _AuditFilterModel() when $default != null:
return $default(_that.action,_that.client,_that.user,_that.program,_that.details,_that.startDate,_that.endDate);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( AuditActionEnum? action,  String? client,  String? user,  AuditProgramEnum? program,  String? details,  String? startDate,  String? endDate)  $default,) {final _that = this;
switch (_that) {
case _AuditFilterModel():
return $default(_that.action,_that.client,_that.user,_that.program,_that.details,_that.startDate,_that.endDate);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( AuditActionEnum? action,  String? client,  String? user,  AuditProgramEnum? program,  String? details,  String? startDate,  String? endDate)?  $default,) {final _that = this;
switch (_that) {
case _AuditFilterModel() when $default != null:
return $default(_that.action,_that.client,_that.user,_that.program,_that.details,_that.startDate,_that.endDate);case _:
  return null;

}
}

}

/// @nodoc


class _AuditFilterModel implements AuditFilterModel {
  const _AuditFilterModel({this.action, this.client, this.user, this.program, this.details, this.startDate, this.endDate});
  

@override final  AuditActionEnum? action;
@override final  String? client;
@override final  String? user;
@override final  AuditProgramEnum? program;
@override final  String? details;
@override final  String? startDate;
@override final  String? endDate;

/// Create a copy of AuditFilterModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$AuditFilterModelCopyWith<_AuditFilterModel> get copyWith => __$AuditFilterModelCopyWithImpl<_AuditFilterModel>(this, _$identity);



@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _AuditFilterModel&&(identical(other.action, action) || other.action == action)&&(identical(other.client, client) || other.client == client)&&(identical(other.user, user) || other.user == user)&&(identical(other.program, program) || other.program == program)&&(identical(other.details, details) || other.details == details)&&(identical(other.startDate, startDate) || other.startDate == startDate)&&(identical(other.endDate, endDate) || other.endDate == endDate));
}


@override
int get hashCode => Object.hash(runtimeType,action,client,user,program,details,startDate,endDate);

@override
String toString() {
  return 'AuditFilterModel(action: $action, client: $client, user: $user, program: $program, details: $details, startDate: $startDate, endDate: $endDate)';
}


}

/// @nodoc
abstract mixin class _$AuditFilterModelCopyWith<$Res> implements $AuditFilterModelCopyWith<$Res> {
  factory _$AuditFilterModelCopyWith(_AuditFilterModel value, $Res Function(_AuditFilterModel) _then) = __$AuditFilterModelCopyWithImpl;
@override @useResult
$Res call({
 AuditActionEnum? action, String? client, String? user, AuditProgramEnum? program, String? details, String? startDate, String? endDate
});




}
/// @nodoc
class __$AuditFilterModelCopyWithImpl<$Res>
    implements _$AuditFilterModelCopyWith<$Res> {
  __$AuditFilterModelCopyWithImpl(this._self, this._then);

  final _AuditFilterModel _self;
  final $Res Function(_AuditFilterModel) _then;

/// Create a copy of AuditFilterModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? action = freezed,Object? client = freezed,Object? user = freezed,Object? program = freezed,Object? details = freezed,Object? startDate = freezed,Object? endDate = freezed,}) {
  return _then(_AuditFilterModel(
action: freezed == action ? _self.action : action // ignore: cast_nullable_to_non_nullable
as AuditActionEnum?,client: freezed == client ? _self.client : client // ignore: cast_nullable_to_non_nullable
as String?,user: freezed == user ? _self.user : user // ignore: cast_nullable_to_non_nullable
as String?,program: freezed == program ? _self.program : program // ignore: cast_nullable_to_non_nullable
as AuditProgramEnum?,details: freezed == details ? _self.details : details // ignore: cast_nullable_to_non_nullable
as String?,startDate: freezed == startDate ? _self.startDate : startDate // ignore: cast_nullable_to_non_nullable
as String?,endDate: freezed == endDate ? _self.endDate : endDate // ignore: cast_nullable_to_non_nullable
as String?,
  ));
}


}

// dart format on
