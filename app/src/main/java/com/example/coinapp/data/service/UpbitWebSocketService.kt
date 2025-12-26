package com.example.coinapp.data.service

import com.example.coinapp.data.model.UpbitWebSocketRequest
import com.example.coinapp.data.model.UpbitWebSocketResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Upbit Ticker WebSocket Service - 실시간 코인 가격 정보
 * @see <a href="https://docs.upbit.com/kr/reference/websocket-ticker">webSocket 문서</a>
 */
@Singleton
class UpbitWebSocketService @Inject constructor(
    okHttpClient: OkHttpClient
) : BaseWebSocketService<UpbitWebSocketResponse>(okHttpClient) {

    override val url = "wss://api.upbit.com/websocket/v1"
    override val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }


    override fun createSubscribeMessage(params: Any): String {
        val markets = params as List<String>
        val ticketJson = """{"ticket":"${UUID.randomUUID()}"}"""
        val typeJson = json.encodeToString(
            UpbitWebSocketRequest(
                type = "ticker",
                codes = markets
            )
        )
        return "[$ticketJson,$typeJson]"
    }

    override fun parseMessage(text: String): UpbitWebSocketResponse {
        return json.decodeFromString<UpbitWebSocketResponse>(text)
    }

    fun connectToTicker(markets: List<String>) = connect(markets)
}