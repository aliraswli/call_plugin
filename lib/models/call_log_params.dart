import 'package:call_plugin/enums/call_log_type_enum.dart';

class CallLogParams {
  int page;
  int perPage;
  List<CallLogTypeEnum>? types;
  List<String>? phones;
  String? subscriptionId;
  bool? isAnswered;
  bool? isUnknown;
  int? duration;

  CallLogParams({
    required this.page,
    this.perPage = 350,
    this.types,
    this.phones,
    this.subscriptionId,
    this.isAnswered,
    this.isUnknown,
    this.duration,
  });

  CallLogParams copyWith({
    int? page,
    int? perPage,
    List<CallLogTypeEnum>? types,
    List<String>? phones,
    String? subscriptionId,
    bool? isAnswered,
    bool? isUnknown,
    int? duration,
  }) {
    return CallLogParams(
      page: page ?? this.page,
      perPage: perPage ?? this.perPage,
      types: types ?? this.types,
      phones: phones ?? this.phones,
      subscriptionId: subscriptionId ?? this.subscriptionId,
      isAnswered: isAnswered ?? this.isAnswered,
      isUnknown: isUnknown ?? this.isUnknown,
      duration: duration ?? this.duration,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'page': page,
      'perPage': perPage,
      'callTypes': types?.map((e) => e.type).toList(),
      'phoneNumbers': phones,
      'subscriptionId': subscriptionId,
      'isAnswered': isAnswered,
      'isUnknown': isUnknown,
      'duration': duration,
    };
  }
}
