package io.github.t1geryan.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val Purple80 = Color(0xFFD0BCFF)
internal val PurpleGrey80 = Color(0xFFCCC2DC)
internal val Pink80 = Color(0xFFEFB8C8)
internal val LightGrey = Color(0xFFDFDFDF)

internal val Purple40 = Color(0xFF6650a4)
internal val PurpleGrey40 = Color(0xFF625b71)
internal val Pink40 = Color(0xFF7D5260)
internal val DarkGrey = Color(0xFF3B3B3B)

internal val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    surface = DarkGrey,
)

internal val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    surface = LightGrey,
)
