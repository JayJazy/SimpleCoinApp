package com.example.coinapp.search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.coinapp.search.ui.screen.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SearchScreen(
                onFinish = { finish() }
            )
        }
    }
}