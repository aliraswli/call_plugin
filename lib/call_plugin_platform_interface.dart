import 'package:call_plugin/models/call_log_params.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'call_plugin_method_channel.dart';
import 'models/sim_card_model.dart';

abstract class CallPluginPlatform extends PlatformInterface {
  CallPluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static CallPluginPlatform _instance = MethodChannelCallPlugin();

  static CallPluginPlatform get instance => _instance;

  static set instance(CallPluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<Map<String, dynamic>?> getCallLogs(CallLogParams params) {
    throw UnimplementedError('getCallLogs() has not been implemented.');
  }

  Future<List<SimCardModel>?> getSimCards() {
    throw UnimplementedError('getCallLogs() has not been implemented.');
  }
}
