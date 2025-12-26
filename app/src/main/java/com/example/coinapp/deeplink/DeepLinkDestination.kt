package com.example.coinapp.deeplink

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.example.coinapp.main.MainActivity
import com.example.coinapp.search.SearchActivity
import com.example.coinapp.trade.TradingActivity
import com.example.coinapp.UnknownActivity
import com.example.coinapp.deeplink.DeepLinkBuilder.QUERY

sealed class DeepLinkDestination(
    val activity: Class<out Activity>,
    val arguments: Bundle? = null,
    val flags: Int = 0,
    val action: String? = null
) {
    // TODO (영석) : 만약 registerForActivityResult를 사용해야 한다면 어떻게 할 것인가?

    data class Main(val uri: Uri): DeepLinkDestination(
        activity = MainActivity::class.java
    )

    data class Search(val uri: Uri): DeepLinkDestination(
        activity = SearchActivity::class.java
    )

    data class Trading(val uri: Uri): DeepLinkDestination(
        activity = TradingActivity::class.java,
        arguments = Bundle().apply {
            val symbol = uri.getQueryParameter(QUERY)
            putString(QUERY, symbol)
        },
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    )

    data object Unknown: DeepLinkDestination(
        activity = UnknownActivity::class.java,
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    )
}