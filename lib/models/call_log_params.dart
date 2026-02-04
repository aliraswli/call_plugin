import 'package:call_plugin/enums/call_log_type_enum.dart';

class CallLogParams {
  int page;
  int perPage;
  List<CallLogTypeEnum>? types;
  List<String>? phones;
  String? subscriptionId;
  bool? answeredOnly;
  int? duration;

  CallLogParams({
    required this.page,
    this.perPage = 15,
    this.types,
    this.phones,
    this.subscriptionId,
    this.answeredOnly,
    this.duration,
  });

  CallLogParams copyWith({
    int? page,
    int? perPage,
    List<CallLogTypeEnum>? types,
    List<String>? phones,
    String? subscriptionId,
    bool? answeredOnly,
    int? duration,
  }) {
    return CallLogParams(
      page: page ?? this.page,
      perPage: perPage ?? this.perPage,
      types: types ?? this.types,
      phones: phones ?? this.phones,
      subscriptionId: subscriptionId ?? this.subscriptionId,
      answeredOnly: answeredOnly ?? this.answeredOnly,
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
      'answeredOnly': answeredOnly,
      'duration': duration,
    };
  }
}
