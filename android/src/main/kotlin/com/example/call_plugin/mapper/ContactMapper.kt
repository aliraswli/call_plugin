package com.example.call_plugin.mapper

import com.example.call_plugin.model.ContactModel

object ContactMapper {
    fun toMap(model: ContactModel): Map<String, Any?> {
        return mapOf(
            "id" to model.id,
            "name" to model.name,
            "phones" to model.phones,
            "photos" to model.photos,
            "emails" to model.emails,
            "websites" to model.websites,
            "nickname" to model.nickname,
            "note" to model.note,
            "birthday" to model.birthday,
            "organization" to model.organization,
            "jobTitle" to model.jobTitle,
            "address" to model.address,
        )
    }
}
