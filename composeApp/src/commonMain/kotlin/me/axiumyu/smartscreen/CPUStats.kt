package me.axiumyu.smartscreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp


@Composable
fun LineChart(data: List<Float>) {
    // 获取最大和最小的数据点
    val maxData = data.maxOrNull() ?: 1f
    val minData = data.minOrNull() ?: 0f

    // 使用 Modifier 来控制 Canvas 大小，只占用窗口的一部分
    Canvas(
        modifier = Modifier
            .fillMaxWidth(0.8f) // 占据窗口宽度的 80%
            .height(300.dp)      // 固定高度 300dp
            .padding(16.dp)      // 给图表添加内边距
    ) {
        val width = size.width
        val height = size.height

        // 绘制折线图
        val path = Path().apply {
            data.forEachIndexed { index, value ->
                val x = (index.toFloat() / (data.size - 1)) * width
                val y = height - ((value - minData) / (maxData - minData)) * height
                if (index == 0) moveTo(x, y) else lineTo(x, y)
            }
        }

        // 绘制路径
        drawPath(path = path, color = Color.Blue, style = Stroke(width = 3.dp.toPx()))
    }
}

