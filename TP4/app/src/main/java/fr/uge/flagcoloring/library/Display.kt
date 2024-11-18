package fr.uge.flagcoloring.library

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.*


val COUNTRY_SERVER ="http://localhost:8081/";

@Composable
fun CountryGallery(countries: List<Country>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(countries) { country ->
            CountryCard(country)
        }
    }
}

@Composable
fun CountryCard(country: Country) {
    Card(
        modifier = Modifier.fillMaxWidth().aspectRatio(1f),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().weight(1f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ){
                flagDisplayer(country)
                Text(
                    text = country.name,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(200.dp),
                    style = MaterialTheme.typography.bodyLarge

                )
            }
        }
    }
}

@Composable
fun CountryGalleryRoot(){
    var countries by remember { mutableStateOf(listOf<Country>())  }
    LaunchedEffect(true) {
        countries = loadCountries(COUNTRY_SERVER + "countries.json")
    }
    CountryGallery(countries)
}




@Composable
fun flagDisplayer(country: Country, modifier: Modifier = Modifier){
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(country.code) {
        bitmap = withContext(Dispatchers.IO) {
            Log.i("TAg","value is"+country.code)
            loadBitmap(COUNTRY_SERVER +"flags/${country.code.lowercase()}.png")
        }
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap!!.asImageBitmap(),
            contentDescription = "${country.name} flag",
            modifier = modifier.size(128.dp)
        )
    } else {
        Box(
            modifier = modifier.size(128.dp).background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Text("Loading...", fontSize = 12.sp, color = Color.DarkGray)
        }
    }
}