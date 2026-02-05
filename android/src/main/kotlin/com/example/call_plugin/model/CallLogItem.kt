package com.example.call_plugin.model

data class CallLogItem(
    val id: String,
    val number: String?,
    val name: String?,
    val photo: String?,
    val type: Int,
    val date: Long,
    val duration: Long,
    val subscriptionId: String?,
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "number" to number,
            "name" to name,
            "photo" to photo,
            "type" to type,
            "date" to date,
            "duration" to duration,
            "subscriptionId" to subscriptionId
        )
    }
}
