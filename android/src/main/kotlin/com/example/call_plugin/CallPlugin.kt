package com.example.call_plugin

import android.content.Context
import com.example.call_plugin.model.CallLogFilter
import com.example.call_plugin.repository.CallLogRepository
import com.example.call_plugin.repository.SimRepository
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class CallPlugin : FlutterPlugin, MethodCallHandler {

    private lateinit var channel: MethodChannel
    private lateinit var context: Context
    private lateinit var callRepository: CallLogRepository
    private lateinit var simRepository: SimRepository

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        context = binding.applicationContext
        simRepository = SimRepository(context)
        callRepository = CallLogRepository(context)

        channel = MethodChannel(binding.binaryMessenger, "call_plugin")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "getCallLogs" -> handleGetCallLogs(call, result)
            "getSimCards" -> handleGetSimCards(result)
            else -> result.notImplemented()
        }
    }

    private fun handleGetCallLogs(call: MethodCall, result: MethodChannel.Result) {
        try {
            val args = call.arguments as Map<*, *>

            val filter = CallLogFilter(
                page = args["page"] as Int,
                perPage = args["perPage"] as Int,
                callTypes = args["callTypes"] as? List<Int>,
                phoneNumbers = args["phoneNumbers"] as? List<String>,
                subscriptionId = args["subscriptionId"] as? String,
                answeredOnly = args["answeredOnly"] as? Boolean
            )

            val data = callRepository.getPagedLogs(filter)

            result.success(
                mapOf(
                    "items" to data.items.map { it.toMap() },
                    "currentPage" to data.currentPage,
                    "perPage" to data.perPage,
                    "totalPage" to data.totalPage
                )
            )

        } catch (e: Exception) {
            result.error("CALL_LOG_ERROR", e.message, null)
        }
    }

    private fun handleGetSimCards(result: MethodChannel.Result) {
        try {
            val sims = simRepository.getSimCards()
            result.success(
                sims.map { sim ->
                    mapOf(
                        "id" to sim.id,
                        "carrierName" to sim.carrierName,
                        "countryIso" to sim.countryIso,
                        "displayName" to sim.displayName,
                        "subscriptionId" to sim.subscriptionId,
                        "slot" to sim.slot

                    )
                }
            )
        } catch (e: Exception) {
            result.error("SIM_CARD_ERROR", e.message, null)
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
