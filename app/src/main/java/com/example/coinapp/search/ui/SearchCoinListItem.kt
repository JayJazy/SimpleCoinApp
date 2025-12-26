package com.example.coinapp.search.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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

@Composable
fun SearchCoinListItem(
    coinName: String,
    coinKorName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically,
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
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchCoinListItem() {
    SearchCoinListItem(
        coinName = "BTC",
        coinKorName = "비트코인"
    )
}