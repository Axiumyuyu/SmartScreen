package me.axiumyu.smartscreen

import CircularProgressIndicator
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.lang.management.ManagementFactory

@Composable
fun RealtimeSystemStatus() {
    // 定义状态
    var cpuUsage by remember { mutableStateOf(0.0f) }
    var memoryUsage by remember { mutableStateOf(0.0f) }

    // 定时更新状态
    LaunchedEffect(Unit) {
        while (true) {
            cpuUsage = getCpuUsage()
            memoryUsage = getMemoryUsage()
            delay(1000) // 每秒更新一次
        }
    }

    // 显示圆环和数据
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CircularProgressIndicator(
            percentage = cpuUsage,
            title = "CPU 使用率",
            color = Color(0xFF42A5F5)
        )
        CircularProgressIndicator(
            percentage = memoryUsage,
            title = "内存使用率",
            color = Color(0xFF66BB6A)
        )
    }
}
/*
@Composable
fun CircularProgressIndicator(
    percentage: Float, // 占比（0.0 到 1.0）
    title: String,
    color: Color,
    size: Float = 100f // 圆环直径
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // 绘制圆环
        Canvas(modifier = Modifier.size(size.dp)) {
            // 背景圆环
            drawArc(
                color = Color.Gray.copy(alpha = 0.3f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 12f, cap = StrokeCap.Round)
            )
            // 前景圆弧
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = percentage * 360f,
                useCenter = false,
                style = Stroke(width = 12f, cap = StrokeCap.Round)
            )
        }

        // 显示占比文字
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${(percentage * 100).toInt()}%",
                fontSize = 18.sp,
                color = color
            )
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}*/

/*
// 获取 CPU 使用率
fun getCpuUsage(): Float {
    val osBean = ManagementFactory.getOperatingSystemMXBean() as com.sun.management.OperatingSystemMXBean
    return (osBean.systemCpuLoad.coerceIn(0.0, 1.0).toFloat())
}

// 获取内存使用率
fun getMemoryUsage(): Float {
    val runtime = Runtime.getRuntime()
    val totalMemory = runtime.totalMemory().toFloat()
    val freeMemory = runtime.freeMemory().toFloat()
    return ((totalMemory - freeMemory) / totalMemory).coerceIn(0.0f, 1.0f)
}*/
