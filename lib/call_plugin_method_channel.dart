import 'dart:developer';

import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'call_plugin_platform_interface.dart';

class MethodChannelCallPlugin extends CallPluginPlatform {
  @visibleForTesting
  final methodChannel = const MethodChannel('call_plugin');

  @override
  Future<Map<String, dynamic>?> getCallLogs({
    required int page,
    required int perPage,
    List<int>? callTypes,
    List<String>? phoneNumbers,
    String? subscriptionId,
    bool? answeredOnly,
  }) async {
    try {
      final result = await methodChannel.invokeMethod('getCallLogs', {
        'page': page,
        'perPage': perPage,
        'callTypes': callTypes,
        'phoneNumbers': phoneNumbers,
        'subscriptionId': subscriptionId,
        'answeredOnly': answeredOnly,
      });

      return Map<String, dynamic>.from(result);
    } catch (e) {
      log(e.toString());
      return null;
    }
  }

  @override
  Future<List<Map>?> getSimCards() async {
    try {
      final result = await methodChannel.invokeMethod("getSimCards");
      log(result.toString());
      return List<Map>.from(result);
    } catch (e) {
      log(e.toString());
      return null;
    }
  }
}
