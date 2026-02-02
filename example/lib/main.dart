import 'dart:developer';

import 'package:call_plugin/enums/call_log_type_enum.dart';
import 'package:call_plugin/models/call_log_params.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:call_plugin/call_plugin.dart';
import 'package:permission_handler/permission_handler.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _callPlugin = CallPlugin();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    try {
      await Permission.contacts.request();
      await Permission.phone.request();
      // callTypes: [3, 6],
      // subscriptionId: 3
      // answeredOnly: true
      // phoneNumbers: ["09388518146", "09366754028"]
      final data = await _callPlugin.getCallLogs(
        params: CallLogParams(
          page: 1,
          duration: 0,
          types: [CallLogTypeEnum.outgoing],
        ),
      );
      log(data.toString());
    } catch (e) {
      log('Failed to get platform version. ${e.toString()}');
    }
  }

  @override
  build(context) {
    return MaterialApp(
      home: Scaffold(appBar: AppBar(title: const Text('Plugin example app'))),
    );
  }
}
