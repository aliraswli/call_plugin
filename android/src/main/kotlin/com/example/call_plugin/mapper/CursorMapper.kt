package com.example.call_plugin.mapper

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.CallLog
import android.util.Base64
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
                    val photoUriString =
                        it.getString(it.getColumnIndexOrThrow(CallLog.Calls.CACHED_PHOTO_URI))
                    val base64Photo = photoUriToBase64(context, photoUriString)

                    list.add(
                        CallLogItem(
                            id = it.getString(it.getColumnIndexOrThrow(CallLog.Calls._ID)),
                            number = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.NUMBER)),
                            name = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.CACHED_NAME)),
                            photo = base64Photo,
                            type = it.getInt(it.getColumnIndexOrThrow(CallLog.Calls.TYPE)),
                            date = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE)),
                            duration = it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DURATION)),
                            subscriptionId = it.getString(it.getColumnIndexOrThrow(CallLog.Calls.PHONE_ACCOUNT_COMPONENT_NAME)),
                        )
                    )
                }
                index++
            }
        }
        return list
    }

    fun photoUriToBase64(context: Context, uriString: String?): String? {
        if (uriString.isNullOrEmpty()) return null

        return try {
            val uri = Uri.parse(uriString)
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val bytes = inputStream.readBytes()
                Base64.encodeToString(bytes, Base64.NO_WRAP)
            }
        } catch (e: Exception) {
            null
        }
    }

}
