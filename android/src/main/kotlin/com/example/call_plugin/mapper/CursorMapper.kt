package com.example.call_plugin.mapper

import android.database.Cursor
import android.provider.CallLog
import com.example.call_plugin.model.CallLogItem
import com.example.call_plugin.model.SimCardItem

object CursorMapper {
    fun map(
        cursor: Cursor?,
        offset: Int,
        limit: Int,
    ): List<CallLogItem> {
        val list = mutableListOf<CallLogItem>()
        var index = 0

        cursor?.use {
            while (it.moveToNext()) {
                if (index >= offset && list.size < limit) {
                    list.add(
                        CallLogItem(
                            id = it.getString(it.getColumnIndexOrThrow(CallLog.Calls._ID)),
                            number = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER)),
                            name = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME)),
                            type = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE)),
                            date = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE)),
                            duration = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DURATION)),
                            subscriptionId = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.PHONE_ACCOUNT_ID)),
                        )
                    )
                }
                index++
            }
        }
        return list
    }
}
