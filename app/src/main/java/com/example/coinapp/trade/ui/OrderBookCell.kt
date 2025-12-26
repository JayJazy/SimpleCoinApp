package com.example.coinapp.trade.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coinapp.R
import com.example.coinapp.ui.theme.TextStyleType
import com.example.coinapp.ui.theme.typography

@Composable
fun OrderBookCell(
    text: String,
    alignment: Alignment,
    isHeader: Boolean,
    modifier: Modifier = Modifier,
    backgroundColor: Color = colorResource(id = R.color.white),
    textColor: Color = colorResource(id = R.color.black)
) {
    val lineColor = colorResource(id = R.color.light_gray)

    Box(
        modifier = modifier
            .background(
                if (isHeader) colorResource(id = R.color.light_gray)
                else backgroundColor
            )
            .drawBehind {
                if (!isHeader) {
                    val strokeWidth = 1.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
            }
            .padding(vertical = 8.dp, horizontal = 12.dp),
        contentAlignment = alignment
    ) {
        Text(
            text = text,
            style = if (isHeader) {
                typography(TextStyleType.Body2).copy(
                    color = colorResource(id = R.color.gray)
                )
            } else {
                typography(TextStyleType.Body2).copy(
                    color = textColor
                )
            },
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOrderBookCell() {
    OrderBookCell(
        text = "100000",
        alignment = Alignment.Center,
        isHeader = false
    )
}