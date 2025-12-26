package com.example.coinapp.utils

import java.math.BigDecimal
import java.text.DecimalFormat

fun Int.toCommaString(): String {
    val formatter = DecimalFormat("#,###")
    return formatter.format(this)
}

// 예 : 100,000,000.000041
fun Double.toFormattedPriceString(): String {
    val bigDecimal = BigDecimal(this.toString())
    val plainString = bigDecimal.toPlainString()

    if (!plainString.contains(".")) {
        val formatter = DecimalFormat("#,###")
        return formatter.format(bigDecimal.toLong())
    }

    val parts = plainString.split(".")
    val integerPart = parts[0].toLongOrNull() ?: 0L
    val decimalPart = parts.getOrNull(1) ?: ""

    val formatter = DecimalFormat("#,###")
    val formattedInteger = formatter.format(integerPart)

    return if (decimalPart.isNotEmpty() && decimalPart.any { it != '0' }) {
        "$formattedInteger.$decimalPart"
    } else {
        formattedInteger
    }
}

// 예: 100,000,000.123 (소수 3째짜리 까지 표시)
fun Double.toFormattedPriceWithLimit(): String {
    val bigDecimal = BigDecimal(this.toString())
    val plainString = bigDecimal.toPlainString()

    if (!plainString.contains(".")) {
        val formatter = DecimalFormat("#,###")
        return formatter.format(bigDecimal.toLong())
    }

    val parts = plainString.split(".")
    val integerPart = parts[0].toLongOrNull() ?: 0L
    val decimalPart = parts.getOrNull(1) ?: ""

    val formatter = DecimalFormat("#,###")
    val formattedInteger = formatter.format(integerPart)

    // 소수점 3자리까지만 자르기
    val limitedDecimal = if (decimalPart.length > 3) {
        decimalPart.substring(0, 3)
    } else {
        decimalPart
    }

    return if (limitedDecimal.isNotEmpty() && limitedDecimal.any { it != '0' }) {
        "$formattedInteger.$limitedDecimal"
    } else {
        formattedInteger
    }
}

fun String.toChosung(): String {
    val chosungList = listOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ',
        'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ',
        'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    return this.map { char ->
        if (char in '가'..'힣') {
            val unicode = char.code - 0xAC00
            val chosungIndex = unicode / (21 * 28)  // 중성 21개 × 종성 28개
            chosungList[chosungIndex]
        } else {
            char
        }
    }.joinToString("")
}

fun String.matchesChosung(query: String): Boolean {
    if (query.isBlank()) return true

    val lowerQuery = query.lowercase()
    val lowerThis = this.lowercase()

    // 1. 일반 문자열 포함 검사
    if (lowerThis.contains(lowerQuery)) return true

    // 2. 초성 검사
    val chosung = this.toChosung().lowercase()
    if (chosung.contains(lowerQuery)) return true

    // 3. 시작 문자 검사 (B로 시작하는지)
    if (lowerThis.startsWith(lowerQuery)) return true

    return false
}