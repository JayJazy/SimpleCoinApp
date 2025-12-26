package com.example.coinapp.search.data.repository

import com.example.coinapp.data.model.UpbitMarket
import com.example.coinapp.data.service.UpbitApiService
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: UpbitApiService
) {
    suspend fun getAllMarkets(): List<UpbitMarket> {
        return service.getMarkets()
    }
}