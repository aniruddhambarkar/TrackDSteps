package com.aniruddhambarkar.trackdsteps.ui.theme

import androidx.compose.ui.graphics.Color

// ===== Primary Colors =====
val DeepIndigo950 = Color(0xFF1E1B4B) // indigo-950
val DeepIndigo900 = Color(0xFF312E81) // indigo-900
val Purple950     = Color(0xFF581C87) // purple-950
val Purple900     = Color(0xFF701A75) // purple-900
val Purple600     = Color(0xFF9333EA) // purple-600
val Purple500     = Color(0xFFA855F7) // purple-500
val Purple400     = Color(0xFFC084FC) // purple-400
val Pink600       = Color(0xFFDB2777) // pink-600
val Pink500       = Color(0xFFEC4899) // pink-500
val Pink400       = Color(0xFFF472B6) // pink-400

// ===== Steps (Blue-Cyan) =====
val Blue600 = Color(0xFF2563EB) // blue-600
val Blue500 = Color(0xFF3B82F6) // blue-500
val Cyan600 = Color(0xFF0891B2) // cyan-600
val Cyan500 = Color(0xFF06B6D4) // cyan-500

// ===== Calories (Red-Orange) =====
val Red600    = Color(0xFFDC2626) // red-600
val Red500    = Color(0xFFEF4444) // red-500
val Orange600 = Color(0xFFEA580C) // orange-600
val Orange500 = Color(0xFFF97316) // orange-500

// ===== Distance (Green-Emerald) =====
val Green600   = Color(0xFF16A34A) // green-600
val Green500   = Color(0xFF22C55E) // green-500
val Emerald600 = Color(0xFF059669) // emerald-600
val Emerald500 = Color(0xFF10B981) // emerald-500

// ===== Time / Active Minutes =====
// (uses existing Purple600 and Pink600)

// ===== Achievements & Badges =====
val Yellow600 = Color(0xFFCA8A04) // yellow-600
val Yellow500 = Color(0xFFEAB308) // yellow-500
val Yellow400 = Color(0xFFFACC15) // yellow-400
val Amber600  = Color(0xFFD97706) // amber-600
val Amber500  = Color(0xFFF59E0B) // amber-500

// ===== Additional Metric Colors =====
val Indigo600 = Color(0xFF4F46E5) // indigo-600
val Indigo500 = Color(0xFF6366F1) // indigo-500
val Rose600   = Color(0xFFE11D48) // rose-600
val Rose500   = Color(0xFFF43F5E) // rose-500

object UiColors {

    // ===== Slate (Dark UI Elements) =====
    val Slate900 = Color(0xFF0F172A) // slate-900
    val Slate800 = Color(0xFF1E293B) // slate-800
    val Slate700 = Color(0xFF334155) // slate-700
    val Slate600 = Color(0xFF475569) // slate-600
    val Slate500 = Color(0xFF64748B) // slate-500
    val Slate400 = Color(0xFF94A3B8) // slate-400
    val Slate300 = Color(0xFFCBD5E1) // slate-300

    // ===== Text Colors =====
    val TextPrimary   = Color(0xFFFFFFFF) // white
    val TextSecondary = Slate300          // slate-300
    val TextMuted400  = Slate400          // slate-400
    val TextMuted500  = Slate500          // slate-500

    // Purple text variants
    val Purple100 = Color(0xFFF3E8FF) // purple-100
    val Purple200 = Color(0xFFE9D5FF) // purple-200
    val Purple300 = Color(0xFFD8B4FE) // purple-300
}

object OverlayColors {

    // Card Background: bg-slate-900/50
    val CardBackground = UiColors.Slate900.copy(alpha = 0.5f)

    // Card Border: border-purple-500/30
    val CardBorder = Purple500.copy(alpha = 0.3f)

    // Hover: bg-white/10
    val HoverWhite10 = Color.White.copy(alpha = 0.10f)

    // White/20 for icon containers
    val White20 = Color.White.copy(alpha = 0.20f)
}