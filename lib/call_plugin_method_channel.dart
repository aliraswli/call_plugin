import 'dart:developer';

import 'package:call_plugin/models/call_log_params.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'call_plugin_platform_interface.dart';
import 'models/sim_card_model.dart';

class MethodChannelCallPlugin extends CallPluginPlatform {
  @visibleForTesting
  final methodChannel = const MethodChannel('call_plugin');

  @override
  Future<Map<String, dynamic>?> getCallLogs(CallLogParams params) async {
    try {
      final result = await methodChannel.invokeMethod(
        'getCallLogs',
        params.toJson(),
      );
      return Map<String, dynamic>.from(result);
    } catch (e) {
      log(e.toString());
      return null;
    }
  }

  @override
  Future<List<SimCardModel>?> getSimCards() async {
    try {
      final result = await methodChannel.invokeMethod("getSimCards");
      if (result != null) {
        return SimCardModel.fromList(result);
      }
      return null;
    } catch (e) {
      log(e.toString());
      return null;
    }
  }
}
