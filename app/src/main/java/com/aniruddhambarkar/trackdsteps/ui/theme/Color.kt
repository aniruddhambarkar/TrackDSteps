package com.aniruddhambarkar.trackdsteps.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val WHITE = Color(0xFFEFEFEF)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)
val BackgroundMain = Color(0xFFE0A5AA)


// Login Screen
val PinkLight = Color(0xFFFFD6DE)
val PinkMid = Color(0xFFFFB3C7)
val PinkDark = Color(0xFF8B3A4E)
//val TextPrimary = Color(0xFF7A2E3E)
//val TextSecondary = Color(0xFF9E5A6A)
val LIGHT_GREEN = Color(0xFFD9FEEE)
val LIGHT_PURPLE = Color(0XFFF3D1FF)
val MainLight = Color(0XFFFFB3C7)

// Cards
val CardMain =Color(0x448B3A4E)

/* ---------- Brand Colors ---------- */

val PurplePrimary = Color(0xFFC44BCB)
val PurpleDeep = Color(0xFF7A2EFF)
val PurpleDark = Color(0xFF5A1E7A)
val PurpleDarkAlpha = Color(0x555A1E7A)

val PinkAccent = Color(0xFFE0529C)
val OrangeAccent = Color(0xFFFF8A3D)

/* ---------- Backgrounds ---------- */

val BackgroundDark = Color(0xFF2A0E2E)
val SurfaceDark = Color(0xFF3A1643)
val SurfaceElevated = Color(0xFF4A1D55)
val SurfaceElevatedAlpha = Color(0xaa4A1D55)
val CardSurface  = Color(0XFFAE4765)
val CardSurfaceAlpha  = Color(0XaaAE4765)

/* ---------- Text ---------- */

val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFFD1BFD8)
val TextMuted = Color(0xFFA78BB3)

/* ---------- States ---------- */

val SuccessGreen = Color(0xFF4ADE80)
val ErrorRed = Color(0xFFF87171)

/* ---------- Outline / Divider ---------- */

val OutlineSoft = Color(0xFF8F6AA3)

/* ---------- New Colors ---------- */
val DeepIndigo = Color(0xff312e81) // (Indigo-950)
val Purple = Color(0xff9333ea)// (Purple-600)
val Pink= Color(0xffec4899) //(Pink-600)
val DarkPurple= Color(0xff581c87) //(Purple-950)

private val DarkColorScheme = darkColorScheme(
    primary = PurplePrimary,
    onPrimary = Color.White,

    primaryContainer = PurpleDeep,
    onPrimaryContainer = Color.White,

    secondary = OrangeAccent,
    onSecondary = Color.White,

    secondaryContainer = PurpleDark,
    onSecondaryContainer = Color.White,

    tertiary = PinkAccent,
    onTertiary = Color.White,

    background = BackgroundDark,
    onBackground = TextPrimary,

    surface = SurfaceDark,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceElevated,
    onSurfaceVariant = TextSecondary,

    outline = OutlineSoft,

    error = ErrorRed,
    onError = Color.White
)