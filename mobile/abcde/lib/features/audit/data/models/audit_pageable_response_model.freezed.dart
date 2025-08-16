// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'audit_pageable_response_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$AuditPageableResponseModel {

 List<AuditResponseModel> get content; bool get first; bool get end; int get page; int get size; int get pageElements; int get totalElements; int get totalPages;
/// Create a copy of AuditPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$AuditPageableResponseModelCopyWith<AuditPageableResponseModel> get copyWith => _$AuditPageableResponseModelCopyWithImpl<AuditPageableResponseModel>(this as AuditPageableResponseModel, _$identity);

  /// Serializes this AuditPageableResponseModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is AuditPageableResponseModel&&const DeepCollectionEquality().equals(other.content, content)&&(identical(other.first, first) || other.first == first)&&(identical(other.end, end) || other.end == end)&&(identical(other.page, page) || other.page == page)&&(identical(other.size, size) || other.size == size)&&(identical(other.pageElements, pageElements) || other.pageElements == pageElements)&&(identical(other.totalElements, totalElements) || other.totalElements == totalElements)&&(identical(other.totalPages, totalPages) || other.totalPages == totalPages));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,const DeepCollectionEquality().hash(content),first,end,page,size,pageElements,totalElements,totalPages);

@override
String toString() {
  return 'AuditPageableResponseModel(content: $content, first: $first, end: $end, page: $page, size: $size, pageElements: $pageElements, totalElements: $totalElements, totalPages: $totalPages)';
}


}

/// @nodoc
abstract mixin class $AuditPageableResponseModelCopyWith<$Res>  {
  factory $AuditPageableResponseModelCopyWith(AuditPageableResponseModel value, $Res Function(AuditPageableResponseModel) _then) = _$AuditPageableResponseModelCopyWithImpl;
@useResult
$Res call({
 List<AuditResponseModel> content, bool first, bool end, int page, int size, int pageElements, int totalElements, int totalPages
});




}
/// @nodoc
class _$AuditPageableResponseModelCopyWithImpl<$Res>
    implements $AuditPageableResponseModelCopyWith<$Res> {
  _$AuditPageableResponseModelCopyWithImpl(this._self, this._then);

  final AuditPageableResponseModel _self;
  final $Res Function(AuditPageableResponseModel) _then;

/// Create a copy of AuditPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? content = null,Object? first = null,Object? end = null,Object? page = null,Object? size = null,Object? pageElements = null,Object? totalElements = null,Object? totalPages = null,}) {
  return _then(_self.copyWith(
content: null == content ? _self.content : content // ignore: cast_nullable_to_non_nullable
as List<AuditResponseModel>,first: null == first ? _self.first : first // ignore: cast_nullable_to_non_nullable
as bool,end: null == end ? _self.end : end // ignore: cast_nullable_to_non_nullable
as bool,page: null == page ? _self.page : page // ignore: cast_nullable_to_non_nullable
as int,size: null == size ? _self.size : size // ignore: cast_nullable_to_non_nullable
as int,pageElements: null == pageElements ? _self.pageElements : pageElements // ignore: cast_nullable_to_non_nullable
as int,totalElements: null == totalElements ? _self.totalElements : totalElements // ignore: cast_nullable_to_non_nullable
as int,totalPages: null == totalPages ? _self.totalPages : totalPages // ignore: cast_nullable_to_non_nullable
as int,
  ));
}

}


/// Adds pattern-matching-related methods to [AuditPageableResponseModel].
extension AuditPageableResponseModelPatterns on AuditPageableResponseModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _AuditPageableResponseModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _AuditPageableResponseModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _AuditPageableResponseModel value)  $default,){
final _that = this;
switch (_that) {
case _AuditPageableResponseModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _AuditPageableResponseModel value)?  $default,){
final _that = this;
switch (_that) {
case _AuditPageableResponseModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( List<AuditResponseModel> content,  bool first,  bool end,  int page,  int size,  int pageElements,  int totalElements,  int totalPages)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _AuditPageableResponseModel() when $default != null:
return $default(_that.content,_that.first,_that.end,_that.page,_that.size,_that.pageElements,_that.totalElements,_that.totalPages);case _:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( List<AuditResponseModel> content,  bool first,  bool end,  int page,  int size,  int pageElements,  int totalElements,  int totalPages)  $default,) {final _that = this;
switch (_that) {
case _AuditPageableResponseModel():
return $default(_that.content,_that.first,_that.end,_that.page,_that.size,_that.pageElements,_that.totalElements,_that.totalPages);case _:
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( List<AuditResponseModel> content,  bool first,  bool end,  int page,  int size,  int pageElements,  int totalElements,  int totalPages)?  $default,) {final _that = this;
switch (_that) {
case _AuditPageableResponseModel() when $default != null:
return $default(_that.content,_that.first,_that.end,_that.page,_that.size,_that.pageElements,_that.totalElements,_that.totalPages);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _AuditPageableResponseModel implements AuditPageableResponseModel {
  const _AuditPageableResponseModel({required final  List<AuditResponseModel> content, required this.first, required this.end, required this.page, required this.size, required this.pageElements, required this.totalElements, required this.totalPages}): _content = content;
  factory _AuditPageableResponseModel.fromJson(Map<String, dynamic> json) => _$AuditPageableResponseModelFromJson(json);

 final  List<AuditResponseModel> _content;
@override List<AuditResponseModel> get content {
  if (_content is EqualUnmodifiableListView) return _content;
  // ignore: implicit_dynamic_type
  return EqualUnmodifiableListView(_content);
}

@override final  bool first;
@override final  bool end;
@override final  int page;
@override final  int size;
@override final  int pageElements;
@override final  int totalElements;
@override final  int totalPages;

/// Create a copy of AuditPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$AuditPageableResponseModelCopyWith<_AuditPageableResponseModel> get copyWith => __$AuditPageableResponseModelCopyWithImpl<_AuditPageableResponseModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$AuditPageableResponseModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _AuditPageableResponseModel&&const DeepCollectionEquality().equals(other._content, _content)&&(identical(other.first, first) || other.first == first)&&(identical(other.end, end) || other.end == end)&&(identical(other.page, page) || other.page == page)&&(identical(other.size, size) || other.size == size)&&(identical(other.pageElements, pageElements) || other.pageElements == pageElements)&&(identical(other.totalElements, totalElements) || other.totalElements == totalElements)&&(identical(other.totalPages, totalPages) || other.totalPages == totalPages));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,const DeepCollectionEquality().hash(_content),first,end,page,size,pageElements,totalElements,totalPages);

@override
String toString() {
  return 'AuditPageableResponseModel(content: $content, first: $first, end: $end, page: $page, size: $size, pageElements: $pageElements, totalElements: $totalElements, totalPages: $totalPages)';
}


}

/// @nodoc
abstract mixin class _$AuditPageableResponseModelCopyWith<$Res> implements $AuditPageableResponseModelCopyWith<$Res> {
  factory _$AuditPageableResponseModelCopyWith(_AuditPageableResponseModel value, $Res Function(_AuditPageableResponseModel) _then) = __$AuditPageableResponseModelCopyWithImpl;
@override @useResult
$Res call({
 List<AuditResponseModel> content, bool first, bool end, int page, int size, int pageElements, int totalElements, int totalPages
});




}
/// @nodoc
class __$AuditPageableResponseModelCopyWithImpl<$Res>
    implements _$AuditPageableResponseModelCopyWith<$Res> {
  __$AuditPageableResponseModelCopyWithImpl(this._self, this._then);

  final _AuditPageableResponseModel _self;
  final $Res Function(_AuditPageableResponseModel) _then;

/// Create a copy of AuditPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? content = null,Object? first = null,Object? end = null,Object? page = null,Object? size = null,Object? pageElements = null,Object? totalElements = null,Object? totalPages = null,}) {
  return _then(_AuditPageableResponseModel(
content: null == content ? _self._content : content // ignore: cast_nullable_to_non_nullable
as List<AuditResponseModel>,first: null == first ? _self.first : first // ignore: cast_nullable_to_non_nullable
as bool,end: null == end ? _self.end : end // ignore: cast_nullable_to_non_nullable
as bool,page: null == page ? _self.page : page // ignore: cast_nullable_to_non_nullable
as int,size: null == size ? _self.size : size // ignore: cast_nullable_to_non_nullable
as int,pageElements: null == pageElements ? _self.pageElements : pageElements // ignore: cast_nullable_to_non_nullable
as int,totalElements: null == totalElements ? _self.totalElements : totalElements // ignore: cast_nullable_to_non_nullable
as int,totalPages: null == totalPages ? _self.totalPages : totalPages // ignore: cast_nullable_to_non_nullable
as int,
  ));
}


}

// dart format on
