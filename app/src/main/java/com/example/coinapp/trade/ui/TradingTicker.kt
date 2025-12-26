package com.example.coinapp.trade.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coinapp.R
import com.example.coinapp.ui.common.SpacerWidth
import com.example.coinapp.ui.theme.TextStyleType
import com.example.coinapp.ui.theme.typography
import com.example.coinapp.utils.toFormattedPriceString

/**
 * @param coinKorName 한국 코인 이름
 * @param coinName market 코인 이름
 * @param tradePrice 현재가
 * @param signedChangeRate 전일 대비 등락율
 * @param signedChangePrice 전일 대비 가격 변동 값
 */
@Composable
fun TradingTicker(
    coinKorName: String,
    coinName: String,
    tradePrice: Double,
    signedChangeRate: Double,
    signedChangePrice: Double,
    modifier: Modifier = Modifier
) {
    val priceColor = when {
        signedChangeRate > 0.0 -> colorResource(id = R.color.red)
        signedChangeRate < 0.0 -> colorResource(id = R.color.blue)
        else -> colorResource(id = R.color.black)
    }

    val rateText = when {
        signedChangeRate > 0.0 -> "+${"%.5f".format(signedChangeRate)}%"
        else -> "${"%.5f".format(signedChangeRate)}%"
    }

    val directionIcon = when {
        signedChangeRate > 0.0 -> painterResource(id = R.drawable.ico_rate_direction_top)
        else -> painterResource(id = R.drawable.ico_rate_direction_bottom)
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = coinKorName,
                style = typography(TextStyleType.Title1)
            )

            SpacerWidth(2.dp)

            Text(
                text = "(${coinName})",
                style = typography(TextStyleType.Body2).copy(
                    color = colorResource(id = R.color.gray)
                )
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = tradePrice.toFormattedPriceString(),
                style = typography(TextStyleType.Title2).copy(
                    color = priceColor
                )
            )

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rateText,
                    style = typography(TextStyleType.Body2).copy(
                        color = priceColor
                    )
                )

                SpacerWidth(20.dp)


                Row(
                  verticalAlignment = Alignment.CenterVertically
                ) {
                    if (signedChangeRate != 0.0) {
                        Icon(
                            painter = directionIcon,
                            contentDescription = "",
                            tint = priceColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    SpacerWidth(4.dp)

                    Text(
                        text = signedChangePrice.toFormattedPriceString(),
                        style = typography(TextStyleType.Body2).copy(
                            color = priceColor
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTradingTicker() {
    TradingTicker(
        coinKorName = "비트코인",
        coinName = "BTC",
        tradePrice = 1000000000.56,
        signedChangeRate = 12.12,
        signedChangePrice = 20.0,
    )
}