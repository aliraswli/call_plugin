package com.example.call_plugin.repository

import com.example.call_plugin.datasource.ContactDataSource
import com.example.call_plugin.mapper.ContactMapper

class ContactRepository(
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
}
