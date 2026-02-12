package com.example.call_plugin.repository

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import com.example.call_plugin.datasource.ContactDataSource
import com.example.call_plugin.mapper.ContactMapper

class ContactRepository(
    private val context: Context,
    private val dataSource: ContactDataSource
) {
    fun getContactIdByPhoneNumber(phoneNumber: String): String? {
        if (phoneNumber.isBlank()) return null
        return dataSource.getContactIdByPhoneNumber(phoneNumber)
    }

    fun getContactById(contactId: String): Map<String, Any?>? {
        val model = dataSource.getContactById(contactId) ?: return null
        return ContactMapper.toMap(model)
    }

    fun openContactById(contactId: String): Boolean {
        if (contactId.isBlank()) return false

        return try {
            val uri = ContactsContract.Contacts.CONTENT_URI
                .buildUpon()
                .appendPath(contactId)
                .build()

            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = uri
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(intent)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
