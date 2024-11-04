package fr.uge.flagcoloring.library

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.net.URL
import kotlinx.serialization.json.Json

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Serializable
data class Country(
    val code:String,
    val name:String,
    val latitude:Float,
    val longitude:Float
)

@Composable
fun loadCountries(url: String): List<Country>{
    var countries by remember { mutableStateOf(listOf<Country>()) }
    LaunchedEffect(true ){
        countries= withContext(Dispatchers.IO){
            Json.decodeFromString<List<Country>>(URL(url).readText())
        }
    }
    return countries
}

@Composable
fun loadBitmap(url: String) {
    var bitmap by remember { mutableStateOf<ImageBitmap?>(null)}
    bitmap?.let{
        Image(it,"")
    }?:run{
     Box(modifier=Modifier.width(1.dp)){

     }
    }
}