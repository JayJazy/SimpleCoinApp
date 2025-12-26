package com.example.coinapp.main.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.coinapp.BaseViewModel
import com.example.coinapp.main.data.model.CoinInfo
import com.example.coinapp.data.model.UpbitWebSocketResponse
import com.example.coinapp.data.service.BaseWebSocketService
import com.example.coinapp.main.data.repository.MainRepository
import com.example.coinapp.main.ui.model.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
): BaseViewModel() {

    private val _isLoading = MutableStateFlow(true)
    private val _coins = MutableStateFlow<List<CoinInfo>>(emptyList())
    private val coinsMap = MutableStateFlow<Map<String, CoinInfo>>(emptyMap())

    val uiState = combine(
        _isLoading,
        _coins
    ) { isLoading, coins ->
        MainUiState(
            isLoading = isLoading,
            coins = coins
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = MainUiState()
    )

    init {
        fetchCoins()
    }

    private fun fetchCoins() {
        launch {
            _isLoading.value = true
            val coinInfoList = mainRepository.getTop20CoinList(limit = 20)

            coinsMap.value = coinInfoList.associateBy { it.market }
            _coins.value = coinInfoList
            _isLoading.value = false

            connectWebSocket(coinInfoList.map { it.market })
        }
    }

    private fun connectWebSocket(markets: List<String>) {
        launch {
            mainRepository.subscribeToTicker(markets).collect { event ->
                when (event) {
                    is BaseWebSocketService.WebSocketEvent.Connected -> {
                        Log.d("MainViewModel", "WebSocket Connected!")
                    }

                    is BaseWebSocketService.WebSocketEvent.MessageReceived -> {
                        updateCoinData(event.data)
                    }

                    is BaseWebSocketService.WebSocketEvent.Error -> {
                        Log.e("MainViewModel", "WebSocket Error: ${event.throwable.message}")
                    }

                    else -> {}
                }
            }
        }
    }

    private fun updateCoinData(wsData: UpbitWebSocketResponse) {
        val currentCoins = coinsMap.value.toMutableMap()

        currentCoins[wsData.code]?.let { existingCoinInfo ->
            val updatedTicker = existingCoinInfo.ticker.copy(
                tradePrice = wsData.tradePrice,
                signedChangeRate = wsData.signedChangeRate,
                accTradePrice24h = wsData.accTradePrice24h
            )

            val updatedCoinInfo = existingCoinInfo.copy(ticker = updatedTicker)

            currentCoins[wsData.code] = updatedCoinInfo
        } ?: run {
            return
        }

        coinsMap.value = currentCoins

        val top20Coins = currentCoins.values
            .sortedByDescending { it.ticker.accTradePrice24h }
            .take(20)

        _coins.value = top20Coins
    }

    override fun onCleared() {
        super.onCleared()
        mainRepository.disconnectWebSocket()
    }
}