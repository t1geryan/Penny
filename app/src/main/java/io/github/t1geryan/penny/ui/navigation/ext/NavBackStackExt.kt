package io.github.t1geryan.penny.ui.navigation.ext

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun <T : NavKey> NavBackStack<T>.popToFirst() = popTo(0)

fun <T : NavKey> NavBackStack<T>.popTo(index: Int) {
    while (size > index + 1) {
        removeLastOrNull()
    }
}

fun <T : NavKey> NavBackStack<T>.bringToFront(item: T) {
    if (isEmpty()) return
    if (last() == item) return
    removeAll { it == item }
    add(item)
}