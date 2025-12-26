package com.example.coinapp.search.ui.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coinapp.R
import com.example.coinapp.data.model.UpbitMarket
import com.example.coinapp.deeplink.DeepLinkBuilder
import com.example.coinapp.deeplink.DeepLinkBuilder.QUERY
import com.example.coinapp.deeplink.DeepLinkRouter
import com.example.coinapp.deeplink.DeepLinkRouter.TRADING
import com.example.coinapp.search.ui.SearchCoinListItem
import com.example.coinapp.search.ui.SearchTextField
import com.example.coinapp.search.ui.viewmodel.SearchViewModel
import com.example.coinapp.ui.common.BackTopBar
import com.example.coinapp.ui.common.SpacerHeight
import com.example.coinapp.ui.common.clickableNoRipple

@Composable
fun SearchScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val allMarkets by viewModel.allMarkets.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { BackTopBar(onClick = onFinish) },
        containerColor = colorResource(id = R.color.white)
    ) { paddingValues ->
        SearchContent(
            allMarkets = allMarkets,
            onSearch = viewModel::searchCoin,
            onClick = { coinMarket, koreanName ->
                val tradingUri = DeepLinkBuilder.createDeepLink(hostName = TRADING, QUERY to listOf(coinMarket, koreanName))
                DeepLinkRouter.navigate(context, tradingUri)
            },
            modifier = modifier.padding(paddingValues)
        )
    }
}

@Composable
private fun SearchContent(
    allMarkets: List<UpbitMarket>,
    onSearch: (String) -> Unit,
    onClick: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        // 검색 TextField
        SearchTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                onSearch(query)
            }
        )

        SpacerHeight(8.dp)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            itemsIndexed(
                items = allMarkets,
                key = { _, coin -> coin.market }
            ) { index, coin ->
                SearchCoinListItem(
                    coinName = coin.market.replace("KRW-", ""),
                    coinKorName = coin.koreanName,
                    modifier = Modifier
                        .clickableNoRipple {
                            onClick(coin.market, coin.koreanName)
                        }
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = colorResource(id = R.color.light_gray)
                )

                if (index == allMarkets.size - 1) SpacerHeight(60.dp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchContent() {
    SearchContent(
        allMarkets = emptyList(),
        onSearch = {},
        onClick = {_, _ ->}
    )
}