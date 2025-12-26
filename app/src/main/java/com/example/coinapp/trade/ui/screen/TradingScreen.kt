package com.example.coinapp.trade.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coinapp.R
import com.example.coinapp.trade.ui.TradingOrderBook
import com.example.coinapp.trade.ui.TradingTicker
import com.example.coinapp.trade.ui.model.OrderBookItem
import com.example.coinapp.trade.ui.model.TradingTickerInfo
import com.example.coinapp.trade.ui.viewmodel.TradingViewModel
import com.example.coinapp.ui.common.BackTopBar
import com.example.coinapp.ui.common.LoadingProgress
import com.example.coinapp.ui.common.SpacerHeight

@Composable
fun TradingScreen(
    market: String,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TradingViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()

    LaunchedEffect(uiState.isLoading, uiState.orderBookItems.size, uiState.askCount) {
        if (!uiState.isLoading && uiState.orderBookItems.isNotEmpty() && uiState.askCount > 0) {
            val visibleItemsCount = scrollState.layoutInfo.visibleItemsInfo.size
            val itemsToShow = if (visibleItemsCount > 0) visibleItemsCount else 10
            val halfVisible = itemsToShow / 2
            val scrollIndex = (uiState.askCount - halfVisible).coerceAtLeast(0)

            scrollState.scrollToItem(scrollIndex)
        }
    }

    Scaffold(
        topBar = {
            BackTopBar(
                text = market,
                onClick = onFinish
            )
        },
        containerColor = colorResource(id = R.color.white)
    ) { paddingValues ->
        if (uiState.isLoading) {
            LoadingProgress(modifier = modifier.fillMaxSize())
        } else {
            TradingContent(
                scrollState = scrollState,
                tradingTickerInfo = uiState.tradingTickerInfo,
                orderBookItems = uiState.orderBookItems,
                modifier = modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun TradingContent(
    scrollState: LazyListState,
    tradingTickerInfo: TradingTickerInfo,
    orderBookItems: List<OrderBookItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TradingTicker(
            coinKorName = tradingTickerInfo.coinKorName,
            coinName = tradingTickerInfo.coinName,
            tradePrice = tradingTickerInfo.tradePrice,
            signedChangeRate = tradingTickerInfo.signedChangeRate,
            signedChangePrice = tradingTickerInfo.signedChangePrice,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        SpacerHeight(10.dp)

        TradingOrderBook(
            scrollState = scrollState,
            orderBookItems = orderBookItems,
            tradingTickerInfo = tradingTickerInfo,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTradingContent() {
    val sampleOrderBookItems = listOf(
        OrderBookItem(price = 100000000.0, quantity = 1.5, volume = 150000000.0, isBid = false),
        OrderBookItem(price = 99900000.0, quantity = 2.3, volume = 229770000.0, isBid = false),
        OrderBookItem(price = 99800000.0, quantity = 0.8, volume = 79840000.0, isBid = false),
        OrderBookItem(price = 99700000.0, quantity = 3.2, volume = 319040000.0, isBid = true),
        OrderBookItem(price = 99600000.0, quantity = 1.1, volume = 109560000.0, isBid = true),
        OrderBookItem(price = 99500000.0, quantity = 4.5, volume = 447750000.0, isBid = true)
    )

    val sampleTickerInfo = TradingTickerInfo(
        highest52WeekPrice = 150000000.0,
        lowest52WeekPrice = 45000000.0,
        accTradePrice24h = 12345678900.0,
        tradeVolume = 123.456,
        accTradeVolume24h = 987654.321
    )

    TradingContent(
        scrollState = LazyListState(),
        tradingTickerInfo = sampleTickerInfo,
        orderBookItems = sampleOrderBookItems
    )
}