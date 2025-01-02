package fr.uge.androidrevision

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.androidrevision.ui.theme.AndroidRevisionTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidRevisionTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
                //SlotMachineRoll(SYMBOLS,50.sp,0)
                //SlotMachineRolls(SYMBOLS,30.sp, centerIndices = (0..6).toList())
                SlotMachineWithHandlePreview()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidRevisionTheme {
        Greeting("Android")
    }
}
val SYMBOLS = arrayOf("🎲", "🏦", "🍒", "🍓", "💰", "🏇", "🥹")

@Composable
fun SlotMachineRoll(symbols: Array<String>, fontSize: TextUnit, centerIndex: Int){
    val centering = (centerIndex+symbols.size/2)%symbols.size+1
    val array = symbols.slice(centering..symbols.size-1)+symbols.slice(0..centering-1)
    Column(modifier = Modifier.fillMaxSize().padding(1.dp)){
        for(element in array.indices){
            if(element == symbols.size/2){
                Row(modifier = Modifier
                    .border(width = 5.dp,color=Color.Red).background(Color.Yellow)){
                    Text(text = array[element],
                        fontSize= fontSize,
                        textAlign = TextAlign.Center,modifier =Modifier.align(Alignment.CenterVertically).padding(10.dp))
                }
            }
            else{
                Row(modifier = Modifier){
                    Text(text = array[element],
                        fontSize= fontSize,
                        textAlign = TextAlign.Center,modifier =Modifier.align(Alignment.CenterVertically).padding(10.dp))
                }
            }
        }
    }
}

@Composable
fun SlotMachineRolls(symbols: Array<String>, fontSize: TextUnit, centerIndices: List<Int>){
    Box(Modifier.fillMaxSize()){
        Row(modifier= Modifier.fillMaxSize()){
            for(i in centerIndices.indices){
                Column(modifier= Modifier.fillMaxSize().weight(1f)){
                    SlotMachineRoll(symbols,fontSize,centerIndices[i])
                }
                if(i!= centerIndices.size-1){
                    Column(modifier = Modifier.fillMaxSize().weight(0.1f).background(Color.Blue)){
                    }
                }
            }
        }
    }
}

@Composable
fun SlotMachine(symbols: Array<String>, fontSize: TextUnit, rollNumber: Int, running: Boolean, onDraw: (List<String>) -> Unit){
    var randomValues by remember { mutableStateOf(List(rollNumber){Random.nextInt(0,rollNumber)})}
    if(running){
        Box(modifier = Modifier.fillMaxSize().background(Color.Green)){
            Text(text="Draw in progress ...",
                fontSize = fontSize,
                textAlign = TextAlign.Center,modifier =Modifier.align(Alignment.Center).padding(10.dp))
            randomValues = List(rollNumber){Random.nextInt(0,rollNumber)}
        }
    }
    else{
        LaunchedEffect(Unit){
            onDraw(randomValues.map { symbols[it % symbols.size] })
        }
        SlotMachineRolls(symbols,fontSize, centerIndices =randomValues)
    }
}


@Composable
fun VerticalGauge(fillRatio: Float, modifier: Modifier = Modifier){
    Column{
        Column(modifier.fillMaxSize().weight(1f).border(width= 10.dp,Color.Black, RectangleShape)){
            if(fillRatio<1f){
                Row(modifier=Modifier.fillMaxSize().weight(1-fillRatio)){
                }
            }
            if(fillRatio!=0f){
                Row(modifier.fillMaxSize().weight(fillRatio).background(Color.Blue)){
                }
            }

        }
    }
}



@Composable
fun Handle(onReleasedHandle: (Float) -> Unit, modifier: Modifier = Modifier){
    var fillRatio by remember { mutableStateOf(0f) }
    var isPressed = remember {mutableStateOf(false)}
    val fillSpeed = 0.01f
    Column{
        LaunchedEffect(isPressed.value){
            while(isPressed.value && fillRatio <1f){
                fillRatio += fillSpeed
                delay(10)
                if(fillRatio>1f) fillRatio =1f
            }
        }
        Column(modifier.fillMaxSize().weight(9f).pointerInput(Unit){
            detectTapGestures(
                onPress={
                    isPressed.value=true
                    try{
                        awaitRelease()
                    } finally {
                        isPressed.value=false
                        onReleasedHandle(fillRatio)
                        fillRatio=0f
                    }
                }
            )
        }
        ){
            VerticalGauge(fillRatio = fillRatio)
        }
        Column(modifier.fillMaxSize().weight(1f).background(Color.Gray)){
            Text(text= String.format("%.2f",fillRatio),textAlign = TextAlign.Center,modifier =Modifier.align(Alignment.CenterHorizontally).padding(10.dp))
        }
    }

}

@Composable
fun SlotMachineWithHandle(symbols: Array<String>, fontSize: TextUnit, rollNumber: Int, onDraw: (List<String>) -> Unit){
    var running by remember{mutableStateOf(false)}
    var results by remember{mutableStateOf(listOf<String>())}
    var delayT by remember { mutableStateOf(0L) }
    LaunchedEffect(running) {
        delay(delayT)
        running=false
    }
    Box{
        Row{
            Column(modifier=Modifier.fillMaxSize().weight(6f)){
                SlotMachine(symbols= symbols,fontSize=fontSize,rollNumber=rollNumber,running=running){lst-> results=lst}
                onDraw(results)
            }
            Column(modifier=Modifier.fillMaxSize().weight(1f)){
                Handle(onReleasedHandle = {fillRatio -> delayT=(fillRatio*5000).toLong()
                running=true
                })
            }
        }
    }
}


@Preview
@Composable
fun SlotMachineWithHandlePreview(){
    var results by remember{mutableStateOf(listOf<String>())}

    Column{
        Column(modifier=Modifier.fillMaxSize().weight(0.1f)){
        }
        Column(modifier=Modifier.fillMaxSize().weight(2f)){
            SlotMachineWithHandle(SYMBOLS,35.sp,6) {lst-> results=lst }
        }
        Column(modifier = Modifier.fillMaxSize().weight(0.2f)){}
        Column(modifier=Modifier.fillMaxSize().weight(1f)){
            Text(text="Results:$results",  fontSize = 27.sp,modifier= Modifier.align(Alignment.CenterHorizontally))
        }
    }
}


@Preview
@Composable
fun SlotMachinePreview(){
    var running by remember{mutableStateOf(false)}
    var results by remember{mutableStateOf(listOf<String>())}
    Column{
        Column(modifier = Modifier.fillMaxSize().weight(3f)){
            SlotMachine(symbols = SYMBOLS, fontSize = 40.sp, rollNumber = 6, running = running) { lst -> results=lst}
        }
        Column(modifier = Modifier.fillMaxSize().weight(1f)){
            Button(onClick={running=!running},modifier= Modifier.align(Alignment.CenterHorizontally)){
                Text("Start", fontSize = 40.sp)
            }
        }
        Column(modifier = Modifier.fillMaxSize().weight(1f)){
            Text("Results:$results", fontSize = 30.sp)
        }
    }
}

@Preview
@Composable
fun VerticalGaugePreview(){
    Handle(onReleasedHandle = {
        fillRatio ->
        println("$fillRatio")
    })
}