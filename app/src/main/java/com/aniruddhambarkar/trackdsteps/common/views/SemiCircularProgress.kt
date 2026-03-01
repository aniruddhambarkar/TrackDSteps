import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.State
import com.aniruddhambarkar.trackdsteps.common.views.StepsView

@Composable
fun SemiCircularProgress(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    controller: SemiCircularProgressController,
    strokeWidth: Dp = 18.dp,
    steps: Long,
    goal: Int = 10_000,
    gradientColors: List<Color> =  listOf(
//        Color(0xff6C63FF),
//                Color(0xffF06292)

        Color(0xFF60a5fa),
        Color(0xFFc084fc),
        Color(0xFFf472b6),
//        Color(0xFF4C1D95)
    ),
//    backgroundColor: Color = Color(0xFF301547),
    backgroundColor: Color = Color(0x22ffffff),
    animationDuration: Int = 1200
) {
    val progressValue = controller.progressState.value
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(animationDuration, easing = FastOutSlowInEasing),
        label = "progress"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.5f) // ⬅️ NO padding here
    ) {
        val strokePx = strokeWidth.toPx()
        val diameter = size.height
        if (diameter <= 0f) return@Canvas // safety

        val radius = diameter / 2f

        val topLeft = Offset(
            (size.width - diameter) / 2f,
            strokePx / 2f
        )

        val arcSize = androidx.compose.ui.geometry.Size(diameter, diameter)

        val stroke = Stroke(
            width = strokePx,
            cap = StrokeCap.Round
        )

        // Background arc
        drawArc(
            color = backgroundColor,
            startAngle = 120f,
            sweepAngle = 300f,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = stroke
        )

        // Progress arc
        drawArc(
            brush = Brush.sweepGradient(
                colors = gradientColors,
                center = topLeft + Offset(radius, radius)
            ),
            startAngle = 120f,
            sweepAngle = 300f * animatedProgress,
            useCenter = false,
            topLeft = topLeft,
            size = arcSize,
            style = stroke
        )
    }
}

@Composable
fun rememberSemiCircularProgressController(
    initialProgress: Float = 0f
): SemiCircularProgressController {
    return remember {
        SemiCircularProgressController().apply {
            setProgress(initialProgress)
        }
    }
}

@Stable
class SemiCircularProgressController {
    private val _progress = mutableFloatStateOf(0f)
    val progressState: State<Float> get() = _progress

    fun setProgress(value: Float) {
        _progress.floatValue = value.coerceIn(0f, 1f)
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 220)
@Composable
fun ProgressScreen() {
//    val controller = rememberSemiCircularProgressController()
//
//    SemiCircularProgress(
//        progress = 0.9f,
//        controller = controller,
//        steps = 8000,
//        modifier = Modifier.padding(24.dp)
//    )
    StepsView(
        modifier = Modifier.padding(12.dp),
        steps = 8000,
        progress = 0.5f
    )
}