package com.example.coinapp.trade.ui.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.coinapp.BaseViewModel
import com.example.coinapp.data.model.UpbitOrderbookResponse
import com.example.coinapp.data.model.UpbitWebSocketResponse
import com.example.coinapp.data.service.BaseWebSocketService
import com.example.coinapp.deeplink.DeepLinkBuilder.QUERY
import com.example.coinapp.trade.data.repository.TradingRepository
import com.example.coinapp.trade.ui.model.OrderBookItem
import com.example.coinapp.trade.ui.model.TradingTickerInfo
import com.example.coinapp.trade.ui.model.TradingUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TradingViewModel @Inject constructor(
    private val tradingRepository: TradingRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _isLoading = MutableStateFlow(true)
    private val _tradingTickerInfo = MutableStateFlow(TradingTickerInfo())
    private val _orderBookItems = MutableStateFlow<List<OrderBookItem>>(emptyList())
    private val _askCount = MutableStateFlow(0)

    val uiState = combine(
        _isLoading,
        _tradingTickerInfo,
        _orderBookItems,
        _askCount
    ) { isLoading, tickerInfo, orderBookItems, askCount ->
        TradingUiState(
            isLoading = isLoading,
            tradingTickerInfo = tickerInfo,
            orderBookItems = orderBookItems,
            askCount = askCount
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = TradingUiState()
    )

    var coinKorName = ""

    init {
        savedStateHandle.get<String>(QUERY)?.let { query ->
            val queries = query.split(",").map { it.trim() }

            coinKorName = queries.last()
            val market = queries.first()

            launch {
                tradingRepository.subscribeTicker(market).collectLatest { event ->
                    when (event) {
                        is BaseWebSocketService.WebSocketEvent.MessageReceived -> {
                            updateTicker(event.data)
                        }

                        is BaseWebSocketService.WebSocketEvent.Error -> {
                            Log.e("TradingViewModel", "subscribeTicker Error : ${event.throwable}")
                        }

                        else -> {}
                    }
                }
            }

            launch {
                tradingRepository.subscribeOrderBook(market).collectLatest { event ->
                    when (event) {
                        is BaseWebSocketService.WebSocketEvent.MessageReceived -> {
                            updateOrderBook(event.data)
                        }

                        is BaseWebSocketService.WebSocketEvent.Error -> {
                            Log.e("TradingViewModel", "subscribeOrderBook Error : ${event.throwable}")
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    private fun updateTicker(ticker: UpbitWebSocketResponse) {
        TradingTickerInfo(
            coinName = ticker.code.replace("KRW-", ""),
            coinKorName = coinKorName,
            tradePrice = ticker.tradePrice,
            signedChangeRate = ticker.signedChangeRate,
            signedChangePrice = ticker.signedChangePrice,
            highest52WeekPrice = ticker.highest52WeekPrice,
            lowest52WeekPrice = ticker.lowest52WeekPrice,
            accTradePrice24h = ticker.accTradePrice24h,
            tradeVolume = ticker.tradeVolume,
            accTradeVolume24h = ticker.accTradeVolume24h
        ).also { _tradingTickerInfo.value = it }
    }


    private fun updateOrderBook(orderBook: UpbitOrderbookResponse) {
        val items = mutableListOf<OrderBookItem>()

        orderBook.orderbookUnits.forEach { unit ->
            if (unit.askSize > 0) {
                items.add(
                    OrderBookItem(
                        price = unit.askPrice,
                        quantity = unit.askSize,
                        volume = unit.askPrice * unit.askSize,
                        isBid = false
                    )
                )
            }

            if (unit.bidSize > 0) {
                items.add(
                    OrderBookItem(
                        price = unit.bidPrice,
                        quantity = unit.bidSize,
                        volume = unit.bidPrice * unit.bidSize,
                        isBid = true
                    )
                )
            }
        }

        val asks = items.filter { !it.isBid }.sortedByDescending { it.price }
        val bids = items.filter { it.isBid }.sortedByDescending { it.price }

        _orderBookItems.value = asks + bids
        _askCount.value = asks.size

        if (_isLoading.value) {
            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        tradingRepository.disConnectWebsocket()
    }
}