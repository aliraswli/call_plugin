import 'call_plugin_platform_interface.dart';

class CallPlugin {
  Future<Map<String, dynamic>?> getCallLogs({
    required int page,
    required int perPage,
    List<int>? callTypes,
    List<String>? phoneNumbers,
    String? subscriptionId,
    bool? answeredOnly,
  }) => CallPluginPlatform.instance.getCallLogs(
    page: page,
    perPage: perPage,
    answeredOnly: answeredOnly,
    callTypes: callTypes,
    phoneNumbers: phoneNumbers,
    subscriptionId: subscriptionId,
  );

  Future<List<Map>?> getSimCards() {
    return CallPluginPlatform.instance.getSimCards();
  }
}
