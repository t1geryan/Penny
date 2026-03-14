package io.github.t1geryan.domain.models

import androidx.compose.runtime.Immutable

typealias CategoryId = Int

/**
 * @property name - human friendly name of Category
 * @property color - hex value of Category primary color
 */
@Immutable
data class Category(
    val id: CategoryId = 0,
    val name: String,
    val emoji: String,
    val color: Long,
    val limit: Amount?,
)
