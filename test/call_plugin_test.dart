// import 'package:flutter_test/flutter_test.dart';
// import 'package:call_plugin/call_plugin.dart';
// import 'package:call_plugin/call_plugin_platform_interface.dart';
// import 'package:call_plugin/call_plugin_method_channel.dart';
// import 'package:plugin_platform_interface/plugin_platform_interface.dart';
//
// class MockCallPluginPlatform
//     with MockPlatformInterfaceMixin
//     implements CallPluginPlatform {
//
//   @override
//   Future<String?> getPlatformVersion() => Future.value('42');
// }
//
// void main() {
//   final CallPluginPlatform initialPlatform = CallPluginPlatform.instance;
//
//   test('$MethodChannelCallPlugin is the default instance', () {
//     expect(initialPlatform, isInstanceOf<MethodChannelCallPlugin>());
//   });
//
//   test('getPlatformVersion', () async {
//     CallPlugin callPlugin = CallPlugin();
//     MockCallPluginPlatform fakePlatform = MockCallPluginPlatform();
//     CallPluginPlatform.instance = fakePlatform;
//
//     expect(await callPlugin.getPlatformVersion(), '42');
//   });
// }
