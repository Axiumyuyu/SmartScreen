package me.axiumyu.smartscreen

import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun getMemoryUsage(): Float {
    TODO("Not yet implemented")
}

actual fun getCpuUsage(): Float {
    TODO("Not yet implemented")
}