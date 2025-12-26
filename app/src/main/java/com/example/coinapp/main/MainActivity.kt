package com.example.coinapp.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.coinapp.deeplink.DeepLinkBuilder
import com.example.coinapp.deeplink.DeepLinkRouter
import com.example.coinapp.deeplink.DeepLinkRouter.SEARCH
import com.example.coinapp.main.ui.screen.MainScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleDeepLink()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeepLink()

        val searchUri = DeepLinkBuilder.createDeepLink(hostName = SEARCH)

        setContent {
            MainScreen(
                onSearchClick = { DeepLinkRouter.navigate(this@MainActivity, searchUri) }
            )
        }
    }

    private fun handleDeepLink() {
        intent.data?.let { uri ->
            if (uri.host != DeepLinkRouter.MAIN) {
                DeepLinkRouter.navigate(this, uri)
                finish()
            }
        }
    }
}