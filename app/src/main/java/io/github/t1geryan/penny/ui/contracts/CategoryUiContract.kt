package io.github.t1geryan.penny.ui.contracts

import androidx.compose.ui.graphics.Color
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.models.Alpha

val Category.contentColor
    get() = Color(color)

val Category.backgroundColor: Color
    get() = Color(color).copy(alpha = Alpha.TRANSLUCENT.value)

private const val RED = 0xFFDB1818L
private const val ORANGE = 0xFFFF6B35L
private const val PEACH = 0xFFF7C59FL
private const val BLUE = 0xFF1E3A8AL
private const val LIGHT_BLUE = 0xFF3B82F6L
private const val GREEN = 0xFF10B981L
private const val LIME = 0xFF84CC16L
private const val YELLOW = 0xFFF59E0BL
private const val PURPLE = 0xFF8B5CF6L
private const val PINK = 0xFFEC4899L
private const val GRAY = 0xFF6B7280L
private const val DARK_GRAY = 0xFF374151L

val predefinedColors = listOf(
    RED,
    ORANGE,
    PEACH,
    BLUE,
    LIGHT_BLUE,
    GREEN,
    LIME,
    YELLOW,
    PURPLE,
    PINK,
    GRAY,
    DARK_GRAY,
)

val predefinedEmojis = listOf(
    "\uD83C\uDF54", // 🍔
    "\uD83D\uDE97", // 🚗
    "\uD83C\uDFA5", // 🎥
    "\uD83C\uDFAE", // 🎮
    "\uD83D\uDCE6", // 📦
    "\uD83C\uDFE1", // 🏡
    "\uD83D\uDC8A", // 💊
    "\uD83D\uDCDA", // 📚
    "\uD83D\uDC5A", // 👚
    "\uD83D\uDCA1", // 💡
    "\uD83D\uDECD", // 🛍
    "\uD83C\uDFE5", // 🏥
)
