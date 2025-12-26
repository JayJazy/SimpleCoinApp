package com.example.coinapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 업비트 WebSocket 구독 요청
 */
@Serializable
data class UpbitWebSocketRequest(
    @SerialName("type")
    val type: String,  // "ticker", "trade", "orderbook"

    @SerialName("codes")
    val codes: List<String>,  // ["KRW-BTC", "KRW-ETH"]

    @SerialName("isOnlySnapshot")
    val isOnlySnapshot: Boolean = false,

    @SerialName("isOnlyRealtime")
    val isOnlyRealtime: Boolean = true
)

/**
 * 업비트 WebSocket Ticker 응답
 */
@Serializable
data class UpbitWebSocketResponse(
    @SerialName("type")
    val type: String,  // "ticker"

    @SerialName("code")
    val code: String,  // 마켓 코드 (ex: KRW-BTC)

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
    val change: String,  // 전일 대비 (RISE, EVEN, FALL)

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

    @SerialName("trade_date")
    val tradeDate: String,  // 최근 거래 일자(UTC)

    @SerialName("trade_time")
    val tradeTime: String,  // 최근 거래 시각(UTC)

    @SerialName("trade_timestamp")
    val tradeTimestamp: Long,  // 체결 타임스탬프(ms)

    @SerialName("acc_ask_volume")
    val accAskVolume: Double,  // 누적 매도량

    @SerialName("acc_bid_volume")
    val accBidVolume: Double,  // 누적 매수량

    @SerialName("timestamp")
    val timestamp: Long,  // 타임스탬프(ms)

    @SerialName("stream_type")
    val streamType: String  // SNAPSHOT, REALTIME
)

/**
 * 업비트 WebSocket Orderbook 호가 유닛
 */
@Serializable
data class UpbitOrderbookUnit(
    @SerialName("ask_price")
    val askPrice: Double,  // 매도 호가

    @SerialName("bid_price")
    val bidPrice: Double,  // 매수 호가

    @SerialName("ask_size")
    val askSize: Double,  // 매도 잔량

    @SerialName("bid_size")
    val bidSize: Double  // 매수 잔량
)

/**
 * 업비트 WebSocket Orderbook 응답
 */
@Serializable
data class UpbitOrderbookResponse(
    @SerialName("type")
    val type: String,  // "orderbook"

    @SerialName("code")
    val code: String,  // 마켓 코드 (ex: KRW-BTC)

    @SerialName("timestamp")
    val timestamp: Long,  // 타임스탬프 (ms)

    @SerialName("total_ask_size")
    val totalAskSize: Double,  // 호가 매도 총 잔량

    @SerialName("total_bid_size")
    val totalBidSize: Double,  // 호가 매수 총 잔량

    @SerialName("orderbook_units")
    val orderbookUnits: List<UpbitOrderbookUnit>,  // 호가 데이터 배열

    @SerialName("level")
    val level: Double = 0.0,  // 호가 모아보기 단위 (기본값: 0)

    @SerialName("stream_type")
    val streamType: String  // SNAPSHOT, REALTIME
)