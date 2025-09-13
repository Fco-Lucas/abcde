// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'client_pageable_response_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$ClientPageableResponseModel {

 List<ClientResponseModel> get content; bool get first; bool get end; int get page; int get size; int get pageElements; int get totalElements; int get totalPages;
/// Create a copy of ClientPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$ClientPageableResponseModelCopyWith<ClientPageableResponseModel> get copyWith => _$ClientPageableResponseModelCopyWithImpl<ClientPageableResponseModel>(this as ClientPageableResponseModel, _$identity);

  /// Serializes this ClientPageableResponseModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is ClientPageableResponseModel&&const DeepCollectionEquality().equals(other.content, content)&&(identical(other.first, first) || other.first == first)&&(identical(other.end, end) || other.end == end)&&(identical(other.page, page) || other.page == page)&&(identical(other.size, size) || other.size == size)&&(identical(other.pageElements, pageElements) || other.pageElements == pageElements)&&(identical(other.totalElements, totalElements) || other.totalElements == totalElements)&&(identical(other.totalPages, totalPages) || other.totalPages == totalPages));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,const DeepCollectionEquality().hash(content),first,end,page,size,pageElements,totalElements,totalPages);

@override
String toString() {
  return 'ClientPageableResponseModel(content: $content, first: $first, end: $end, page: $page, size: $size, pageElements: $pageElements, totalElements: $totalElements, totalPages: $totalPages)';
}


}

/// @nodoc
abstract mixin class $ClientPageableResponseModelCopyWith<$Res>  {
  factory $ClientPageableResponseModelCopyWith(ClientPageableResponseModel value, $Res Function(ClientPageableResponseModel) _then) = _$ClientPageableResponseModelCopyWithImpl;
@useResult
$Res call({
 List<ClientResponseModel> content, bool first, bool end, int page, int size, int pageElements, int totalElements, int totalPages
});




}
/// @nodoc
class _$ClientPageableResponseModelCopyWithImpl<$Res>
    implements $ClientPageableResponseModelCopyWith<$Res> {
  _$ClientPageableResponseModelCopyWithImpl(this._self, this._then);

  final ClientPageableResponseModel _self;
  final $Res Function(ClientPageableResponseModel) _then;

/// Create a copy of ClientPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? content = null,Object? first = null,Object? end = null,Object? page = null,Object? size = null,Object? pageElements = null,Object? totalElements = null,Object? totalPages = null,}) {
  return _then(_self.copyWith(
content: null == content ? _self.content : content // ignore: cast_nullable_to_non_nullable
as List<ClientResponseModel>,first: null == first ? _self.first : first // ignore: cast_nullable_to_non_nullable
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


/// Adds pattern-matching-related methods to [ClientPageableResponseModel].
extension ClientPageableResponseModelPatterns on ClientPageableResponseModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _ClientPageableResponseModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _ClientPageableResponseModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _ClientPageableResponseModel value)  $default,){
final _that = this;
switch (_that) {
case _ClientPageableResponseModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _ClientPageableResponseModel value)?  $default,){
final _that = this;
switch (_that) {
case _ClientPageableResponseModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( List<ClientResponseModel> content,  bool first,  bool end,  int page,  int size,  int pageElements,  int totalElements,  int totalPages)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _ClientPageableResponseModel() when $default != null:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( List<ClientResponseModel> content,  bool first,  bool end,  int page,  int size,  int pageElements,  int totalElements,  int totalPages)  $default,) {final _that = this;
switch (_that) {
case _ClientPageableResponseModel():
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( List<ClientResponseModel> content,  bool first,  bool end,  int page,  int size,  int pageElements,  int totalElements,  int totalPages)?  $default,) {final _that = this;
switch (_that) {
case _ClientPageableResponseModel() when $default != null:
return $default(_that.content,_that.first,_that.end,_that.page,_that.size,_that.pageElements,_that.totalElements,_that.totalPages);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _ClientPageableResponseModel implements ClientPageableResponseModel {
  const _ClientPageableResponseModel({required final  List<ClientResponseModel> content, required this.first, required this.end, required this.page, required this.size, required this.pageElements, required this.totalElements, required this.totalPages}): _content = content;
  factory _ClientPageableResponseModel.fromJson(Map<String, dynamic> json) => _$ClientPageableResponseModelFromJson(json);

 final  List<ClientResponseModel> _content;
@override List<ClientResponseModel> get content {
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

/// Create a copy of ClientPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$ClientPageableResponseModelCopyWith<_ClientPageableResponseModel> get copyWith => __$ClientPageableResponseModelCopyWithImpl<_ClientPageableResponseModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$ClientPageableResponseModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _ClientPageableResponseModel&&const DeepCollectionEquality().equals(other._content, _content)&&(identical(other.first, first) || other.first == first)&&(identical(other.end, end) || other.end == end)&&(identical(other.page, page) || other.page == page)&&(identical(other.size, size) || other.size == size)&&(identical(other.pageElements, pageElements) || other.pageElements == pageElements)&&(identical(other.totalElements, totalElements) || other.totalElements == totalElements)&&(identical(other.totalPages, totalPages) || other.totalPages == totalPages));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,const DeepCollectionEquality().hash(_content),first,end,page,size,pageElements,totalElements,totalPages);

@override
String toString() {
  return 'ClientPageableResponseModel(content: $content, first: $first, end: $end, page: $page, size: $size, pageElements: $pageElements, totalElements: $totalElements, totalPages: $totalPages)';
}


}

/// @nodoc
abstract mixin class _$ClientPageableResponseModelCopyWith<$Res> implements $ClientPageableResponseModelCopyWith<$Res> {
  factory _$ClientPageableResponseModelCopyWith(_ClientPageableResponseModel value, $Res Function(_ClientPageableResponseModel) _then) = __$ClientPageableResponseModelCopyWithImpl;
@override @useResult
$Res call({
 List<ClientResponseModel> content, bool first, bool end, int page, int size, int pageElements, int totalElements, int totalPages
});




}
/// @nodoc
class __$ClientPageableResponseModelCopyWithImpl<$Res>
    implements _$ClientPageableResponseModelCopyWith<$Res> {
  __$ClientPageableResponseModelCopyWithImpl(this._self, this._then);

  final _ClientPageableResponseModel _self;
  final $Res Function(_ClientPageableResponseModel) _then;

/// Create a copy of ClientPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? content = null,Object? first = null,Object? end = null,Object? page = null,Object? size = null,Object? pageElements = null,Object? totalElements = null,Object? totalPages = null,}) {
  return _then(_ClientPageableResponseModel(
content: null == content ? _self._content : content // ignore: cast_nullable_to_non_nullable
as List<ClientResponseModel>,first: null == first ? _self.first : first // ignore: cast_nullable_to_non_nullable
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
