import 'package:call_plugin/models/call_log_params.dart';

import 'call_plugin_platform_interface.dart';
import 'models/sim_card_model.dart';

class CallPlugin {
  Future<Map<String, dynamic>?> getCallLogs({required CallLogParams params}) {
    return CallPluginPlatform.instance.getCallLogs(params);
  }

  Future<List<SimCardModel>?> getSimCards() {
    return CallPluginPlatform.instance.getSimCards();
  }

  Future<bool> deleteCallLogById(String callLogId) {
    return CallPluginPlatform.instance.deleteCallLogById(callLogId);
  }

  Future<bool> deleteCallLogByPhone(String phoneNumber) {
    return CallPluginPlatform.instance.deleteCallLogByPhone(phoneNumber);
  }
}
