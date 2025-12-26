package com.example.coinapp.main.ui.model

import com.example.coinapp.main.data.model.CoinInfo

data class MainUiState(
    val isLoading: Boolean = true,
    val coins: List<CoinInfo> = emptyList()
)