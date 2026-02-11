package com.example.call_plugin.repository

import android.content.Context
import android.provider.CallLog
import android.telephony.PhoneNumberUtils
import android.util.Log
import com.example.call_plugin.mapper.CursorMapper
import com.example.call_plugin.model.CallLogFilter
import com.example.call_plugin.model.CallLogResult
import kotlin.math.ceil

class CallLogRepository(
    private val context: Context,
) {
    fun deleteCallLogById(id: String): Int {
        try {
            return context.contentResolver.delete(
                CallLog.Calls.CONTENT_URI, CallLog.Calls._ID + " = ? ", arrayOf(id)
            )
        } catch (e: Exception) {
            Log.e("CallLog", "Not allowed to delete call log", e)
            return -1;
        }
    }

    fun deleteCallLogByPhone(targetNumber: String): Int {
        try {
            var deletedCount = 0
            val resolver = context.contentResolver

            val digitsOnly = targetNumber.replace(Regex("[^0-9]"), "")

            val projection = arrayOf(
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER
            )

            val selection = "${CallLog.Calls.NUMBER} LIKE ?"
            val selectionArgs = arrayOf("%$digitsOnly%")

            val cursor = resolver.query(
                CallLog.Calls.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
            )

            cursor?.use {
                val idIndex = it.getColumnIndex(CallLog.Calls._ID)
                val numberIndex = it.getColumnIndex(CallLog.Calls.NUMBER)

                while (it.moveToNext()) {
                    val id = it.getString(idIndex)
                    val number = it.getString(numberIndex)

                    if (PhoneNumberUtils.compare(number, targetNumber)) {
                        val rows = resolver.delete(
                            CallLog.Calls.CONTENT_URI,
                            "${CallLog.Calls._ID} = ?",
                            arrayOf(id)
                        )
                        deletedCount += rows
                    }
                }
            }

            return deletedCount
        } catch (e: Exception) {
            return -1
        }
    }

    private fun normalizePhone(number: String): String {
        return number
            .replace(" ", "")
            .replace("-", "")
            .replace("(", "")
            .replace(")", "")
            .replace("+98", "0")
            .replace("0098", "0")
    }

    fun getPagedLogs(filter: CallLogFilter): CallLogResult {
        val selectionParts = mutableListOf<String>()
        val selectionArgs = mutableListOf<String>()

        filter.callTypes?.let {
            selectionParts.add("${CallLog.Calls.TYPE} IN (${it.joinToString(",")})")
        }

        filter.phoneNumbers?.takeIf { it.isNotEmpty() }?.let { numbers ->
            val conditions = numbers.joinToString(" OR ") {
                "${CallLog.Calls.NUMBER} LIKE ?"
            }

            selectionParts.add("($conditions)")

            numbers.forEach {
                selectionArgs.add("%${it.takeLast(7)}%")
            }
        }

        filter.subscriptionId?.let {
            selectionParts.add("${CallLog.Calls.PHONE_ACCOUNT_ID} = ?")
            selectionArgs.add(it)
        }

        filter.isUnknown?.let { isUnknown ->
            if (isUnknown) {
                selectionParts.add(
                    "(${CallLog.Calls.CACHED_NAME} IS NULL OR ${CallLog.Calls.CACHED_NAME} = '')"
                )
            } else {
                selectionParts.add(
                    "(${CallLog.Calls.CACHED_NAME} IS NOT NULL AND ${CallLog.Calls.CACHED_NAME} != '')"
                )
            }
        }

        filter.duration?.let {
            selectionParts.add("${CallLog.Calls.DURATION} = ?");
            selectionArgs.add(it.toString())
        }

        if (filter.isAnswered == true) {
            selectionParts.add("${CallLog.Calls.DURATION} > 0")
        } else if (filter.isAnswered == false) {
            selectionParts.add("${CallLog.Calls.DURATION} = 0")
        }

        val selection =
            if (selectionParts.isNotEmpty()) selectionParts.joinToString(" AND ") else null

        val args = if (selectionArgs.isNotEmpty()) selectionArgs.toTypedArray() else null

        val totalCount = getTotalCount(selection, args)
        val totalPage = if (totalCount == 0) 0
        else ceil(totalCount / filter.perPage.toDouble()).toInt()

        val limit = filter.page * filter.perPage

        val uri =
            CallLog.Calls.CONTENT_URI.buildUpon().appendQueryParameter("limit", limit.toString())
                .build()

        val cursor = context.contentResolver.query(
            uri, null, selection, args, "${CallLog.Calls.DATE} DESC"
        )

        val offset = (filter.page - 1) * filter.perPage
        val items = CursorMapper.map(context, cursor, offset, filter.perPage)

        return CallLogResult(
            items = items,
            currentPage = filter.page,
            perPage = filter.perPage,
            totalPage = totalPage
        )
    }

    private fun getTotalCount(selection: String?, args: Array<String>?): Int {
        context.contentResolver.query(
            CallLog.Calls.CONTENT_URI, arrayOf(CallLog.Calls._ID), selection, args, null
        )?.use { return it.count }

        return 0
    }
}
