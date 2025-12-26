package com.example.coinapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 업비트 마켓 코드 정보
 */
@Serializable
data class UpbitMarket(
    @SerialName("market")
    val market: String,  // 마켓 코드 (ex: KRW-BTC)

    @SerialName("korean_name")
    val koreanName: String,  // 한글명

    @SerialName("english_name")
    val englishName: String  // 영문명
)