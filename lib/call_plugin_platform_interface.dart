import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'call_plugin_method_channel.dart';

abstract class CallPluginPlatform extends PlatformInterface {
  CallPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static CallPluginPlatform _instance = MethodChannelCallPlugin();

  static CallPluginPlatform get instance => _instance;

  static set instance(CallPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<Map<String, dynamic>?> getCallLogs({
    required int page,
    required int perPage,
    List<int>? callTypes,
    List<String>? phoneNumbers,
    String? subscriptionId,
    bool? answeredOnly,
  }) {
    throw UnimplementedError('getCallLogs() has not been implemented.');
  }

  Future<List<Map>?> getSimCards() {
    throw UnimplementedError('getCallLogs() has not been implemented.');
  }
}
