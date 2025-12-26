package com.example.coinapp.deeplink

import android.net.Uri

object DeepLinkBuilder {
    private const val SCHEME = "mycoinsample"

    const val QUERY = "query"

    fun createDeepLink(
        hostName: String,
        vararg params: Pair<String, List<Any>?>,
    ): Uri {
        return Uri.Builder()
            .scheme(SCHEME)
            .authority(hostName)
            .apply {
                params.forEach { (key, value) ->
                    value?.let {
                        val stringValue = it.joinToString(",")
                        appendQueryParameter(key, stringValue)
                    }
                }
            }
            .build()
    }
}