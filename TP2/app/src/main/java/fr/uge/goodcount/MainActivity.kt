package fr.uge.goodcount

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.stream.IntStream
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val buttonsArray = intArrayOf(R.id.Plate1,R.id.Plate2,R.id.Plate3,R.id.Plate4,R.id.Plate5,R.id.Plate6)

    private enum class State {
        FIRST,
        OP,
        SECOND
    }
    private enum class Type{
        ADD,
        SUB,
        MUL,
        DIV
    }

    private var state = State.FIRST
    private var operande1: Int = 0
    private var operande2: Int = 0
    private var operation = Type.ADD

    private var elementPicker : List<Int> = emptyList()


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        draw()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun draw(){
        val draw = findViewById<Button>(R.id.Draw)
        draw.setOnClickListener{
            val goal = findViewById<TextView>(R.id.goal)
            goal.setText("Goal : "+ Random.nextInt(101,999))
            elementPicker = ElementPicker.buildFromResource(this, R.raw.frequencies) { s -> s.toInt()}.pickElements(buttonsArray.size)
            IntStream.range(0,buttonsArray.size).forEach { e->  findViewById<Button>(buttonsArray[e]).setText(elementPicker[e].toString())}
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun clickOnOperande(){
        IntStream.range(0,buttonsArray.size).forEach{
            e-> findViewById<Button>(buttonsArray[e]).setOnClickListener{
                when(state){
                    State.FIRST ->  operande1 = elementPicker[e]
                    State.SECOND -> operande2 = elementPicker[e]
                    else ->{
                        print("wrong")
                    }
                }
        }
        }

    }

    fun cancel(){
    }
    fun solution(){
    }

    fun addition(){
    }
    fun substraction(){
    }
    fun multiplcation(){
    }
    fun division(){

    }

}