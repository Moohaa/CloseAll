package org.close_all.project.ui.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Exit: ImageVector
    get() {
        if (_Exit != null) {
            return _Exit!!
        }
        _Exit = ImageVector.Builder(
            name = "Exit",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(3f, 1f)
                curveTo(2.4477f, 1f, 2f, 1.4477f, 2f, 2f)
                verticalLineTo(13f)
                curveTo(2f, 13.5523f, 2.4477f, 14f, 3f, 14f)
                horizontalLineTo(10.5f)
                curveTo(10.7761f, 14f, 11f, 13.7761f, 11f, 13.5f)
                curveTo(11f, 13.2239f, 10.7761f, 13f, 10.5f, 13f)
                horizontalLineTo(3f)
                verticalLineTo(2f)
                lineTo(10.5f, 2f)
                curveTo(10.7761f, 2f, 11f, 1.7761f, 11f, 1.5f)
                curveTo(11f, 1.2239f, 10.7761f, 1f, 10.5f, 1f)
                horizontalLineTo(3f)
                close()
                moveTo(12.6036f, 4.89645f)
                curveTo(12.4083f, 4.7012f, 12.0917f, 4.7012f, 11.8964f, 4.8964f)
                curveTo(11.7012f, 5.0917f, 11.7012f, 5.4083f, 11.8964f, 5.6036f)
                lineTo(13.2929f, 7f)
                horizontalLineTo(6.5f)
                curveTo(6.2239f, 7f, 6f, 7.2239f, 6f, 7.5f)
                curveTo(6f, 7.7761f, 6.2239f, 8f, 6.5f, 8f)
                horizontalLineTo(13.2929f)
                lineTo(11.8964f, 9.39645f)
                curveTo(11.7012f, 9.5917f, 11.7012f, 9.9083f, 11.8964f, 10.1036f)
                curveTo(12.0917f, 10.2988f, 12.4083f, 10.2988f, 12.6036f, 10.1036f)
                lineTo(14.8536f, 7.85355f)
                curveTo(15.0488f, 7.6583f, 15.0488f, 7.3417f, 14.8536f, 7.1464f)
                lineTo(12.6036f, 4.89645f)
                close()
            }
        }.build()
        return _Exit!!
    }

private var _Exit: ImageVector? = null
