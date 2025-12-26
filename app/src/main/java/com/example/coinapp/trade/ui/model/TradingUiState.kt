package com.example.coinapp.trade.ui.model

data class TradingUiState(
    val isLoading: Boolean = true,
    val tradingTickerInfo: TradingTickerInfo = TradingTickerInfo(),
    val orderBookItems: List<OrderBookItem> = emptyList(),
    val askCount: Int = 0
)