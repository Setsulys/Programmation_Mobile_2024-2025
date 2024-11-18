package fr.uge.flagcoloring.gamelibrary

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.tooling.preview.Preview
import fr.uge.flagcoloring.data.Sketch
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.IntSize
import fr.uge.flagcoloring.library.loadBitmap
import fr.uge.flagcoloring.library.loadCountries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun Drawer(sketch: Sketch, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        sketch.paths.forEach { coloredPath ->
            for (i in 0 until coloredPath.size - 1) {
                val start = coloredPath[i]
                val end = coloredPath[i + 1]
                drawLine(
                    color = coloredPath.color,
                    start = start,
                    end = end,
                    strokeWidth = 40f
                )
            }
        }
    }
}

@Composable
fun PointerCapturer(modifier: Modifier = Modifier, onNewPointerPosition: (Offset, Boolean) -> Unit){
    var componentSize by remember { mutableStateOf(IntSize.Zero) }
    Box(
        modifier = modifier
            .onSizeChanged { componentSize = it }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    var isPressed = true
                    while (true) {
                        val event = awaitPointerEvent()
                        event.changes.forEach { pointerChange ->
                            val position = pointerChange.position
                            val boundedPosition = Offset(
                                x = position.x.coerceIn(0f, componentSize.width.toFloat()),
                                y = position.y.coerceIn(0f, componentSize.height.toFloat())
                            )
                            val newIsPressed = !pointerChange.pressed
                            onNewPointerPosition(boundedPosition, isPressed)
                            isPressed = newIsPressed
                        }
                    }
                }
            }
    ) {
    }
}




@Composable
fun ActiveDrawer(color: Color, modifier: Modifier = Modifier){
    var sketch by remember { mutableStateOf(Sketch.createEmpty()) }
    Box(modifier.fillMaxSize()){
        Drawer(sketch = sketch,modifier= Modifier.fillMaxSize())
        PointerCapturer(modifier = Modifier
        .fillMaxSize(),
        onNewPointerPosition = { position, isPressed ->
            if(isPressed){
                sketch+=color
            }
            sketch+=position
            println("Pointeur à la position: $position, Pressé: $isPressed")
        })
    }
}

@Composable
fun Palette(image: ImageBitmap, modifier: Modifier = Modifier, onSelectedColor: (Color) -> Unit){
    val size = remember { mutableStateOf(IntSize(0, 0)) }

    modifier.onSizeChanged { newSize ->
        size.value = newSize
    }
    Image(bitmap = image, contentDescription = null, modifier= Modifier.pointerInput(Unit){

    })
        // Affichage de l'image
/*        Image(
            bitmap = image,
            contentDescription = null,
            modifier = modifier.pointerInput(Unit) {
                detectTapGestures { offset ->
                    // Récupérer la couleur au pixel où le clic a eu lieu
                    val pixelX = (offset.x * size.value.width / modifier.size.width).toInt()
                    val pixelY = (offset.y * size.value.height / modifier.size.height).toInt()

                    // Récupérer la couleur du pixel à la position (pixelX, pixelY)
                    val color = image.get(pixelX, pixelY)
                    onSelectedColor(color)
                }
            },
            contentScale = ContentScale.Fit
        )*/
}



///Previews

/*@Preview
@Composable
fun ImagePallette(){
    Palette(image = loadBitmapAsImageBitmap("http://localhost:8081/flags/ad.png"), onSelectedColor = { color-> println(color)})
}*/

@Preview
@Composable
fun PreviewDrawer() {
    val sketch = Sketch.createEmpty()
        .plus(Color.Red)
        .plus(Offset(500f, 100f))
        .plus(Offset(150f, 150f))
        .plus(Offset(100f, 100f))
        .plus(Color.Green)
        .plus(Offset(800f, 400f))
        .plus(Offset(500f, 450f))
        .plus(Offset(100f,100f))
    Drawer(sketch = sketch, modifier =  Modifier.fillMaxSize())
}

@Preview
@Composable
fun PointerCapturingExample() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        PointerCapturer(
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxSize(),
            onNewPointerPosition = { position, isPressed ->
                println("Pointeur à la position: $position, Pressé: $isPressed")
            }
        )
    }
}

@Preview
@Composable
fun PreviewActiveDrawer(){
    ActiveDrawer(color = Color.Red)
}