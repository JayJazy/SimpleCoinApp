package com.example.coinapp.deeplink

import android.content.Context
import android.content.Intent
import android.net.Uri

object DeepLinkRouter {
    const val MAIN = "main"
    const val SEARCH = "search"
    const val TRADING = "trading"

    /**
     * DeepLink URI를 분석하고 적절한 화면으로 이동
     * @param context Context
     * @param uri DeepLink URI (예: mycoinsample://trading?symbol=BTC)
     */
    fun navigate(context: Context, uri: Uri) {
        val destination = findDestination(uri)
        navigateToDestination(context, destination)
    }

    private fun findDestination(uri: Uri): DeepLinkDestination {
        return when (uri.host) {
            SEARCH -> DeepLinkDestination.Search(uri)
            TRADING -> DeepLinkDestination.Trading(uri)
            MAIN -> DeepLinkDestination.Main(uri)
            else -> DeepLinkDestination.Unknown
        }
    }

    private fun navigateToDestination(context: Context, destination: DeepLinkDestination) {
        Intent(context, destination.activity).apply {
            destination.action?.let { action = it }
            destination.arguments?.let { putExtras(it) }
            addFlags(destination.flags)
        }.also { context.startActivity(it) }
    }
}