import 'dart:developer';

import 'package:call_plugin/models/call_log_params.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'call_plugin_platform_interface.dart';
import 'models/contact_model.dart';
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

  @override
  Future<bool> deleteCallLogById(String callLogId) async {
    try {
      final result = await methodChannel.invokeMethod(
        "deleteCallLogById",
        callLogId,
      );
      log("deleteCallLogById: ** / $result");
      return result != -1;
    } catch (e) {
      log(e.toString());
      return false;
    }
  }

  @override
  Future<bool> deleteCallLogByPhone(String phoneNumber) async {
    try {
      final result = await methodChannel.invokeMethod(
        "deleteCallLogByPhone",
        phoneNumber,
      );
      log("deleteCallLogByPhone: ** / $result");
      // 0 or zero its mean no call logs to delete.
      // -1 its mean cant delete call logs or has error.
      return result != 0 && result != -1;
    } catch (e) {
      log(e.toString());
      return false;
    }
  }

  @override
  Future<String?> getContactIdByPhone(String phone) async {
    try {
      final result = await methodChannel.invokeMethod<String?>(
        'getContactIdByPhone',
        {'phone': phone},
      );
      return result;
    } catch (e) {
      log(e.toString());
      return null;
    }
  }

  @override
  Future<ContactModel?> getContactById(String id) async {
    try {
      final result = await methodChannel.invokeMethod<Map>('getContactById', {
        'id': id,
      });
      if (result == null) return null;
      return ContactModel.fromJson(Map<String, dynamic>.from(result));
    } catch (e) {
      log(e.toString());
      return null;
    }
  }
}
