// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'jwt_data_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$JwtDataModel {

@JsonKey(name: 'sub') String get subject; int get iat;// Issued At
 int get exp;// Expiration Time
 String get id; String get role;
/// Create a copy of JwtDataModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$JwtDataModelCopyWith<JwtDataModel> get copyWith => _$JwtDataModelCopyWithImpl<JwtDataModel>(this as JwtDataModel, _$identity);

  /// Serializes this JwtDataModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is JwtDataModel&&(identical(other.subject, subject) || other.subject == subject)&&(identical(other.iat, iat) || other.iat == iat)&&(identical(other.exp, exp) || other.exp == exp)&&(identical(other.id, id) || other.id == id)&&(identical(other.role, role) || other.role == role));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,subject,iat,exp,id,role);

@override
String toString() {
  return 'JwtDataModel(subject: $subject, iat: $iat, exp: $exp, id: $id, role: $role)';
}


}

/// @nodoc
abstract mixin class $JwtDataModelCopyWith<$Res>  {
  factory $JwtDataModelCopyWith(JwtDataModel value, $Res Function(JwtDataModel) _then) = _$JwtDataModelCopyWithImpl;
@useResult
$Res call({
@JsonKey(name: 'sub') String subject, int iat, int exp, String id, String role
});




}
/// @nodoc
class _$JwtDataModelCopyWithImpl<$Res>
    implements $JwtDataModelCopyWith<$Res> {
  _$JwtDataModelCopyWithImpl(this._self, this._then);

  final JwtDataModel _self;
  final $Res Function(JwtDataModel) _then;

/// Create a copy of JwtDataModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? subject = null,Object? iat = null,Object? exp = null,Object? id = null,Object? role = null,}) {
  return _then(_self.copyWith(
subject: null == subject ? _self.subject : subject // ignore: cast_nullable_to_non_nullable
as String,iat: null == iat ? _self.iat : iat // ignore: cast_nullable_to_non_nullable
as int,exp: null == exp ? _self.exp : exp // ignore: cast_nullable_to_non_nullable
as int,id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as String,role: null == role ? _self.role : role // ignore: cast_nullable_to_non_nullable
as String,
  ));
}

}


/// Adds pattern-matching-related methods to [JwtDataModel].
extension JwtDataModelPatterns on JwtDataModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _JwtDataModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _JwtDataModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _JwtDataModel value)  $default,){
final _that = this;
switch (_that) {
case _JwtDataModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _JwtDataModel value)?  $default,){
final _that = this;
switch (_that) {
case _JwtDataModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function(@JsonKey(name: 'sub')  String subject,  int iat,  int exp,  String id,  String role)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _JwtDataModel() when $default != null:
return $default(_that.subject,_that.iat,_that.exp,_that.id,_that.role);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function(@JsonKey(name: 'sub')  String subject,  int iat,  int exp,  String id,  String role)  $default,) {final _that = this;
switch (_that) {
case _JwtDataModel():
return $default(_that.subject,_that.iat,_that.exp,_that.id,_that.role);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function(@JsonKey(name: 'sub')  String subject,  int iat,  int exp,  String id,  String role)?  $default,) {final _that = this;
switch (_that) {
case _JwtDataModel() when $default != null:
return $default(_that.subject,_that.iat,_that.exp,_that.id,_that.role);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _JwtDataModel implements JwtDataModel {
  const _JwtDataModel({@JsonKey(name: 'sub') required this.subject, required this.iat, required this.exp, required this.id, required this.role});
  factory _JwtDataModel.fromJson(Map<String, dynamic> json) => _$JwtDataModelFromJson(json);

@override@JsonKey(name: 'sub') final  String subject;
@override final  int iat;
// Issued At
@override final  int exp;
// Expiration Time
@override final  String id;
@override final  String role;

/// Create a copy of JwtDataModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$JwtDataModelCopyWith<_JwtDataModel> get copyWith => __$JwtDataModelCopyWithImpl<_JwtDataModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$JwtDataModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _JwtDataModel&&(identical(other.subject, subject) || other.subject == subject)&&(identical(other.iat, iat) || other.iat == iat)&&(identical(other.exp, exp) || other.exp == exp)&&(identical(other.id, id) || other.id == id)&&(identical(other.role, role) || other.role == role));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,subject,iat,exp,id,role);

@override
String toString() {
  return 'JwtDataModel(subject: $subject, iat: $iat, exp: $exp, id: $id, role: $role)';
}


}

/// @nodoc
abstract mixin class _$JwtDataModelCopyWith<$Res> implements $JwtDataModelCopyWith<$Res> {
  factory _$JwtDataModelCopyWith(_JwtDataModel value, $Res Function(_JwtDataModel) _then) = __$JwtDataModelCopyWithImpl;
@override @useResult
$Res call({
@JsonKey(name: 'sub') String subject, int iat, int exp, String id, String role
});




}
/// @nodoc
class __$JwtDataModelCopyWithImpl<$Res>
    implements _$JwtDataModelCopyWith<$Res> {
  __$JwtDataModelCopyWithImpl(this._self, this._then);

  final _JwtDataModel _self;
  final $Res Function(_JwtDataModel) _then;

/// Create a copy of JwtDataModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? subject = null,Object? iat = null,Object? exp = null,Object? id = null,Object? role = null,}) {
  return _then(_JwtDataModel(
subject: null == subject ? _self.subject : subject // ignore: cast_nullable_to_non_nullable
as String,iat: null == iat ? _self.iat : iat // ignore: cast_nullable_to_non_nullable
as int,exp: null == exp ? _self.exp : exp // ignore: cast_nullable_to_non_nullable
as int,id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as String,role: null == role ? _self.role : role // ignore: cast_nullable_to_non_nullable
as String,
  ));
}


}

// dart format on
