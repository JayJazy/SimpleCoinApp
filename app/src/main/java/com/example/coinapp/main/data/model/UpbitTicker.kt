package com.example.coinapp.main.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 업비트 현재가 정보 (API)
 */
@Serializable
data class UpbitTicker(
    @SerialName("market")
    val market: String,  // 마켓 코드 (ex: KRW-BTC)

    @SerialName("trade_date")
    val tradeDate: String,  // 최근 거래 일자(UTC) (yyyyMMdd)

    @SerialName("trade_time")
    val tradeTime: String,  // 최근 거래 시각(UTC) (HHmmss)

    @SerialName("trade_date_kst")
    val tradeDateKst: String,  // 최근 거래 일자(KST) (yyyyMMdd)

    @SerialName("trade_time_kst")
    val tradeTimeKst: String,  // 최근 거래 시각(KST) (HHmmss)

    @SerialName("trade_timestamp")
    val tradeTimestamp: Long,  // 최근 거래 타임스탬프

    @SerialName("opening_price")
    val openingPrice: Double,  // 시가

    @SerialName("high_price")
    val highPrice: Double,  // 고가

    @SerialName("low_price")
    val lowPrice: Double,  // 저가

    @SerialName("trade_price")
    val tradePrice: Double,  // 현재가

    @SerialName("prev_closing_price")
    val prevClosingPrice: Double,  // 전일 종가

    @SerialName("change")
    val change: String,  // 전일 대비 (RISE: 상승, EVEN: 보합, FALL: 하락)

    @SerialName("change_price")
    val changePrice: Double,  // 전일 대비 가격

    @SerialName("change_rate")
    val changeRate: Double,  // 전일 대비 등락률

    @SerialName("signed_change_price")
    val signedChangePrice: Double,  // 부호가 있는 전일 대비 가격

    @SerialName("signed_change_rate")
    val signedChangeRate: Double,  // 부호가 있는 전일 대비 등락률

    @SerialName("trade_volume")
    val tradeVolume: Double,  // 가장 최근 거래량

    @SerialName("acc_trade_price")
    val accTradePrice: Double,  // 누적 거래대금(당일 00시 기준)

    @SerialName("acc_trade_price_24h")
    val accTradePrice24h: Double,  // 24시간 누적 거래대금

    @SerialName("acc_trade_volume")
    val accTradeVolume: Double,  // 누적 거래량(당일 00시 기준)

    @SerialName("acc_trade_volume_24h")
    val accTradeVolume24h: Double,  // 24시간 누적 거래량

    @SerialName("highest_52_week_price")
    val highest52WeekPrice: Double,  // 52주 최고가

    @SerialName("highest_52_week_date")
    val highest52WeekDate: String,  // 52주 최고가 달성일

    @SerialName("lowest_52_week_price")
    val lowest52WeekPrice: Double,  // 52주 최저가

    @SerialName("lowest_52_week_date")
    val lowest52WeekDate: String,  // 52주 최저가 달성일

    @SerialName("timestamp")
    val timestamp: Long  // 타임스탬프
)