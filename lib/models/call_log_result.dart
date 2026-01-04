import 'package:call_plugin/models/call_log_model.dart';

class CallLogResult {
  CallLogResult({this.items, this.currentPage, this.perPage, this.totalPage});

  CallLogResult.fromJson(dynamic json) {
    if (json['items'] != null) {
      items = [];
      json['items'].forEach((v) {
        items?.add(CallLogModel.fromJson(v));
      });
    }
    currentPage = json['currentPage'];
    perPage = json['perPage'];
    totalPage = json['totalPage'];
  }

  List<CallLogModel>? items;
  num? currentPage;
  num? perPage;
  num? totalPage;

  CallLogResult copyWith({
    List<CallLogModel>? items,
    num? currentPage,
    num? perPage,
    num? totalPage,
  }) => CallLogResult(
    items: items ?? this.items,
    currentPage: currentPage ?? this.currentPage,
    perPage: perPage ?? this.perPage,
    totalPage: totalPage ?? this.totalPage,
  );

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    if (items != null) {
      map['items'] = items?.map((e) => e.toJson()).toList();
    }
    map['currentPage'] = currentPage;
    map['perPage'] = perPage;
    map['totalPage'] = totalPage;
    return map;
  }
}
