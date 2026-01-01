package com.example.call_plugin.repository

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.SubscriptionManager
import com.example.call_plugin.model.SimCardItem

class SimRepository(private val context: Context) {
    @SuppressLint("MissingPermission")
    fun getSimCards(): List<SimCardItem> {
        val subscription = context.getSystemService(
            Context.TELEPHONY_SUBSCRIPTION_SERVICE
        ) as SubscriptionManager

        val subs = subscription.activeSubscriptionInfoList ?: emptyList()

        return subs.map { sub ->
            SimCardItem(
                id = sub.iccId,
                carrierName = sub.carrierName?.toString(),
                countryIso = sub.countryIso,
                displayName = sub.displayName?.toString(),
                slot = sub.simSlotIndex,
                subscriptionId = sub.subscriptionId
            )
        }
    }
}
