package com.aniruddhambarkar.trackdsteps.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val PrimaryGradient = Brush.horizontalGradient(
    listOf(
        PurpleDeep,
        PurplePrimary,
        OrangeAccent
    )
)

val ProgressGradient = Brush.linearGradient(
    listOf(
        PurpleDeep,
        CardSurface,
        OrangeAccent
    )
)

val StepsProgressLinearGradient = Brush.linearGradient(
    colors = listOf(
        Color(0XFF3F1D64),
        Color(0xFF4d1e65),
        Color(0xff92316a),
        CardSurface  // orange
    ),
    start = Offset(0f, Float.POSITIVE_INFINITY),
    end = Offset(Float.POSITIVE_INFINITY, 0f)
)

val ProgressCardBackground = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF3B1B5A),
        Color(0xFF5A2A6F),
        Color(0xFF7A2F6C)
    )
)


val ChartGlowGradient = Brush.horizontalGradient(
    colors = listOf(
        Color.Transparent,
        Color(0xFFFF8A3D).copy(alpha = 0.25f),
        Color.Transparent
    )
)

val SoftBackgroundGradient = Brush.horizontalGradient(
    colors = listOf(
        Color(0xFF2A0E2E),
        Color(0xFF3A1643),
        Color(0xFF4A1D55)
    )
)

val ScreenBackgroundGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFF2B0F2F), // top
        Color(0xFF3A1643),
        Color(0xFF4A1D55),
        Color(0xFF5A1F5E),
        Color(0xFF6A2A6F)  // bottom
    )
)
/*-----------Gradients --------------*/
val MainBackground = Brush.linearGradient(
    colors = listOf(
        DeepIndigo950, // from #1e1b4b (indigo-950)
        Purple950,     // via  #581c87 (purple-950)
        DeepIndigo900  // to   #312e81 (indigo-900)
    )
)

// ===== Header Gradient =====
// from-purple-600 via-pink-600 to-purple-600
val HeaderGradient = Brush.horizontalGradient(
    colors = listOf(
        Purple600, // from #9333ea (purple-600)
        Pink600,   // via  #db2777 (pink-600)
        Purple600  // to   #9333ea (purple-600)
    )
)

// ===== Primary Button Gradient =====
// from-purple-600 to-pink-600
val PrimaryButton = Brush.horizontalGradient(
    colors = listOf(
        Purple600, // from #9333ea (purple-600)
        Pink600    // to   #db2777 (pink-600)
    )
)

val stepsCardGradient = Brush.linearGradient(
    colors = listOf(
        Color(0x806B21A8),
        Color(0x809D174D)
//        Purple80
    ),
    start = Offset.Zero,
    end = Offset.Infinite
)
//
//val stepsCardGradient = Brush.linearGradient(
//    colors = listOf(
//        Color(0x806B21A8),
//        Color(0x809D174D)
//    ),
//    start = Offset.Zero,
//    end = Offset.Infinite
//)

val caloriesCardGradient = Brush.horizontalGradient(
    colors = listOf(
        Orange600,
        Red600
    )
)