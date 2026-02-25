package com.example.call_plugin.mapper

import android.content.Context
import android.database.Cursor
import android.provider.CallLog
import com.example.call_plugin.core.PhotoUtils
import com.example.call_plugin.model.CallLogItem

object CursorMapper {
    fun map(
        context: Context,
        cursor: Cursor?,
        offset: Int,
        limit: Int,
    ): List<CallLogItem> {
        val list = mutableListOf<CallLogItem>()
        var index = 0

        cursor?.use {
            while (it.moveToNext()) {
                if (index >= offset && list.size < limit) {
                    val photoUri = it.getString(
                        it.getColumnIndexOrThrow(CallLog.Calls.CACHED_PHOTO_URI)
                    )
                    val number = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER))
                    val photoBase64 = PhotoUtils.photoUriToBase64(context, photoUri)

                    list.add(
                        CallLogItem(
                            number = number,
                            photo = photoBase64,
                            id = it.getString(it.getColumnIndexOrThrow(CallLog.Calls._ID)),
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
