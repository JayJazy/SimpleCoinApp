package com.example.coinapp.data.service

import com.example.coinapp.data.model.UpbitMarket
import com.example.coinapp.main.data.model.UpbitTicker
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitApiService {

    /**
     * 마켓 코드 조회
     * @param isDetails 유의종목 필드과 같은 상세 정보 노출 여부(선택)
     */
    @GET("market/all")
    suspend fun getMarkets(
        @Query("isDetails") isDetails: Boolean = false
    ): List<UpbitMarket>

    /**
     * 현재가 정보 조회
     * @param markets 마켓 코드 리스트 (ex: KRW-BTC,KRW-ETH)
     */
    @GET("ticker")
    suspend fun getTickers(
        @Query("markets") markets: String
    ): List<UpbitTicker>
}