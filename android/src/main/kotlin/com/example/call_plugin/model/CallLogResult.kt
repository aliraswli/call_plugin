package com.example.call_plugin.model

data class CallLogResult(
    val items: List<CallLogItem>,
    val currentPage: Int,
    val perPage: Int,
    val totalPage: Int
)
