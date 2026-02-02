package com.example.call_plugin.model

data class CallLogFilter(
    val page: Int = 1,
    val perPage: Int = 20,

    // INCOMING_TYPE = 1
    // OUTGOING_TYPE = 2
    // MISSED_TYPE = 3
    // VOICEMAIL_TYPE = 4
    // REJECTED_TYPE = 5
    // BLOCKED_TYPE = 6
    val callTypes: List<Int>? = null,
    val phoneNumbers: List<String>? = null,
    val subscriptionId: String? = null,
    val answeredOnly: Boolean? = null,
    val duration: Int? = null
)
