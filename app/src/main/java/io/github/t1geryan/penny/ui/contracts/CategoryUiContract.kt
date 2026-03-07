package io.github.t1geryan.penny.ui.contracts

import androidx.compose.ui.graphics.Color
import io.github.t1geryan.domain.models.Category

val Category.contentColor
    get() = Color(color)

val Category.backgroundColor: Color
    get() = Color(color).copy(alpha = 0.3f)
