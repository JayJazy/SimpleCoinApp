package com.example.coinapp.trade.ui.model

data class OrderBookItem(
    val price: Double,
    val quantity: Double,
    val volume: Double,
    val isBid: Boolean
)