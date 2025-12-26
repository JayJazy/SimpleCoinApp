package com.example.coinapp.main.data.repository

import com.example.coinapp.data.model.UpbitMarket
import com.example.coinapp.data.model.UpbitWebSocketResponse
import com.example.coinapp.data.service.BaseWebSocketService
import com.example.coinapp.data.service.UpbitApiService
import com.example.coinapp.data.service.UpbitWebSocketService
import com.example.coinapp.main.data.model.CoinInfo
import com.example.coinapp.main.data.model.UpbitTicker
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val upbitApiService: UpbitApiService,
    private val upbitWebSocketService: UpbitWebSocketService
) {
    private suspend fun getKrwMarkets(): List<UpbitMarket> {
        val markets = upbitApiService.getMarkets()
        return markets.filter { it.market.startsWith("KRW-") }    }

    private suspend fun getTickers(markets: List<String>): List<UpbitTicker> {
        val marketsString = markets.joinToString(",")
        return upbitApiService.getTickers(marketsString)
    }

    suspend fun getTop20CoinList(limit: Int = 20): List<CoinInfo> {
        // 1. KRW 마켓 코드 조회
        val markets = getKrwMarkets()
        val marketMap = markets.associateBy { it.market }

        // 2. 현재가 정보 조회
        val tickers = getTickers(markets.map { it.market })

        // 3. Market + Ticker 결합
        val coinInfoList = tickers.mapNotNull { ticker ->
            val market = marketMap[ticker.market] ?: return@mapNotNull null
            CoinInfo(
                market = market.market,
                koreanName = market.koreanName,
                englishName = market.englishName,
                ticker = ticker
            )
        }

        val top20CoinList = coinInfoList
            .sortedByDescending { it.ticker.accTradePrice24h }
            .take(limit)

        return top20CoinList
    }

    fun subscribeToTicker(markets: List<String>): Flow<BaseWebSocketService.WebSocketEvent<UpbitWebSocketResponse>> {
        return upbitWebSocketService.connectToTicker(markets)
    }

    fun disconnectWebSocket() {
        upbitWebSocketService.disconnect()
    }
}