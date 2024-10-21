package fr.uge.worldiscovery

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import fr.uge.worldiscovery.countries.Country
import fr.uge.worldiscovery.ui.theme.WorldDiscoveryTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorldDiscoveryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    helloWorld(name = "Yassine le boss")

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHelloWorld() {
    helloWorld("Android")
}


@Preview(showBackground = true)
@Composable
fun lazy(){
    Demo_DropDownMenu()
    LazyColumn {
        // Add a single item
        item {
            Text(text = "First item")
        }

        // Add 5 items
        items(5) { index ->
            Text(text = "Item: $index")
        }

        // Add another single item
        item {
            Text(text = "Last item")
        }
    }
}

@Composable
fun Demo_DropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Load") },
                onClick = { Toast.makeText(context, "Load", Toast.LENGTH_SHORT).show() }
            )
            DropdownMenuItem(
                text = { Text("Save") },
                onClick = { Toast.makeText(context, "Save", Toast.LENGTH_SHORT).show() }
            )
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun helloWorld(name: String) {
    var counter by remember { mutableIntStateOf(0) }
    //var counter = remember { mutableIntStateOf(0) }
    //var counter = mutableIntStateOf(0)
    //var counter = remember { 0 }
    //var counter = 0
    Log.d("Test","debug $counter")
    Column(modifier= Modifier.background(color = Color.White)){
        Column(modifier = Modifier
            .weight(1f)
            .fillMaxSize()){
            helloWorldMessage(name = name, counter = counter)
        }
        Column(modifier = Modifier
            .weight(2f)
            .fillMaxSize()){
            worldMap({if(it){counter++}else {counter+=4} })
        }
        Column(modifier = Modifier
            .weight(4f)
            .fillMaxSize()){
            flags()
        }

    }
}

@Composable
fun helloWorldMessage(name:String,counter:Int) {
    Text(
        "Hello world $name!\n counter: $counter", color = Color.White,
        modifier = Modifier
            .background(color = Color.Red)
            .fillMaxWidth()
            .padding(10.dp),
        fontSize = 30.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun worldMap(mapClick : (Boolean)-> Unit,modifier: Modifier= Modifier){
    Box(modifier = modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.equirectangular_world_map),
            contentDescription  = "Worldmap",
            modifier = modifier.pointerInput(Unit){
                detectTapGestures(
                    onTap = {mapClick(true)},
                    onDoubleTap = {mapClick(false)}
                )
            }
        )
    }
}

@Composable
fun flags(){
    Column(modifier= Modifier.background(color = Color.White)) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            unitedArabEmiratesFlag()
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            sweedenFlag()
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            monacoFlag()
        }
    }
}


@Composable
fun unitedArabEmiratesFlag(){
    Row (modifier = Modifier
        .fillMaxSize()
        .border(width = Dp.Hairline, color = Color.Black)) {
        Box(
            Modifier
                .fillMaxHeight()
                .background(color = Color(0xFFEF3340))
                .weight(1f / 4f, fill = true)) {  }
        Column(
            Modifier
                .fillMaxHeight()
                .weight(2f / 3f, fill = true)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF009739))
                    .weight(1f / 2f, fill = true)) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .weight(1f / 2f, fill = true)) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .weight(1f / 2f, fill = true)) {}
        }
    }
}


@Composable
fun monacoFlag(){
    Column(modifier = Modifier
        .fillMaxSize()
        .border(width = Dp.Hairline, color = Color.Black)) {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = Color(0xFFCE1126))
                .weight(1f / 2f, fill = true)) {}
        Box(
            Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .weight(1f / 2f, fill = true)) {}
    }
}


@Composable
fun sweedenFlag(){
    Row (modifier = Modifier
        .fillMaxSize()
        .border(width = Dp.Hairline, color = Color.Black)) {
        Column(
            Modifier
                .fillMaxHeight()
                .weight(5f / 16f, fill = true)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF006AA7))
                    .weight(4f / 10f, fill = true)) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFFECC02))
                    .weight(2f / 10f, fill = true)) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF006AA7))
                    .weight(4f / 10f, fill = true)) {}
        }
        Box(
            Modifier
                .fillMaxHeight()
                .background(color = Color(0xFFFECC02))
                .weight(2f / 16f, fill = true)) {  }
        Column(
            Modifier
                .fillMaxHeight()
                .weight(9f / 16f, fill = true)) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF006AA7))
                    .weight(4f / 10f, fill = true)) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFFECC02))
                    .weight(2f / 10f, fill = true)) {}
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFF006AA7))
                    .weight(4f / 10f, fill = true)) {}
        }
    }
}