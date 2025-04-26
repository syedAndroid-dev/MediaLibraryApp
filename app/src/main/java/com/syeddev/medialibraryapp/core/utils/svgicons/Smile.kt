package com.syeddev.medialibraryapp.core.utils.svgicons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.Unit

val ic_smile: ImageVector
    get() {
        if (_smile != null) {
            return _smile!!
        }
        _smile = Builder(name = "Smile", defaultWidth = 162.0.dp, defaultHeight = 148.0.dp,
                viewportWidth = 162.0f, viewportHeight = 148.0f).apply {
            path(fill = SolidColor(Color(0xFF0098FF)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(89.21f, 78.04f)
                moveToRelative(-69.53f, 0.0f)
                arcToRelative(69.53f, 69.53f, 0.0f, true, true, 139.07f, 0.0f)
                arcToRelative(69.53f, 69.53f, 0.0f, true, true, -139.07f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(105.32f, 72.3f)
                curveTo(107.03f, 72.3f, 108.48f, 71.7f, 109.69f, 70.49f)
                curveTo(110.89f, 69.28f, 111.5f, 67.83f, 111.5f, 66.12f)
                curveTo(111.5f, 64.41f, 110.89f, 62.96f, 109.69f, 61.75f)
                curveTo(108.48f, 60.55f, 107.03f, 59.94f, 105.32f, 59.94f)
                curveTo(103.61f, 59.94f, 102.16f, 60.54f, 100.96f, 61.74f)
                curveTo(99.77f, 62.94f, 99.17f, 64.4f, 99.17f, 66.11f)
                curveTo(99.17f, 67.82f, 99.77f, 69.28f, 100.96f, 70.49f)
                curveTo(102.16f, 71.7f, 103.61f, 72.3f, 105.32f, 72.3f)
                close()
                moveTo(73.1f, 72.3f)
                curveTo(74.81f, 72.3f, 76.26f, 71.7f, 77.46f, 70.5f)
                curveTo(78.65f, 69.3f, 79.25f, 67.84f, 79.25f, 66.13f)
                curveTo(79.25f, 64.42f, 78.65f, 62.96f, 77.46f, 61.75f)
                curveTo(76.26f, 60.55f, 74.81f, 59.94f, 73.1f, 59.94f)
                curveTo(71.39f, 59.94f, 69.94f, 60.55f, 68.73f, 61.75f)
                curveTo(67.53f, 62.96f, 66.92f, 64.41f, 66.92f, 66.12f)
                curveTo(66.92f, 67.83f, 67.53f, 69.28f, 68.73f, 70.49f)
                curveTo(69.94f, 71.7f, 71.39f, 72.3f, 73.1f, 72.3f)
                close()
                moveTo(89.21f, 102.45f)
                curveTo(94.11f, 102.45f, 98.63f, 101.13f, 102.75f, 98.49f)
                curveTo(105.6f, 96.67f, 107.91f, 94.39f, 109.68f, 91.66f)
                curveTo(110.82f, 89.89f, 109.4f, 87.74f, 107.29f, 87.74f)
                curveTo(106.23f, 87.74f, 105.27f, 88.32f, 104.65f, 89.18f)
                curveTo(103.09f, 91.37f, 101.1f, 93.1f, 98.69f, 94.38f)
                curveTo(95.77f, 95.92f, 92.63f, 96.7f, 89.26f, 96.7f)
                curveTo(85.9f, 96.7f, 82.74f, 95.94f, 79.8f, 94.43f)
                curveTo(77.38f, 93.2f, 75.41f, 91.47f, 73.9f, 89.25f)
                curveTo(73.28f, 88.35f, 72.3f, 87.74f, 71.2f, 87.74f)
                curveTo(69.07f, 87.74f, 67.65f, 89.92f, 68.82f, 91.71f)
                curveTo(70.61f, 94.42f, 72.91f, 96.68f, 75.73f, 98.49f)
                curveTo(79.81f, 101.13f, 84.31f, 102.45f, 89.21f, 102.45f)
                close()
                moveTo(89.21f, 123.28f)
                curveTo(82.95f, 123.28f, 77.08f, 122.1f, 71.57f, 119.73f)
                curveTo(66.07f, 117.37f, 61.28f, 114.14f, 57.19f, 110.06f)
                curveTo(53.11f, 105.98f, 49.89f, 101.18f, 47.52f, 95.68f)
                curveTo(45.15f, 90.18f, 43.97f, 84.3f, 43.97f, 78.05f)
                curveTo(43.97f, 71.79f, 45.15f, 65.91f, 47.52f, 60.41f)
                curveTo(49.89f, 54.9f, 53.11f, 50.11f, 57.19f, 46.03f)
                curveTo(61.27f, 41.94f, 66.06f, 38.71f, 71.56f, 36.34f)
                curveTo(77.07f, 33.97f, 82.95f, 32.78f, 89.21f, 32.78f)
                curveTo(95.46f, 32.78f, 101.34f, 33.96f, 106.85f, 36.34f)
                curveTo(112.35f, 38.71f, 117.15f, 41.94f, 121.23f, 46.02f)
                curveTo(125.31f, 50.1f, 128.54f, 54.89f, 130.91f, 60.4f)
                curveTo(133.29f, 65.9f, 134.48f, 71.79f, 134.48f, 78.05f)
                curveTo(134.48f, 84.3f, 133.29f, 90.19f, 130.91f, 95.69f)
                curveTo(128.54f, 101.19f, 125.31f, 105.98f, 121.23f, 110.06f)
                curveTo(117.14f, 114.14f, 112.35f, 117.37f, 106.85f, 119.73f)
                curveTo(101.35f, 122.1f, 95.47f, 123.28f, 89.21f, 123.28f)
                close()
                moveTo(89.21f, 115.69f)
                curveTo(99.71f, 115.69f, 108.62f, 112.04f, 115.91f, 104.75f)
                curveTo(123.21f, 97.45f, 126.86f, 88.55f, 126.86f, 78.04f)
                curveTo(126.86f, 67.54f, 123.21f, 58.64f, 115.91f, 51.34f)
                curveTo(108.62f, 44.04f, 99.71f, 40.4f, 89.21f, 40.4f)
                curveTo(78.7f, 40.4f, 69.8f, 44.04f, 62.51f, 51.34f)
                curveTo(55.21f, 58.64f, 51.56f, 67.54f, 51.56f, 78.04f)
                curveTo(51.56f, 88.55f, 55.21f, 97.45f, 62.51f, 104.75f)
                curveTo(69.8f, 112.04f, 78.7f, 115.69f, 89.21f, 115.69f)
                close()
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0098FF)),
                    strokeLineWidth = 2.13953f, strokeLineCap = Round, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(106.03f, 4.92f)
                curveTo(107.57f, 2.49f, 115.72f, -1.07f, 118.95f, 3.36f)
                curveTo(122.84f, 8.68f, 123.49f, 8.63f, 129.4f, 5.54f)
                curveTo(129.86f, 5.3f, 130.4f, 5.23f, 130.9f, 5.38f)
                curveTo(137.5f, 7.39f, 132.91f, 11.67f, 136.21f, 17.29f)
                curveTo(136.4f, 17.6f, 136.68f, 17.86f, 137.03f, 18.0f)
                curveTo(142.3f, 20.12f, 146.25f, 13.07f, 151.88f, 16.88f)
                curveTo(152.35f, 17.2f, 152.63f, 17.73f, 152.59f, 18.3f)
                curveTo(152.28f, 22.53f, 148.18f, 31.17f, 154.62f, 31.96f)
                curveTo(161.54f, 32.81f, 159.95f, 44.51f, 157.25f, 44.93f)
            }
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF0098FF)),
                    strokeLineWidth = 2.13953f, strokeLineCap = Round, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.25f, 80.17f)
                curveTo(9.58f, 81.23f, 4.59f, 88.6f, 8.35f, 92.58f)
                curveTo(12.88f, 97.37f, 12.71f, 98.01f, 8.6f, 103.25f)
                curveTo(8.27f, 103.66f, 8.1f, 104.18f, 8.16f, 104.7f)
                curveTo(8.94f, 111.55f, 13.97f, 107.82f, 18.9f, 112.09f)
                curveTo(19.18f, 112.34f, 19.38f, 112.66f, 19.45f, 113.03f)
                curveTo(20.57f, 118.6f, 12.93f, 121.2f, 15.64f, 127.42f)
                curveTo(15.87f, 127.94f, 16.34f, 128.32f, 16.9f, 128.38f)
                curveTo(21.13f, 128.85f, 30.37f, 126.4f, 29.97f, 132.87f)
                curveTo(29.54f, 139.83f, 41.34f, 140.4f, 42.24f, 137.82f)
            }
        }
        .build()
        return _smile!!
    }

private var _smile: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = ic_smile, contentDescription = "")
    }
}
