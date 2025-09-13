// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'permission_response_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$PermissionResponseModel {

 int get id; String get upload_files; String get read_files;
/// Create a copy of PermissionResponseModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$PermissionResponseModelCopyWith<PermissionResponseModel> get copyWith => _$PermissionResponseModelCopyWithImpl<PermissionResponseModel>(this as PermissionResponseModel, _$identity);

  /// Serializes this PermissionResponseModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is PermissionResponseModel&&(identical(other.id, id) || other.id == id)&&(identical(other.upload_files, upload_files) || other.upload_files == upload_files)&&(identical(other.read_files, read_files) || other.read_files == read_files));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,upload_files,read_files);

@override
String toString() {
  return 'PermissionResponseModel(id: $id, upload_files: $upload_files, read_files: $read_files)';
}


}

/// @nodoc
abstract mixin class $PermissionResponseModelCopyWith<$Res>  {
  factory $PermissionResponseModelCopyWith(PermissionResponseModel value, $Res Function(PermissionResponseModel) _then) = _$PermissionResponseModelCopyWithImpl;
@useResult
$Res call({
 int id, String upload_files, String read_files
});




}
/// @nodoc
class _$PermissionResponseModelCopyWithImpl<$Res>
    implements $PermissionResponseModelCopyWith<$Res> {
  _$PermissionResponseModelCopyWithImpl(this._self, this._then);

  final PermissionResponseModel _self;
  final $Res Function(PermissionResponseModel) _then;

/// Create a copy of PermissionResponseModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? id = null,Object? upload_files = null,Object? read_files = null,}) {
  return _then(_self.copyWith(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int,upload_files: null == upload_files ? _self.upload_files : upload_files // ignore: cast_nullable_to_non_nullable
as String,read_files: null == read_files ? _self.read_files : read_files // ignore: cast_nullable_to_non_nullable
as String,
  ));
}

}


/// Adds pattern-matching-related methods to [PermissionResponseModel].
extension PermissionResponseModelPatterns on PermissionResponseModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _PermissionResponseModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _PermissionResponseModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _PermissionResponseModel value)  $default,){
final _that = this;
switch (_that) {
case _PermissionResponseModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _PermissionResponseModel value)?  $default,){
final _that = this;
switch (_that) {
case _PermissionResponseModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( int id,  String upload_files,  String read_files)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _PermissionResponseModel() when $default != null:
return $default(_that.id,_that.upload_files,_that.read_files);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( int id,  String upload_files,  String read_files)  $default,) {final _that = this;
switch (_that) {
case _PermissionResponseModel():
return $default(_that.id,_that.upload_files,_that.read_files);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( int id,  String upload_files,  String read_files)?  $default,) {final _that = this;
switch (_that) {
case _PermissionResponseModel() when $default != null:
return $default(_that.id,_that.upload_files,_that.read_files);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _PermissionResponseModel implements PermissionResponseModel {
  const _PermissionResponseModel({required this.id, required this.upload_files, required this.read_files});
  factory _PermissionResponseModel.fromJson(Map<String, dynamic> json) => _$PermissionResponseModelFromJson(json);

@override final  int id;
@override final  String upload_files;
@override final  String read_files;

/// Create a copy of PermissionResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$PermissionResponseModelCopyWith<_PermissionResponseModel> get copyWith => __$PermissionResponseModelCopyWithImpl<_PermissionResponseModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$PermissionResponseModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _PermissionResponseModel&&(identical(other.id, id) || other.id == id)&&(identical(other.upload_files, upload_files) || other.upload_files == upload_files)&&(identical(other.read_files, read_files) || other.read_files == read_files));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,id,upload_files,read_files);

@override
String toString() {
  return 'PermissionResponseModel(id: $id, upload_files: $upload_files, read_files: $read_files)';
}


}

/// @nodoc
abstract mixin class _$PermissionResponseModelCopyWith<$Res> implements $PermissionResponseModelCopyWith<$Res> {
  factory _$PermissionResponseModelCopyWith(_PermissionResponseModel value, $Res Function(_PermissionResponseModel) _then) = __$PermissionResponseModelCopyWithImpl;
@override @useResult
$Res call({
 int id, String upload_files, String read_files
});




}
/// @nodoc
class __$PermissionResponseModelCopyWithImpl<$Res>
    implements _$PermissionResponseModelCopyWith<$Res> {
  __$PermissionResponseModelCopyWithImpl(this._self, this._then);

  final _PermissionResponseModel _self;
  final $Res Function(_PermissionResponseModel) _then;

/// Create a copy of PermissionResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? id = null,Object? upload_files = null,Object? read_files = null,}) {
  return _then(_PermissionResponseModel(
id: null == id ? _self.id : id // ignore: cast_nullable_to_non_nullable
as int,upload_files: null == upload_files ? _self.upload_files : upload_files // ignore: cast_nullable_to_non_nullable
as String,read_files: null == read_files ? _self.read_files : read_files // ignore: cast_nullable_to_non_nullable
as String,
  ));
}


}

// dart format on
