package me.axiumyu.smartscreen

import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.lang.management.ManagementFactory

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual fun getMemoryUsage(): Float {
    val runtime = Runtime.getRuntime()
    val totalMemory = runtime.totalMemory().toFloat()
    val freeMemory = runtime.freeMemory().toFloat()
    return ((totalMemory - freeMemory) / totalMemory).coerceIn(0.0f, 1.0f)
}

actual fun getCurrentTime(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return LocalDateTime.now().format(formatter)
}

actual fun getCpuUsage(): Float {
    val osName = System.getProperty("os.name").lowercase()

    return if (osName.contains("win")) {
        // Windows 使用 wmic 命令获取 CPU 使用情况
        getCpuUsageWindows()
    } else {
        // Linux 使用 top 或 ps 命令
        getCpuUsageLinux()
    }
}

fun getCpuUsageWindows(): Float{
    // 使用 wmic 命令获取 CPU 使用率
    val process = ProcessBuilder("wmic", "cpu", "get", "loadpercentage").start()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val output = StringBuilder()
    var line: String?
    while (reader.readLine().also { line = it } != null) {
        if (line?.contains("LoadPercentage") == true) continue // 跳过标题行
        output.append(line).append("\n")
    }
    return extractTotalCpuUsage(output.toString())
}

fun getCpuUsageLinux(): Float {
    // 使用 top 命令获取 CPU 使用情况
    val process = ProcessBuilder("top", "-bn", "1").start()
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val output = StringBuilder()
    var line: String?
    while (reader.readLine().also { line = it } != null) {
        if (line!!.contains("Cpu(s)")) {
            output.append(line).append("\n")
        }
    }
    return extractTotalCpuUsage(output.toString())
}

fun extractTotalCpuUsage(cpuInfo: String): Float {
    val regex = """%Cpu\(s\):\s+([\d.]+)\s+us,\s+([\d.]+)\s+sy""".toRegex()
    val matchResult = regex.find(cpuInfo) ?: throw IllegalArgumentException("Invalid CPU info format")

    val us = matchResult.groupValues[1].toFloat()
    val sy = matchResult.groupValues[2].toFloat()

    return (us + sy)/100
}
