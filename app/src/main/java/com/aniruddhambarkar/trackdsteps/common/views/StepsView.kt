package com.aniruddhambarkar.trackdsteps.common.views

import SemiCircularProgress
import android.graphics.Paint.Style
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aniruddhambarkar.trackdsteps.R
import com.aniruddhambarkar.trackdsteps.ui.theme.WHITE
import rememberSemiCircularProgressController


@Composable
fun StepsView(
    modifier: Modifier = Modifier,
    steps: Long,
    goal: Int = 10_000,
    progress : Float,
    stepsTitleFontStyle: StepsFontStyle? = null,
){
    Box(
        modifier = modifier
            .fillMaxWidth().padding(bottom = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        val stepsFontStyle =
            stepsTitleFontStyle?.titleStyle
                ?: TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 26.sp,
                    color = Color.White
                )

        val totalStepsFontStyle =
            stepsTitleFontStyle?.subtitleStyle
                ?: TextStyle(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 26.sp,
                    color = Color.White
                )
            val controller = rememberSemiCircularProgressController()
            SemiCircularProgress(
                steps = steps,
                progress = progress,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp),
                controller = controller
            )
            /* ---------------- Center Text ---------------- */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = steps.toString(),
                    modifier = Modifier,
                    style = stepsFontStyle
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "of $goal steps",
                    style = totalStepsFontStyle
                )
            }

            Image(
                painter = painterResource(id = R.drawable.man),
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.align(Alignment.BottomCenter).size(40.dp)
                    .padding(bottom = 8.dp),
                        contentDescription = stringResource(id = R.string.steps),
            )

        }
}

data class StepsFontStyle(var title :String){
    var  titleStyle : TextStyle? = null
    var subtitleStyle :TextStyle? = null
}

@Preview(showBackground = false, widthDp = 260, heightDp = 220)
@Composable
fun ProgressScreen() {
    MaterialTheme {
        StepsView(
            steps = 8000,
            modifier = Modifier.padding(24.dp),
            progress = 0.5f,
        )
    }
}
