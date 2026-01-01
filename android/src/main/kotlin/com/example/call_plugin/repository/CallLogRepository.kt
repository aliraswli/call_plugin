package com.example.call_plugin.repository

import android.content.Context
import android.provider.CallLog
import com.example.call_plugin.mapper.CursorMapper
import com.example.call_plugin.model.CallLogFilter
import com.example.call_plugin.model.CallLogResult
import kotlin.math.ceil

class CallLogRepository(
    private val context: Context,
) {
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

        if (filter.answeredOnly == true) {
            selectionParts.add("${CallLog.Calls.DURATION} > 0")
        }

        val selection = if (selectionParts.isNotEmpty())
            selectionParts.joinToString(" AND ") else null

        val args = if (selectionArgs.isNotEmpty())
            selectionArgs.toTypedArray() else null

        val totalCount = getTotalCount(selection, args)
        val totalPage = if (totalCount == 0) 0
        else ceil(totalCount / filter.perPage.toDouble()).toInt()

        val limit = filter.page * filter.perPage

        val uri = CallLog.Calls.CONTENT_URI.buildUpon()
            .appendQueryParameter("limit", limit.toString())
            .build()

        val cursor = context.contentResolver.query(
            uri,
            null,
            selection,
            args,
            "${CallLog.Calls.DATE} DESC"
        )

        val offset = (filter.page - 1) * filter.perPage
        val items = CursorMapper.map(cursor, offset, filter.perPage)

        return CallLogResult(
            items = items,
            currentPage = filter.page,
            perPage = filter.perPage,
            totalPage = totalPage
        )
    }

    private fun getTotalCount(selection: String?, args: Array<String>?): Int {
        context.contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            arrayOf(CallLog.Calls._ID),
            selection,
            args,
            null
        )?.use { return it.count }

        return 0
    }
}
