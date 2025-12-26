package com.example.coinapp.ui.theme


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.coinapp.R

enum class TextStyleType {
    HeadLine,
    Title1, Title2, Title3, Title4,
    Body1, Body2
}

val Pretendard = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.pretendard_bold, FontWeight.Bold, FontStyle.Normal)
)
val Int.nonScaledSp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

@Composable
fun typography(textStyle: TextStyleType): TextStyle {
    return when (textStyle) {
        TextStyleType.HeadLine -> {
            TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 40.nonScaledSp,
                lineHeight = 50.nonScaledSp,
                color = colorResource(R.color.black),
            )
        }

        TextStyleType.Title1 -> {
            TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 20.nonScaledSp,
                lineHeight = 27.nonScaledSp,
                color = colorResource(R.color.black),
            )
        }

        TextStyleType.Title2 -> {
            TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 17.nonScaledSp,
                lineHeight = 23.nonScaledSp,
                color = colorResource(R.color.black),
            )
        }

        TextStyleType.Title3 -> {
            TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Bold,
                fontSize = 15.nonScaledSp,
                lineHeight = 20.nonScaledSp,
                color = colorResource(R.color.black),
            )
        }

        TextStyleType.Title4 -> TextStyle(
            fontFamily = Pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 12.nonScaledSp,
            lineHeight = 16.nonScaledSp,
            color = colorResource(R.color.black),
        )

        TextStyleType.Body1 -> {
            TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 17.nonScaledSp,
                lineHeight = 23.nonScaledSp,
                color = colorResource(R.color.black),
            )
        }

        TextStyleType.Body2 -> {
            TextStyle(
                fontFamily = Pretendard,
                fontWeight = FontWeight.Normal,
                fontSize = 14.nonScaledSp,
                lineHeight = 22.nonScaledSp,
                color = colorResource(R.color.black),
            )
        }
    }
}