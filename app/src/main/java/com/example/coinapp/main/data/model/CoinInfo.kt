package com.example.coinapp.main.data.model

data class CoinInfo(
    val market: String,              // 마켓 코드 (ex: KRW-BTC)
    val koreanName: String,          // 한글명 (ex: 비트코인)
    val englishName: String,         // 영문명 (ex: Bitcoin)
    val ticker: UpbitTicker          // 현재가 정보
)