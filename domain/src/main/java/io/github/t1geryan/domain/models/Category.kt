package io.github.t1geryan.domain.models

/**
 * @property name - human friendly name of Category
 * @property color - hex value of Category primary color
 */
data class Category(
    val name: String,
    val color: Int,
)
