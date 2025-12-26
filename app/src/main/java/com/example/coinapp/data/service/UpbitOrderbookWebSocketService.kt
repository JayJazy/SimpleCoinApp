package com.example.coinapp.data.service

import com.example.coinapp.data.model.UpbitOrderbookResponse
import com.example.coinapp.data.model.UpbitWebSocketRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Upbit Orderbook WebSocket Service - 실시간 호가 정보
 * @see <a href="https://docs.upbit.com/kr/reference/websocket-orderbook">webSocket 문서</a>
 */
@Singleton
class UpbitOrderBookWebSocketService @Inject constructor(
    okHttpClient: OkHttpClient
) : BaseWebSocketService<UpbitOrderbookResponse>(okHttpClient) {

    override val url = "wss://api.upbit.com/websocket/v1"
    override val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    override fun createSubscribeMessage(params: Any): String {
        val market = params as String
        val ticketJson = """{"ticket":"${UUID.randomUUID()}"}"""
        val typeJson = json.encodeToString(
            UpbitWebSocketRequest(
                type = "orderbook",
                codes = listOf(market)
            )
        )
        return "[$ticketJson,$typeJson]"
    }

    override fun parseMessage(text: String): UpbitOrderbookResponse {
        return json.decodeFromString<UpbitOrderbookResponse>(text)
    }

    fun connectToOrderBook(markets: String) = connect(markets)
}