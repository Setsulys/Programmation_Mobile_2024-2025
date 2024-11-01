package fr.uge.worldiscovery

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import fr.uge.worldiscovery.ui.theme.WorldDiscoveryTheme
import fr.uge.worldiscovery.countries.*


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
                    //helloWorld(name = "Yassine le boss")
                    CountryList()
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
            HelloWorldMessage(name = name, counter = counter)
        }
        Column(modifier = Modifier
            .weight(2f)
            .fillMaxSize()){
            WorldMap({if(it){counter++}else {counter+=4} })
        }
        Column(modifier = Modifier
            .weight(4f)
            .fillMaxSize()){
            Flags()
        }

    }
}

@Composable
fun HelloWorldMessage(name:String,counter:Int) {
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
fun WorldMap(mapClick : (Boolean)-> Unit,modifier: Modifier= Modifier){
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
fun Flags(){
    Column(modifier= Modifier.background(color = Color.White)) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            UnitedArabEmiratesFlag()
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            SweedenFlag()
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            MonacoFlag()
        }
    }
}


@Composable
fun CountryList() {
    val ALL_COUNTRIES = Country::class.sealedSubclasses.map { it.objectInstance }
    var selectedValue by remember { mutableStateOf("None") }
    var dsc by remember { mutableStateOf(false) }
   Column{

       Column(modifier = Modifier.weight(0.5f/10f,fill = true).fillMaxSize().background(Color.Gray)) {
           Row{
               Box(modifier = Modifier.fillMaxSize().weight(0.5f/7f,fill = true))
               Box(modifier = Modifier.fillMaxSize().weight(2f/7f,fill = true)) {
                   DropDownMenu{
                           value -> selectedValue =value
                   }
               }
               Box(modifier = Modifier.fillMaxSize().weight(2f/7f,fill = true)){
                   IsDesc {
                           value ->  dsc=value
                   }
               }
               Box(modifier = Modifier.fillMaxSize().weight(2.5f/7f,fill = true))
           }
       }
       var selected = when(selectedValue) {
           "Population" -> if(dsc)ALL_COUNTRIES.sortedByDescending { it!!.population } else ALL_COUNTRIES.sortedBy{ it!!.population }
           "Area" -> if(dsc) ALL_COUNTRIES.sortedByDescending { it!!.area } else ALL_COUNTRIES.sortedBy{ it!!.area }
           "Gdp" -> if(dsc) ALL_COUNTRIES.sortedByDescending { it!!.gdp } else ALL_COUNTRIES.sortedBy{ it!!.gdp }
           "Unemployment" -> if(dsc) ALL_COUNTRIES.sortedByDescending { it!!.unemployment } else ALL_COUNTRIES.sortedBy{ it!!.unemployment }
           "Import" -> if(dsc) ALL_COUNTRIES.sortedByDescending { it!!.imports } else ALL_COUNTRIES.sortedBy{ it!!.imports }
           "Export" -> if(dsc) ALL_COUNTRIES.sortedByDescending { it!!.exports } else ALL_COUNTRIES.sortedBy{ it!!.exports }
           else -> if(dsc) ALL_COUNTRIES.sortedByDescending { it!!.population } else ALL_COUNTRIES.sortedBy{ it!!.population }
       }
       LazyColumn(modifier = Modifier.weight(9f/10f,fill = true).fillMaxSize()) {
           itemsIndexed(selected){ index, country ->
               if (country != null) {
                   var (maxFact,fact) = when(selectedValue){
                       "Population" -> ALL_COUNTRIES.maxOf { it!!.population.toFloat() } to country.population.toFloat()
                       "Area" -> ALL_COUNTRIES.maxOf { it!!.area } to country.area
                       "Gdp" -> ALL_COUNTRIES.maxOf { it!!.gdp} to country.gdp
                       "Unemployment" -> ALL_COUNTRIES.maxOf { it!!.unemployment } to country.unemployment
                       "Import" -> ALL_COUNTRIES.maxOf { it!!.imports} to country.imports
                       "Export" -> ALL_COUNTRIES.maxOf { it!!.exports } to country.exports
                       else ->  ALL_COUNTRIES.maxOf { it!!.population.toFloat() } to country.population.toFloat()
                   }
                   Column{
                       CountryFact(
                           rank = index + 1,
                           country = country,
                           factValue = fact,
                           maxFactValue = maxFact,
                           unit = when(selectedValue){
                               "Population" -> "Hab"
                               "Area" -> "Km2"
                               "Gdp" -> "$"
                               "Unemployment"->"%"
                               "Import" ->"$"
                               "Export" ->"$"
                               else -> "Hab"
                           }
                       )
                   }
               }
           }
       }
    }
}