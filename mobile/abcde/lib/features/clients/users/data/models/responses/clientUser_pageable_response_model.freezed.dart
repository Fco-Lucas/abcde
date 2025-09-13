// GENERATED CODE - DO NOT MODIFY BY HAND
// coverage:ignore-file
// ignore_for_file: type=lint
// ignore_for_file: unused_element, deprecated_member_use, deprecated_member_use_from_same_package, use_function_type_syntax_for_parameters, unnecessary_const, avoid_init_to_null, invalid_override_different_default_values_named, prefer_expression_function_bodies, annotate_overrides, invalid_annotation_target, unnecessary_question_mark

part of 'clientUser_pageable_response_model.dart';

// **************************************************************************
// FreezedGenerator
// **************************************************************************

// dart format off
T _$identity<T>(T value) => value;

/// @nodoc
mixin _$ClientuserPageableResponseModel {

 List<ClientuserResponseModel> get content; bool get first; bool get end; int get page; int get size; int get pageElements; int get totalElements; int get totalPages;
/// Create a copy of ClientuserPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
$ClientuserPageableResponseModelCopyWith<ClientuserPageableResponseModel> get copyWith => _$ClientuserPageableResponseModelCopyWithImpl<ClientuserPageableResponseModel>(this as ClientuserPageableResponseModel, _$identity);

  /// Serializes this ClientuserPageableResponseModel to a JSON map.
  Map<String, dynamic> toJson();


@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is ClientuserPageableResponseModel&&const DeepCollectionEquality().equals(other.content, content)&&(identical(other.first, first) || other.first == first)&&(identical(other.end, end) || other.end == end)&&(identical(other.page, page) || other.page == page)&&(identical(other.size, size) || other.size == size)&&(identical(other.pageElements, pageElements) || other.pageElements == pageElements)&&(identical(other.totalElements, totalElements) || other.totalElements == totalElements)&&(identical(other.totalPages, totalPages) || other.totalPages == totalPages));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,const DeepCollectionEquality().hash(content),first,end,page,size,pageElements,totalElements,totalPages);

@override
String toString() {
  return 'ClientuserPageableResponseModel(content: $content, first: $first, end: $end, page: $page, size: $size, pageElements: $pageElements, totalElements: $totalElements, totalPages: $totalPages)';
}


}

/// @nodoc
abstract mixin class $ClientuserPageableResponseModelCopyWith<$Res>  {
  factory $ClientuserPageableResponseModelCopyWith(ClientuserPageableResponseModel value, $Res Function(ClientuserPageableResponseModel) _then) = _$ClientuserPageableResponseModelCopyWithImpl;
@useResult
$Res call({
 List<ClientuserResponseModel> content, bool first, bool end, int page, int size, int pageElements, int totalElements, int totalPages
});




}
/// @nodoc
class _$ClientuserPageableResponseModelCopyWithImpl<$Res>
    implements $ClientuserPageableResponseModelCopyWith<$Res> {
  _$ClientuserPageableResponseModelCopyWithImpl(this._self, this._then);

  final ClientuserPageableResponseModel _self;
  final $Res Function(ClientuserPageableResponseModel) _then;

/// Create a copy of ClientuserPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@pragma('vm:prefer-inline') @override $Res call({Object? content = null,Object? first = null,Object? end = null,Object? page = null,Object? size = null,Object? pageElements = null,Object? totalElements = null,Object? totalPages = null,}) {
  return _then(_self.copyWith(
content: null == content ? _self.content : content // ignore: cast_nullable_to_non_nullable
as List<ClientuserResponseModel>,first: null == first ? _self.first : first // ignore: cast_nullable_to_non_nullable
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


/// Adds pattern-matching-related methods to [ClientuserPageableResponseModel].
extension ClientuserPageableResponseModelPatterns on ClientuserPageableResponseModel {
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

@optionalTypeArgs TResult maybeMap<TResult extends Object?>(TResult Function( _ClientuserPageableResponseModel value)?  $default,{required TResult orElse(),}){
final _that = this;
switch (_that) {
case _ClientuserPageableResponseModel() when $default != null:
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

@optionalTypeArgs TResult map<TResult extends Object?>(TResult Function( _ClientuserPageableResponseModel value)  $default,){
final _that = this;
switch (_that) {
case _ClientuserPageableResponseModel():
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

@optionalTypeArgs TResult? mapOrNull<TResult extends Object?>(TResult? Function( _ClientuserPageableResponseModel value)?  $default,){
final _that = this;
switch (_that) {
case _ClientuserPageableResponseModel() when $default != null:
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

@optionalTypeArgs TResult maybeWhen<TResult extends Object?>(TResult Function( List<ClientuserResponseModel> content,  bool first,  bool end,  int page,  int size,  int pageElements,  int totalElements,  int totalPages)?  $default,{required TResult orElse(),}) {final _that = this;
switch (_that) {
case _ClientuserPageableResponseModel() when $default != null:
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

@optionalTypeArgs TResult when<TResult extends Object?>(TResult Function( List<ClientuserResponseModel> content,  bool first,  bool end,  int page,  int size,  int pageElements,  int totalElements,  int totalPages)  $default,) {final _that = this;
switch (_that) {
case _ClientuserPageableResponseModel():
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

@optionalTypeArgs TResult? whenOrNull<TResult extends Object?>(TResult? Function( List<ClientuserResponseModel> content,  bool first,  bool end,  int page,  int size,  int pageElements,  int totalElements,  int totalPages)?  $default,) {final _that = this;
switch (_that) {
case _ClientuserPageableResponseModel() when $default != null:
return $default(_that.content,_that.first,_that.end,_that.page,_that.size,_that.pageElements,_that.totalElements,_that.totalPages);case _:
  return null;

}
}

}

/// @nodoc
@JsonSerializable()

class _ClientuserPageableResponseModel implements ClientuserPageableResponseModel {
  const _ClientuserPageableResponseModel({required final  List<ClientuserResponseModel> content, required this.first, required this.end, required this.page, required this.size, required this.pageElements, required this.totalElements, required this.totalPages}): _content = content;
  factory _ClientuserPageableResponseModel.fromJson(Map<String, dynamic> json) => _$ClientuserPageableResponseModelFromJson(json);

 final  List<ClientuserResponseModel> _content;
@override List<ClientuserResponseModel> get content {
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

/// Create a copy of ClientuserPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @JsonKey(includeFromJson: false, includeToJson: false)
@pragma('vm:prefer-inline')
_$ClientuserPageableResponseModelCopyWith<_ClientuserPageableResponseModel> get copyWith => __$ClientuserPageableResponseModelCopyWithImpl<_ClientuserPageableResponseModel>(this, _$identity);

@override
Map<String, dynamic> toJson() {
  return _$ClientuserPageableResponseModelToJson(this, );
}

@override
bool operator ==(Object other) {
  return identical(this, other) || (other.runtimeType == runtimeType&&other is _ClientuserPageableResponseModel&&const DeepCollectionEquality().equals(other._content, _content)&&(identical(other.first, first) || other.first == first)&&(identical(other.end, end) || other.end == end)&&(identical(other.page, page) || other.page == page)&&(identical(other.size, size) || other.size == size)&&(identical(other.pageElements, pageElements) || other.pageElements == pageElements)&&(identical(other.totalElements, totalElements) || other.totalElements == totalElements)&&(identical(other.totalPages, totalPages) || other.totalPages == totalPages));
}

@JsonKey(includeFromJson: false, includeToJson: false)
@override
int get hashCode => Object.hash(runtimeType,const DeepCollectionEquality().hash(_content),first,end,page,size,pageElements,totalElements,totalPages);

@override
String toString() {
  return 'ClientuserPageableResponseModel(content: $content, first: $first, end: $end, page: $page, size: $size, pageElements: $pageElements, totalElements: $totalElements, totalPages: $totalPages)';
}


}

/// @nodoc
abstract mixin class _$ClientuserPageableResponseModelCopyWith<$Res> implements $ClientuserPageableResponseModelCopyWith<$Res> {
  factory _$ClientuserPageableResponseModelCopyWith(_ClientuserPageableResponseModel value, $Res Function(_ClientuserPageableResponseModel) _then) = __$ClientuserPageableResponseModelCopyWithImpl;
@override @useResult
$Res call({
 List<ClientuserResponseModel> content, bool first, bool end, int page, int size, int pageElements, int totalElements, int totalPages
});




}
/// @nodoc
class __$ClientuserPageableResponseModelCopyWithImpl<$Res>
    implements _$ClientuserPageableResponseModelCopyWith<$Res> {
  __$ClientuserPageableResponseModelCopyWithImpl(this._self, this._then);

  final _ClientuserPageableResponseModel _self;
  final $Res Function(_ClientuserPageableResponseModel) _then;

/// Create a copy of ClientuserPageableResponseModel
/// with the given fields replaced by the non-null parameter values.
@override @pragma('vm:prefer-inline') $Res call({Object? content = null,Object? first = null,Object? end = null,Object? page = null,Object? size = null,Object? pageElements = null,Object? totalElements = null,Object? totalPages = null,}) {
  return _then(_ClientuserPageableResponseModel(
content: null == content ? _self._content : content // ignore: cast_nullable_to_non_nullable
as List<ClientuserResponseModel>,first: null == first ? _self.first : first // ignore: cast_nullable_to_non_nullable
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
