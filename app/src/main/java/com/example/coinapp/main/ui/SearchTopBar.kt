package com.example.coinapp.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.coinapp.ui.common.clickableNoRipple
import com.example.coinapp.ui.theme.TextStyleType
import com.example.coinapp.ui.theme.typography

@Composable
fun SearchTopBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .clickableNoRipple { onClick() }
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = colorResource(id = R.color.light_gray),
                    shape = RoundedCornerShape(6.dp)
                )
                .fillMaxSize()
                .weight(1f, fill = false)
        ) {
            Text(
                text = "검색",
                style = typography(TextStyleType.Body2).copy(
                    color = colorResource(id = R.color.gray)
                ),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterStart)
            )
        }

        SpacerWidth(10.dp)

        Icon(
            painter = painterResource(id = R.drawable.ico_search),
            contentDescription = "",
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchTopBar() {
    SearchTopBar(
        onClick = {}
    )
}