package fr.uge.flagcoloring.library

import android.graphics.*
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import java.net.URL
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection

@Serializable
data class Country(
    val code:String,
    val name:String,
    val latitude:Float,
    val longitude:Float
)

suspend fun loadCountries(url: String): List<Country>{
    return withContext(Dispatchers.IO){
        Json.decodeFromString<List<Country>>(URL(url).readText())
    }
}


fun loadBitmap(url: String) : Bitmap {
    val connection = URL(url).openConnection() as HttpURLConnection
    connection.doInput = true
    connection.connect()
    val inputStream = connection.inputStream
    return BitmapFactory.decodeStream(inputStream)

}
