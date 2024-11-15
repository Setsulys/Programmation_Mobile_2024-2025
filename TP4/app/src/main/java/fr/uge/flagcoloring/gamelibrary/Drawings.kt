package fr.uge.flagcoloring.gamelibrary

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.tooling.preview.Preview
import fr.uge.flagcoloring.data.Sketch


/*
@Composable
fun Drawer(sketch: Sketch, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        sketch.paths.forEach { path ->
            for (i in 1 until path.size) {
                val start = path[i - 1]
                val end = path[i]
                drawLine(
                    color = path.color,
                    start = start,
                    end = end,
                    strokeWidth = 4f // Ajustez l'épaisseur selon vos besoins
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerPreview() {
    val testSketch = Sketch.createEmpty()
        .let {
            it + Color.Red +
                    Offset(10f, 10f) + Offset(50f, 50f) +
                    Color.Blue +
                    Offset(100f, 100f) + Offset(150f, 150f) + Offset(200f, 200f)
        }

    Drawer(
        sketch = testSketch,
        modifier = Modifier.fillMaxSize()
            .background(Color.LightGray)
    )
}*/
