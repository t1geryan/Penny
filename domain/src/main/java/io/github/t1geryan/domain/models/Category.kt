package io.github.t1geryan.domain.models

typealias CategoryId = Int

/**
 * @property name - human friendly name of Category
 * @property color - hex value of Category primary color
 */
data class Category(
    val id: CategoryId = 0,
    val name: String,
    val color: Int,
)
