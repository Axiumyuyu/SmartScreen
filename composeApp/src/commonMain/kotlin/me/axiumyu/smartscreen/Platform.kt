package me.axiumyu.smartscreen

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

// commonMain
expect fun getCpuUsage(): Float

expect fun getMemoryUsage(): Float
