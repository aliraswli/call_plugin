package com.example.call_plugin.datasource

import android.content.ContentResolver
import android.provider.ContactsContract
import com.example.call_plugin.model.ContactModel

class ContactDataSource(
    private val contentResolver: ContentResolver
) {
    fun getContactIdByPhoneNumber(phoneNumber: String): String? {
        val uri = ContactsContract.PhoneLookup.CONTENT_FILTER_URI
            .buildUpon()
            .appendPath(phoneNumber)
            .build()

        val projection = arrayOf(
            ContactsContract.PhoneLookup._ID
        )

        val cursor = contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(ContactsContract.PhoneLookup._ID)
                return it.getString(index)
            }
        }

        return null
    }

    fun getContactById(contactId: String): ContactModel? {

        val name = getName(contactId)
        val phones = getPhones(contactId)
        val emails = getEmails(contactId)
        val photo = getPhotoUri(contactId)

        return ContactModel(
            id = contactId,
            name = name,
            phones = phones,
            emails = emails,
            photos = photo?.let { listOf(it) } ?: emptyList()
        )
    }

    private fun getName(contactId: String): String? {
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(ContactsContract.Contacts.DISPLAY_NAME),
            "${ContactsContract.Contacts._ID} = ?",
            arrayOf(contactId),
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                return it.getString(0)
            }
        }
        return null
    }

    private fun getPhones(contactId: String): List<String> {
        val phones = mutableListOf<String>()

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                phones.add(it.getString(0))
            }
        }

        return phones
    }

    private fun getEmails(contactId: String): List<String> {
        val emails = mutableListOf<String>()

        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Email.ADDRESS),
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                emails.add(it.getString(0))
            }
        }

        return emails
    }

    private fun getPhotoUri(contactId: String): String? {
        return ContactsContract.Contacts.CONTENT_URI
            .buildUpon()
            .appendPath(contactId)
            .appendPath(ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
            .build()
            .toString()
    }
}
