package com.example.coinapp.trade.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coinapp.R
import com.example.coinapp.trade.ui.model.OrderBookItem
import com.example.coinapp.trade.ui.model.TradingTickerInfo
import com.example.coinapp.ui.theme.TextStyleType
import com.example.coinapp.ui.theme.typography
import com.example.coinapp.utils.toFormattedPriceWithLimit

@Composable
fun TradingOrderBook(
    scrollState: LazyListState,
    orderBookItems: List<OrderBookItem>,
    tradingTickerInfo: TradingTickerInfo,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // 헤더
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OrderBookCell(
                text = "잔량",
                alignment = Alignment.CenterStart,
                isHeader = true,
                modifier = Modifier.weight(1f)
            )

            OrderBookCell(
                text = "가격",
                alignment = Alignment.Center,
                isHeader = true,
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(colorResource(id = R.color.light_gray))
                    .padding(vertical = 8.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = "거래정보",
                    style = typography(TextStyleType.Body2).copy(
                        color = colorResource(id = R.color.gray)
                    ),
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyColumn(
                state = scrollState,
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(
                    items = orderBookItems
                ) { index, item ->
                    val bgColor = if (item.isBid)
                        colorResource(id = R.color.light_red2).copy(alpha = 0.3f)
                    else
                        colorResource(id = R.color.light_blue2).copy(alpha = 0.3f)

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OrderBookCell(
                            text = item.quantity.toFormattedPriceWithLimit(),
                            alignment = Alignment.CenterStart,
                            isHeader = false,
                            backgroundColor = bgColor,
                            modifier = Modifier.weight(1f)
                        )

                        OrderBookCell(
                            text = item.price.toFormattedPriceWithLimit(),
                            alignment = Alignment.Center,
                            textColor = if (item.isBid) colorResource(id = R.color.red) else colorResource(id = R.color.blue),
                            isHeader = false,
                            modifier = Modifier.weight(1f)
                        )

                        // 빈 공간
                        Box(
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // 거래 정보
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterEnd)
            ) {
                Box(
                    modifier = Modifier
                        .weight(2f)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    val tradeInfoItems = listOf(
                        Triple("52주 최고", tradingTickerInfo.highest52WeekPrice, colorResource(id = R.color.red)),
                        Triple("52주 최저", tradingTickerInfo.lowest52WeekPrice, colorResource(id = R.color.blue)),
                        Triple("거래대금(24H)", tradingTickerInfo.accTradePrice24h, colorResource(id = R.color.black)),
                        Triple("최근 거래량", tradingTickerInfo.tradeVolume, colorResource(id = R.color.black)),
                        Triple("거래량(24H)", tradingTickerInfo.accTradeVolume24h, colorResource(id = R.color.black))
                    )

                    tradeInfoItems.forEach { (label, value, color) ->
                        OrderBookGroupCell(
                            label = label,
                            value = value,
                            valueColor = color,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTradingOrderBook() {
    val sampleData = listOf(
        OrderBookItem(price = 100000000.0, quantity = 1.5, volume = 150000000.0, isBid = false),
        OrderBookItem(price = 99900000.0, quantity = 2.3, volume = 229770000.0, isBid = false),
        OrderBookItem(price = 99800000.0, quantity = 0.8, volume = 79840000.0, isBid = false),
        OrderBookItem(price = 99700000.0, quantity = 3.2, volume = 319040000.0, isBid = true),
        OrderBookItem(price = 99600000.0, quantity = 1.1, volume = 109560000.0, isBid = true),
        OrderBookItem(price = 99500000.0, quantity = 4.5, volume = 447750000.0, isBid = true),
    )

    val sampleTickerInfo = TradingTickerInfo(
        highest52WeekPrice = 150000000.0,
        lowest52WeekPrice = 45000000.0,
        accTradePrice24h = 12345678900.0,
        tradeVolume = 123.456,
        accTradeVolume24h = 987654.321
    )

    TradingOrderBook(
        scrollState = LazyListState(),
        orderBookItems = sampleData,
        tradingTickerInfo = sampleTickerInfo
    )
}