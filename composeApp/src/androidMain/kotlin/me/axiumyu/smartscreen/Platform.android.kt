
package me.axiumyu.smartscreen

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import java.io.IOException
import java.io.RandomAccessFile
import java.text.SimpleDateFormat
import java.util.*

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


actual fun getCpuUsage(): Float {
    try {
        val reader = RandomAccessFile("/proc/stat", "r")
        val load = reader.readLine()
        val toks = load.split(" ")
        val idle1 = toks[5].toLong()
        val cpu1 = toks[2].toLong() + toks[3].toLong() + toks[4].toLong() + toks[6].toLong() + toks[7].toLong() + toks[8].toLong()
        try {
            Thread.sleep(360)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        reader.seek(0)
        val load2 = reader.readLine()
        reader.close()
        val toks2 = load2.split(" ")
        val idle2 = toks2[5].toLong()
        val cpu2 = toks2[2].toLong() + toks2[3].toLong() + toks2[4].toLong() + toks2[6].toLong() + toks2[7].toLong() + toks2[8].toLong()
        return (cpu2 - cpu1).toFloat() / ((cpu2 + idle2) - (cpu1 + idle1)).toFloat()
    } catch (ex: IOException) {
        ex.printStackTrace()
    }
    return 0f
}

actual fun getMemoryUsage(): Float {
    val am = getActivityManager()
    val mi = ActivityManager.MemoryInfo()
    am.getMemoryInfo(mi)
    //mi.availMem; 当前系统的可用内存
    return (mi.availMem/1024/1024).toFloat()// 将获取的内存大小规格化
}

fun getActivityManager(): ActivityManager {
    val context = AndroidContextProvider.context
    return context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
}

/*
fun getTotalMemory(): Long {
    val str1 = "/proc/meminfo" // 系统内存信息文件
    val str2: String?
    val arrayOfString : List<String>?
    var initialMemory: Long = 0
    try {
        val localFileReader = FileReader(str1)
        val localBufferedReader = BufferedReader(localFileReader, 8192)
        str2 = localBufferedReader.readLine() // 读取meminfo第一行，系统总内存大小
        arrayOfString= str2?.split("\\s+")
        if (arrayOfString != null) {
            for (num in arrayOfString) {
                println("$str2\t$num")
            }
        }
        initialMemory = arrayOfString?.get(1)?.toLongOrNull()
            ?: (0L / 1024) // 获得系统总内存，单位是KB，除以1024转换为MB
        localBufferedReader.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return initialMemory/1024/1024 // Byte转换为KB或者MB，内存大小规格化
}
*/
actual fun getCurrentTime(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return formatter.format(Date())
}