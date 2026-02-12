package com.example.call_plugin.model

data class ContactModel(
    val id: String,
    val name: String?,
    val phones: List<String> = emptyList(),
    val emails: List<String> = emptyList(),
    val photos: List<String> = emptyList(),
    val websites: List<String> = emptyList(),
    val nickname: String? = null,
    val note: String? = null,
    val birthday: String? = null,
    val organization: String? = null,
    val jobTitle: String? = null,
    val address: String? = null
)
