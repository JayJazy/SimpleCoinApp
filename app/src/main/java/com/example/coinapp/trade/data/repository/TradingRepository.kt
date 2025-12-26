package com.example.coinapp.trade.data.repository

import com.example.coinapp.data.model.UpbitOrderbookResponse
import com.example.coinapp.data.model.UpbitWebSocketResponse
import com.example.coinapp.data.service.BaseWebSocketService
import com.example.coinapp.data.service.UpbitOrderBookWebSocketService
import com.example.coinapp.data.service.UpbitWebSocketService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TradingRepository @Inject constructor(
    private val upBitWebSocketService: UpbitWebSocketService,
    private val upBitOrderBookWebSocketService: UpbitOrderBookWebSocketService
) {
    fun subscribeTicker(market: String) : Flow<BaseWebSocketService.WebSocketEvent<UpbitWebSocketResponse>> {
        return upBitWebSocketService.connectToTicker(listOf(market))
    }

    fun subscribeOrderBook(market: String): Flow<BaseWebSocketService.WebSocketEvent<UpbitOrderbookResponse>> {
        return upBitOrderBookWebSocketService.connectToOrderBook(market)
    }

    fun disConnectWebsocket() {
        upBitWebSocketService.disconnect()
        upBitOrderBookWebSocketService.disconnect()
    }
}