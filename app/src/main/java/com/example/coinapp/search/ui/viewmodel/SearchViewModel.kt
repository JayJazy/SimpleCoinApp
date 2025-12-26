package com.example.coinapp.search.ui.viewmodel

import com.example.coinapp.BaseViewModel
import com.example.coinapp.data.model.UpbitMarket
import com.example.coinapp.search.data.repository.SearchRepository
import com.example.coinapp.utils.matchesChosung
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : CoroutineScope, BaseViewModel() {

    private var totalMarkets = emptyList<UpbitMarket>()

    private val _allMarkets = MutableStateFlow(emptyList<UpbitMarket>())
    val allMarkets = _allMarkets.asStateFlow()

    init {
        fetchAllMarkets()
    }

    private fun fetchAllMarkets() {
        launch {
            totalMarkets = searchRepository.getAllMarkets()
        }
    }

    fun searchCoin(query: String) {
        if (query.isBlank()) {
            _allMarkets.value = emptyList()
            return
        }

        val filteredMarkets = totalMarkets.filter { market ->
            market.market.matchesChosung(query) ||
                    market.koreanName.matchesChosung(query) ||
                    market.englishName.matchesChosung(query)
        }

        _allMarkets.value = filteredMarkets
    }
}
