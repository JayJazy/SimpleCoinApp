package com.example.coinapp.trade.ui.model

data class TradingTickerInfo(
    val coinName: String = "",
    val coinKorName: String = "",
    val tradePrice: Double = 0.0,
    val signedChangeRate: Double = 0.0,
    val signedChangePrice: Double = 0.0,
    val highest52WeekPrice: Double = 0.0,
    val lowest52WeekPrice: Double = 0.0,
    val accTradePrice24h: Double = 0.0,
    val tradeVolume: Double = 0.0,              // 가장 최근 거래량
    val accTradeVolume24h: Double = 0.0         // 24시간 누적 거래량
)