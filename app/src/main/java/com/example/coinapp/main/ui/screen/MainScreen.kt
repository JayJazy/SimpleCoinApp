package com.example.coinapp.main.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coinapp.R
import com.example.coinapp.deeplink.DeepLinkBuilder
import com.example.coinapp.deeplink.DeepLinkBuilder.QUERY
import com.example.coinapp.deeplink.DeepLinkRouter
import com.example.coinapp.deeplink.DeepLinkRouter.TRADING
import com.example.coinapp.main.data.model.CoinInfo
import com.example.coinapp.main.ui.CoinListItem
import com.example.coinapp.main.ui.SearchTopBar
import com.example.coinapp.main.ui.viewmodel.MainViewModel
import com.example.coinapp.ui.common.LoadingProgress
import com.example.coinapp.ui.common.SpacerHeight
import com.example.coinapp.ui.common.clickableNoRipple

@Composable
fun MainScreen(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SearchTopBar(
                onClick = onSearchClick,
                modifier = Modifier.padding(top = 20.dp)
            )
        },
        containerColor = colorResource(id = R.color.white)
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingProgress(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            else -> {
                MainContent(
                    coins = uiState.coins,
                    onClick = { coinMarket, koreanName->
                        val tradingUri = DeepLinkBuilder.createDeepLink(hostName = TRADING, QUERY to listOf(coinMarket, koreanName))
                        DeepLinkRouter.navigate(context, tradingUri)
                    },
                    modifier = modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun MainContent(
    coins: List<CoinInfo>,
    onClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        itemsIndexed(
            items = coins,
            key = { _, coinInfo -> coinInfo.market }
        ) { index, coinInfo ->
            CoinListItem(
                index = index + 1,
                coinKorName = coinInfo.koreanName,
                coinName = coinInfo.market.replace("KRW-", ""),
                tradePrice = coinInfo.ticker.tradePrice,
                signedChangeRate = coinInfo.ticker.signedChangeRate * 100,
                modifier = Modifier
                    .clickableNoRipple { onClick(coinInfo.market, coinInfo.koreanName) }
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            if (index == coins.size - 1) SpacerHeight(60.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMainContent() {
    MainContent(
        coins = emptyList(),
        onClick = {_, _ ->}
    )
}