package com.example.call_plugin.model

data class SimCardItem(
    val id: String,
    val slot: Int?,
    val subscriptionId: Int?,
    val carrierName: String?,
    val countryIso: String?,
    val displayName: String?
)
