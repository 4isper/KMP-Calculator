package com.example.kmpproject

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.*
import platform.CoreGraphics.CGFloat

fun MainViewController() = ComposeUIViewController {
    val insets = remember { getSafeAreaInsets() }  // Получаем Safe Area Insets

    App(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = with(LocalDensity.current) { insets.top.toDp() },
                bottom = with(LocalDensity.current) { insets.bottom.toDp() },
                start = with(LocalDensity.current) { insets.left.toDp() },
                end = with(LocalDensity.current) { insets.right.toDp() }
            )
    )
}

@OptIn(ExperimentalForeignApi::class)
fun getSafeAreaInsets(): UIEdgeInsets {
    // Извлечение значений safeAreaInsets с помощью useContents
    val insets = UIApplication.sharedApplication.keyWindow?.safeAreaInsets
    return insets?.useContents { this } ?: UIEdgeInsetsZero
}

// Extension function для перевода CGFloat в Dp
fun CGFloat.toDp(): androidx.compose.ui.unit.Dp = (this / UIScreen.mainScreen.scale).dp
