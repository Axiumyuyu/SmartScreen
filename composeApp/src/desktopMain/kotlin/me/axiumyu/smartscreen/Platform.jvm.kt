package me.axiumyu.smartscreen

import java.lang.management.ManagementFactory

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getCpuUsage(): Float {
    val osBean = ManagementFactory.getOperatingSystemMXBean() as com.sun.management.OperatingSystemMXBean
    return (osBean.systemCpuLoad.coerceIn(0.0, 1.0).toFloat())
}

actual fun getMemoryUsage(): Float {
    val runtime = Runtime.getRuntime()
    val totalMemory = runtime.totalMemory().toFloat()
    val freeMemory = runtime.freeMemory().toFloat()
    return ((totalMemory - freeMemory) / totalMemory).coerceIn(0.0f, 1.0f)
}
