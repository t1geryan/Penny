package io.github.t1geryan.models

import androidx.compose.runtime.Immutable

@Immutable
data class Alpha(val value: Float) {

    companion object {
        val TRANSPARENT = Alpha(0.0f)
        val GHOSTLY = Alpha(0.1f)
        val TRANSLUCENT = Alpha(0.2f)
        val MUTED = Alpha(0.35f)
        val SEMI_TRANSPARENT = Alpha(0.5f)
        val DIMMED = Alpha(0.65f)
        val DENSE = Alpha(0.8f)
        val VIVID = Alpha(0.9f)
        val OPAQUE = Alpha(1.0f)
    }
}
