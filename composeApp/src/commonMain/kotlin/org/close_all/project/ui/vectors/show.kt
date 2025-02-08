package org.close_all.project.ui.vectors

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Eye: ImageVector
    get() {
        if (_Eye != null) {
            return _Eye!!
        }
        _Eye = ImageVector.Builder(
            name = "Eye",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
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
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(16f, 8f)
                reflectiveCurveToRelative(-3f, -5.5f, -8f, -5.5f)
                reflectiveCurveTo(0f, 8f, 0f, 8f)
                reflectiveCurveToRelative(3f, 5.5f, 8f, 5.5f)
                reflectiveCurveTo(16f, 8f, 16f, 8f)
                moveTo(1.173f, 8f)
                arcToRelative(
                    13f,
                    13f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    1.66f,
                    -2.043f
                )
                curveTo(4.12f, 4.668f, 5.88f, 3.5f, 8f, 3.5f)
                reflectiveCurveToRelative(3.879f, 1.168f, 5.168f, 2.457f)
                arcTo(13f, 13f, 0f, isMoreThanHalf = false, isPositiveArc = true, 14.828f, 8f)
                quadToRelative(-0.086f, 0.13f, -0.195f, 0.288f)
                curveToRelative(-0.335f, 0.48f, -0.83f, 1.12f, -1.465f, 1.755f)
                curveTo(11.879f, 11.332f, 10.119f, 12.5f, 8f, 12.5f)
                reflectiveCurveToRelative(-3.879f, -1.168f, -5.168f, -2.457f)
                arcTo(13f, 13f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.172f, 8f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(8f, 5.5f)
                arcToRelative(2.5f, 2.5f, 0f, isMoreThanHalf = true, isPositiveArc = false, 0f, 5f)
                arcToRelative(
                    2.5f,
                    2.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0f,
                    -5f
                )
                moveTo(4.5f, 8f)
                arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = true, isPositiveArc = true, 7f, 0f)
                arcToRelative(3.5f, 3.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -7f, 0f)
            }
        }.build()
        return _Eye!!
    }

private var _Eye: ImageVector? = null
