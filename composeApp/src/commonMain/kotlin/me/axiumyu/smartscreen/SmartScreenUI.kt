import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import me.axiumyu.smartscreen.LineChart
import me.axiumyu.smartscreen.getCpuUsage
import me.axiumyu.smartscreen.getCurrentTime
import me.axiumyu.smartscreen.getMemoryUsage
import kotlin.random.Random

//主UI
val cpu_usage = listOf(0.22, 0.25,0.19,0.24, 0.34, 0.41,0.40, 0.35, 0.29, 0.22, 0.18, 0.16, 0.15, 0.18, 0.18, 0.29, 0.26, 0.22, 0.23, 0.22, 0.20, 0.18)

@Composable
fun DashboardUI() {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF1E1E1E))) {
        /*Header(title = "智慧大屏 - 服务器运行状况")
        Spacer(modifier = Modifier.height(16.dp))
        RealTimeCharts()
        Spacer(modifier = Modifier.height(16.dp))
        AlarmLog()*/
        Header(title = "智慧大屏 - 服务器运行状况")
//        SystemInfoPanel()
        Spacer(modifier = Modifier.height(16.dp))
        RealTimeCharts()
        Spacer(modifier = Modifier.height(16.dp))
        LineChart(cpu_usage.map { it.toFloat() })
        AlarmLog()
    }
}

//UI头
@Composable
fun Header(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF282828)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        var currentTime by remember { mutableStateOf("Loading...") }
        LaunchedEffect(Unit) {
            while (true) {
                currentTime = getCurrentTime()
                delay(1000)
            }
        }
        Text(
            text = "时间: $currentTime",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun SystemInfoPanel() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF383838))
            .border(1.dp, Color.Gray)
            .padding(16.dp)
    ) {
        ChartCard("CPU 使用率",  Color(0xFF42A5F5)) { getCpuUsage() }
        Spacer(modifier = Modifier.width(16.dp))
        ChartCard("内存使用率", Color(0xFF66BB6A)){ getMemoryUsage()}
        /*Spacer(modifier = Modifier.width(16.dp))
        SystemInfoCard("磁盘使用率", "72%", Color(0xFFEF5350))*/
    }
}
/*@Composable
fun SystemInfoPanel() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF383838))
            .border(1.dp, Color.Gray)
            .padding(16.dp)
    ) {
        SystemInfoCard("CPU 使用率", "45%", Color(0xFF42A5F5))
        Spacer(modifier = Modifier.width(16.dp))
        SystemInfoCard("内存使用率", "68%", Color(0xFF66BB6A))
        Spacer(modifier = Modifier.width(16.dp))
        SystemInfoCard("磁盘使用率", "72%", Color(0xFFEF5350))
    }
}*/

/*
@Composable
fun SystemInfoCard(title: String, value: String, color: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color.copy(alpha = 0.2f))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp
        )
        Text(
            text = value,
            color = color,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
*/

@Composable
fun RealTimeCharts() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
//        ChartCard("CPU 实时状态", Color(0xFF42A5F5)) { getCpuUsage() }
        CircularProgressIndicator(getCpuUsage(),"CPU 实时状态", Color(0xFF42A5F5))
        Spacer(modifier = Modifier.width(16.dp))
        CircularProgressIndicator(getMemoryUsage(),"内存实时状态", Color(0xFF66BB6A))
//        ChartCard("内存实时状态", Color(0xFF66BB6A)) { getMemoryUsage() }
    }
}

@Composable
fun ChartCard(title: String, lineColor: Color, valueProvider: () -> Float) {
    val values = remember { mutableStateListOf<Float>() }

    LaunchedEffect(Unit) {
        while (true) {
            values.add(valueProvider())
            if (values.size > 20) values.removeAt(0)
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
//            .weight(1f)
            .background(Color(0xFF383838))
            .padding(16.dp)
    ) {
        Text(text = title, color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFF282828))
        ) {
            val stepX = size.width / 20f
            val stepY = size.height / 100f
            values.forEachIndexed { index, value ->
                if (index < values.size - 1) {
                    drawLine(
                        color = lineColor,
                        start = Offset(index * stepX, size.height - value * stepY),
                        end = Offset((index + 1) * stepX, size.height - values[index + 1] * stepY),
                        strokeWidth = 3f
                    )
                }
            }
        }
    }
}

@Composable
fun AlarmLog() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color(0xFF383838))
            .padding(16.dp)
    ) {
        Text(text = "告警信息", color = Color.White, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        repeat(3) {
            Text(
                text = "告警 $it: 示例告警内容",
                color = Color(0xFFFFAB00),
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}


/*@Composable
fun RealtimeSystemStatus() {
    // 定义状态
    var cpuUsage by remember { mutableStateOf(0.0f) }
    var memoryUsage by remember { mutableStateOf(0.0f) }

    // 定时更新状态
    LaunchedEffect(Unit) {  // 启动一个协程，定时更新状态
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
}*/
