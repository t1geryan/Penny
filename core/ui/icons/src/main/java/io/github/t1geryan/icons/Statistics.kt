package io.github.t1geryan.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Statistics: ImageVector
    get() {
        if (_Statistics != null) {
            return _Statistics!!
        }
        _Statistics = ImageVector.Builder(
            name = "Statistics",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFFE3E3E3))) {
                moveToRelative(136f, 720f)
                lineToRelative(-56f, -56f)
                lineToRelative(296f, -298f)
                lineToRelative(160f, 160f)
                lineToRelative(208f, -206f)
                lineTo(640f, 320f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(240f)
                verticalLineToRelative(240f)
                horizontalLineToRelative(-80f)
                verticalLineToRelative(-104f)
                lineTo(536f, 640f)
                lineTo(376f, 480f)
                lineTo(136f, 720f)
                close()
            }
        }.build()

        return _Statistics!!
    }

@Suppress("ObjectPropertyName")
private var _Statistics: ImageVector? = null
