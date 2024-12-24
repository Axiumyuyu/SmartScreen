package me.axiumyu.smartscreen

import DashboardUI
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SmartScreen",
    ) {
        DashboardUI()
    }
}