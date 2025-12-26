package com.example.coinapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.coinapp.R
import com.example.coinapp.ui.theme.TextStyleType
import com.example.coinapp.ui.theme.typography

@Composable
fun SpacerWidth(dp: Dp) {
    Spacer(modifier = Modifier.width(dp))
}

@Composable
fun SpacerHeight(dp: Dp) {
    Spacer(modifier = Modifier.height(dp))
}

@Composable
fun LoadingProgress(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun BackTopBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = ""
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ico_back),
            contentDescription = "",
            modifier = Modifier
                .clickableNoRipple { onClick() }
                .size(36.dp)
                .align(Alignment.CenterVertically)
        )

        SpacerWidth(10.dp)

        Text(
            text = text,
            style = typography(TextStyleType.Title2)
        )
    }
}