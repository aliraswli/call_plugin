import 'package:call_plugin/enums/call_log_type_enum.dart';

class CallLogModel {
  const CallLogModel({
    this.id,
    this.number,
    this.name,
    this.type,
    this.date,
    this.duration,
    this.subscriptionId,
  });

  factory CallLogModel.fromJson(dynamic json) {
    return CallLogModel(
      id: json['id'],
      number: json['number'],
      name: json['name'],
      type: CallLogTypeEnum.fromValue(json['type'] ?? 0),
      date: json['date'],
      duration: json['duration'],
      subscriptionId: json['subscriptionId'],
    );
  }

  final String? id;
  final String? number;
  final String? name;
  final CallLogTypeEnum? type;
  final num? date;
  final num? duration;
  final String? subscriptionId;

  CallLogModel copyWith({
    String? id,
    String? number,
    String? name,
    CallLogTypeEnum? type,
    num? date,
    num? duration,
    String? subscriptionId,
  }) => CallLogModel(
    id: id ?? this.id,
    number: number ?? this.number,
    name: name ?? this.name,
    type: type ?? this.type,
    date: date ?? this.date,
    duration: duration ?? this.duration,
    subscriptionId: subscriptionId ?? this.subscriptionId,
  );

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['id'] = id;
    map['number'] = number;
    map['name'] = name;
    map['type'] = type;
    map['date'] = date;
    map['duration'] = duration;
    map['subscriptionId'] = subscriptionId;
    return map;
  }
}
