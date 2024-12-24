import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CircularProgressPanel() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CircularProgressIndicator(
            percentage = 0.45f,
            title = "CPU 使用率",
            color = Color(0xFF42A5F5)
        )
        CircularProgressIndicator(
            percentage = 0.68f,
            title = "内存使用率",
            color = Color(0xFF66BB6A)
        )
        CircularProgressIndicator(
            percentage = 0.72f,
            title = "磁盘使用率",
            color = Color(0xFFEF5350)
        )
    }
}
