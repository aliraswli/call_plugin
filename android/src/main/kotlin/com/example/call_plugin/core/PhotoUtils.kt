package com.example.call_plugin.core

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.util.Base64
import android.util.Log

object PhotoUtils {
    @SuppressLint("Range")
    fun getContactPhotoBase64(context: Context, phoneNumber: String?): String? {
        if (phoneNumber.isNullOrBlank()) return null
        return try {
            val resolver = context.contentResolver
            val lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(phoneNumber)
            )
            var contactId: Long? = null
            resolver.query(
                lookupUri, arrayOf(ContactsContract.PhoneLookup._ID), null, null, null
            )?.use { item ->
                if (item.moveToFirst()) {
                    val id = item.getColumnIndex(ContactsContract.PhoneLookup._ID)
                    if (id >= 0) contactId = item.getLong(id)
                }
            }

            val photoUri = contactId?.let {
                ContentUris.withAppendedId(
                    ContactsContract.Contacts.CONTENT_URI,
                    it
                )
            }
            val stream = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                ContactsContract.Contacts.openContactPhotoInputStream(resolver, photoUri, true)
            } else {
                ContactsContract.Contacts.openContactPhotoInputStream(resolver, photoUri)
            }
            stream?.use { s ->
                val bytes = s.readBytes()
                if (bytes.isNotEmpty()) Base64.encodeToString(bytes, Base64.NO_WRAP) else null
            }
        } catch (e: Exception) {
            Log.e("CallHelper", "getContactPhotoBase64: $phoneNumber", e)
            null
        }
    }
}