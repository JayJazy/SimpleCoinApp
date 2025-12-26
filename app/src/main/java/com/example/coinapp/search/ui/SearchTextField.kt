package com.example.coinapp.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coinapp.R

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            placeholder = {
                Text(
                    text = "코인 검색",
                    color = colorResource(id = R.color.gray)
                )
            },
            trailingIcon = {
                if (value.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            onValueChange("")
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ico_delete),
                            contentDescription = "",
                            tint = colorResource(id = R.color.black),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(id = R.color.light_gray),
                unfocusedContainerColor = colorResource(id = R.color.light_gray),
                disabledContainerColor = colorResource(id = R.color.light_gray),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchTextField() {
    SearchTextField(
        value = "",
        onValueChange = {}
    )
}