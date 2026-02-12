package com.example.call_plugin

import android.content.Context
import com.example.call_plugin.datasource.ContactDataSource
import com.example.call_plugin.model.CallLogFilter
import com.example.call_plugin.repository.CallLogRepository
import com.example.call_plugin.repository.ContactRepository
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

    private lateinit var contactRepository: ContactRepository

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        context = binding.applicationContext
        simRepository = SimRepository(context)
        callRepository = CallLogRepository(context)

        val contactDataSource = ContactDataSource(context.contentResolver)
        contactRepository = ContactRepository(contactDataSource)

        channel = MethodChannel(binding.binaryMessenger, "call_plugin")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "getCallLogs" -> handleGetCallLogs(call, result)
            "deleteCallLogById" -> handleDeleteCallLogById(call, result)
            "deleteCallLogByPhone" -> handleDeleteCallLogByPhone(call, result)
            "getSimCards" -> handleGetSimCards(result)
            "getContactById" -> handleGetContactById(call, result)
            "getContactIdByPhone" -> handleGetContactIdByPhone(call, result)
            else -> result.notImplemented()
        }
    }

    private fun handleGetContactIdByPhone(call: MethodCall, result: MethodChannel.Result) {
        try {
            val args = call.arguments as? Map<*, *>
            val phoneNumber = args?.get("phone") as? String
            if (phoneNumber.isNullOrBlank()) {
                result.error("CONTACT_ID_ERROR", "phoneNumber cannot be null or empty", null)
                return
            }
            val contactId = contactRepository.getContactIdByPhoneNumber(phoneNumber)
            result.success(contactId)
        } catch (e: Exception) {
            result.error("CONTACT_ID_ERROR", e.message, null)
        }
    }

    private fun handleGetContactById(call: MethodCall, result: MethodChannel.Result) {
        try {
            val args = call.arguments as? Map<*, *>
            val contactId = args?.get("id") as? String

            if (contactId.isNullOrEmpty()) {
                result.error("CONTACT_ERROR", "contactId cannot be null or empty", null)
                return
            }

            val contact = contactRepository.getContactById(contactId)

            result.success(contact)
        } catch (e: Exception) {
            result.error("CONTACT_ERROR", e.message, null)
        }
    }

    private fun handleDeleteCallLogById(call: MethodCall, result: MethodChannel.Result) {
        try {
            val callLogId = call.arguments as String?
            if (callLogId == null) {
                result.error("DELETE_CALL_LOG_BY_ID_ERROR", "callLogId cant be null.", null)
                return
            }
            val data = callRepository.deleteCallLogById(callLogId)
            result.success(data)
        } catch (e: Exception) {
            result.error("DELETE_CALL_LOG_BY_ID_ERROR", e.message, null)
        }
    }

    private fun handleDeleteCallLogByPhone(call: MethodCall, result: MethodChannel.Result) {
        try {
            val phoneNumber = call.arguments as String?
            if (phoneNumber == null) {
                result.error("DELETE_CALL_LOG_BY_PHONE_ERROR", "number cant be null.", null)
                return
            }
            val data = callRepository.deleteCallLogByPhone(phoneNumber)
            result.success(data)
        } catch (e: Exception) {
            result.error("DELETE_CALL_LOG_BY_PHONE_ERROR", e.message, null)
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
                isAnswered = args["isAnswered"] as? Boolean,
                isUnknown = args["isUnknown"] as? Boolean,
                duration = args["duration"] as? Int
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
                })
        } catch (e: Exception) {
            result.error("SIM_CARD_ERROR", e.message, null)
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
