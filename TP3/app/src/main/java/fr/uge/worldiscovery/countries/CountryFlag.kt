package fr.uge.worldiscovery.countries

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun UnitedArabEmiratesFlag() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .border(width = Dp.Hairline, color = Color.Black)
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .background(color = Color(0xFFEF3340))
                .weight(1f / 4f, fill = true)
        ) { }
        Column(
            Modifier
                .fillMaxHeight()
                .weight(2f / 3f, fill = true)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF009739))
                    .weight(1f / 2f, fill = true)
            ) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .weight(1f / 2f, fill = true)
            ) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .weight(1f / 2f, fill = true)
            ) {}
        }
    }
}


@Composable
fun MonacoFlag() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(width = Dp.Hairline, color = Color.Black)
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = Color(0xFFCE1126))
                .weight(1f / 2f, fill = true)
        ) {}
        Box(
            Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .weight(1f / 2f, fill = true)
        ) {}
    }
}


@Composable
fun SweedenFlag() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .border(width = Dp.Hairline, color = Color.Black)
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .weight(5f / 16f, fill = true)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF006AA7))
                    .weight(4f / 10f, fill = true)
            ) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFFECC02))
                    .weight(2f / 10f, fill = true)
            ) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF006AA7))
                    .weight(4f / 10f, fill = true)
            ) {}
        }
        Box(
            Modifier
                .fillMaxHeight()
                .background(color = Color(0xFFFECC02))
                .weight(2f / 16f, fill = true)
        ) { }
        Column(
            Modifier
                .fillMaxHeight()
                .weight(9f / 16f, fill = true)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF006AA7))
                    .weight(4f / 10f, fill = true)
            ) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFFECC02))
                    .weight(2f / 10f, fill = true)
            ) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF006AA7))
                    .weight(4f / 10f, fill = true)
            ) {}
        }
    }
}

@Composable
fun TchadFlag() {
    Row(modifier = Modifier.aspectRatio(3f / 2f).border(width = Dp.Hairline, color = Color.Black)) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFF002664))
        )
        Box(
            modifier =
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFFFCD116))
        )
        Box(
            modifier =
            Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color(0xFFCE1126))
        )
    }
}

@Composable
fun EstoniaFlag() {
    Column(modifier = Modifier.aspectRatio(11f / 7f).border(width = Dp.Hairline, color = Color.Black)) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF0072CE))
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF000000))
        )
        Box(
            modifier =
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFFFFFFFF))

        )
    }
}

@Composable
fun JapanFlag() {
    BoxWithConstraints(
        modifier = Modifier
            .background(Color.White)
            .width(270.dp)
            .height(140.dp).border(width = Dp.Hairline, color = Color.Black)
    ) {
        // Calculer une taille en fonction de la largeur ou hauteur maximale disponible
        val circleSize = maxWidth * 0.3f  // Par exemple, 30% de la largeur du parent

        Box(
            modifier = Modifier
                .background(Color.Red, shape = CircleShape)
                .size(circleSize)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun Austria() {
    Column(modifier = Modifier.fillMaxWidth().border(width = Dp.Hairline, color = Color.Black)) {
        Box(
            modifier = Modifier
                .weight(1f / 3f, fill = true)
                .fillMaxWidth()
                .background(Color(0xFFEF3340))
        )
        Box(
            modifier =
            Modifier
                .weight(1f / 3f, fill = true)
                .fillMaxWidth()
                .background(Color.White)
        )
        Box(
            modifier =
            Modifier
                .weight(1f / 3f, fill = true)
                .fillMaxWidth()
                .background(Color(0xFFEF3340))
        )
    }
}


@Composable
fun GermanFlag() {
    Column(Modifier.fillMaxSize().border(width = Dp.Hairline, color = Color.Black)) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Color.Black)
                .weight(1f / 3f, fill = true)
        )
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFDD0000))
                .weight(1f / 3f, fill = true)
        )
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFFFCC00))
                .weight(1f / 3f, fill = true)
        )
    }
}


@Composable
fun DanemarkFlag() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .border(width = Dp.Hairline, color = Color.Black)
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .weight(12f / 37f, fill = true)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFC8102E))
                    .weight(12f / 28f, fill = true)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .weight(4f / 28f, fill = true)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFC8102E))
                    .weight(12f / 28f, fill = true)
            )
        }
        Box(
            Modifier
                .fillMaxHeight()
                .background(color = Color.White)
                .weight(4f / 37f, fill = true)
        )
        Column(
            Modifier
                .fillMaxHeight()
                .weight(21f / 37f, fill = true)
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFC8102E))
                    .weight(12f / 28f, fill = true)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .weight(4f / 28f, fill = true)
            )
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFC8102E))
                    .weight(12f / 28f, fill = true)
            ) {}
        }
    }
}


@Composable
fun MaliFlag() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp).border(width = Dp.Hairline, color = Color.Black)
    ) {
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(Color(0xFF009E49)))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(Color(0xFFFFD100)))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .background(Color(0xFFCE1126)))
    }
}

@Composable
fun YemenFlag() {
    Column(modifier = Modifier.fillMaxWidth().border(width = Dp.Hairline, color = Color.Black)) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFFCE1126))
        )
        Box (modifier =
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.White))
        Box (modifier =
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFF000000)))
    }
}