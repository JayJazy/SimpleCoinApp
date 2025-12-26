package com.example.coinapp.trade.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coinapp.R
import com.example.coinapp.ui.theme.TextStyleType
import com.example.coinapp.ui.theme.typography
import com.example.coinapp.utils.toFormattedPriceWithLimit

@Composable
fun OrderBookGroupCell(
    label: String,
    value: Double,
    modifier: Modifier = Modifier,
    valueColor: Color = colorResource(id = R.color.black)
) {
    Box(
        modifier = modifier
            .background(colorResource(id = R.color.white))
            .padding(vertical = 8.dp, horizontal = 12.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = label,
                style = typography(TextStyleType.Body2),
                maxLines = 1
            )

            Text(
                text = value.toFormattedPriceWithLimit(),
                style = typography(TextStyleType.Body2).copy(
                    color = valueColor
                ),
                fontSize = 10.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOrderBookGroupCell() {
    Column {
        OrderBookGroupCell(
            label = "52주 최고",
            value = 1234567.89
        )
        OrderBookGroupCell(
            label = "52주 최저",
            value = 451151.0
        )
    }
}

