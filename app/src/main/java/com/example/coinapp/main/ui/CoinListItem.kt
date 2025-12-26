package com.example.coinapp.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coinapp.R
import com.example.coinapp.ui.common.SpacerWidth
import com.example.coinapp.ui.theme.TextStyleType
import com.example.coinapp.ui.theme.typography
import com.example.coinapp.utils.toCommaString
import com.example.coinapp.utils.toFormattedPriceString

@Composable
fun CoinListItem(
    index: Int,
    coinKorName: String,
    coinName: String,
    tradePrice: Double,
    signedChangeRate: Double,
    modifier: Modifier = Modifier
) {
    val indexColor = if (index < 4) colorResource(id = R.color.light_red) else colorResource(id = R.color.light_blue)

    val rateText = when {
        signedChangeRate > 0.0 -> "+${"%.5f".format(signedChangeRate)}%"
        else -> "${"%.5f".format(signedChangeRate)}%"
    }

    val priceColor = when {
        signedChangeRate > 0.0 -> colorResource(id = R.color.red)
        signedChangeRate < 0.0 -> colorResource(id = R.color.blue)
        else -> colorResource(id = R.color.black)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$index",
            style = typography(TextStyleType.Body1).copy(
                color = indexColor
            )
        )

        SpacerWidth(20.dp)


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = coinKorName,
                style = typography(TextStyleType.Title3)
            )

            SpacerWidth(2.dp)

            Text(
                text = "(${coinName})",
                style = typography(TextStyleType.Body2).copy(
                    color = colorResource(id = R.color.gray)
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = tradePrice.toFormattedPriceString(),
                style = typography(TextStyleType.Body1).copy(
                    color = priceColor
                )
            )

            Text(
                text = rateText,
                style = typography(TextStyleType.Body2).copy(
                    color = priceColor
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCoinListItem() {
    CoinListItem(
        index = 3,
        coinKorName = "비트코인",
        coinName = "BTC",
        tradePrice = 110000.00,
        signedChangeRate = 12.41
    )
}