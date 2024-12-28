package me.axiumyu.smartscreen

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getCpuUsage(): Float

expect fun getMemoryUsage(): Float

expect fun getCurrentTime(): String

