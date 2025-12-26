package com.example.coinapp.trade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.coinapp.deeplink.DeepLinkBuilder.QUERY
import com.example.coinapp.trade.ui.screen.TradingScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlin.text.split

@AndroidEntryPoint
class TradingActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val query = intent.getStringExtra(QUERY) ?: "Unknown"

        setContent {
            val market = query.split(",").map { it.trim() }.firstOrNull() ?: ""

            TradingScreen(
                market = market,
                onFinish = { finish() }
            )
        }
    }
}