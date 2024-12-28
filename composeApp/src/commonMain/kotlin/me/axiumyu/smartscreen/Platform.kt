package me.axiumyu.smartscreen

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getTime(): String