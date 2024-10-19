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

    private var operande1: String = ""
    private var operande2: String = ""
    private var operator : String=""

    private var elementPicker : List<Int> = emptyList()
    private var calculateButton : Button? =null
    private var hideButton : Button? = null
    private var hiddedButtons : MutableList<Button> = mutableListOf()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numberButton = listOf<Button>(findViewById(R.id.Plate1),findViewById(R.id.Plate2),findViewById(R.id.Plate3),findViewById(R.id.Plate4),findViewById(R.id.Plate5),findViewById(R.id.Plate6))
        val operandeButton = listOf<Button>(findViewById(R.id.ButtonAdd),findViewById(R.id.ButtonMinus),findViewById(R.id.ButtonProduct),findViewById(R.id.ButtonDivide))
        val draw = findViewById<Button>(R.id.Draw)
        val cancel = findViewById<Button>(R.id.Cancel)
        val soluce = findViewById<Button>(R.id.Solution)
        operandeButton.forEach { e-> e.isEnabled=false }
        numberButton.forEach { e-> e.isEnabled=false}
        cancel.isEnabled=false
        soluce.isEnabled=false

        draw.setOnClickListener{
            val goal = findViewById<TextView>(R.id.goal)
            goal.setText("Goal : "+ Random.nextInt(101,999))
            elementPicker = ElementPicker.buildFromResource(this, R.raw.frequencies) { s -> s.toInt()}.pickElements(numberButton.size)
            IntStream.range(0,numberButton.size).forEach { e->  numberButton[e].setText(elementPicker[e].toString())}
            numberButton.forEach { e-> e.isEnabled=true }
            cancel.isEnabled=true
            soluce.isEnabled=true
        }

       numberButton.forEach { button ->
            button.setOnClickListener {
                if (operande1.isEmpty()) {
                    operande1 = button.text.toString()
                    calculateButton = findViewById(button.id)
                    findViewById<TextView>(R.id.calculs).setText(operande1 + " (?) ? ")
                    numberButton.forEach{e->e.isEnabled =false}
                    operandeButton.forEach { e-> e.isEnabled=true }
                }
                else {
                    operande2 = button.text.toString()
                    hiddedButtons.add(findViewById<Button>(button.id))
                    numberButton.filter { f-> !hiddedButtons.contains(findViewById(button.id)) }.forEach{e->e.isEnabled =true}
                    val res = when(operator) {
                        "+" -> operande1.toInt() + operande2.toInt()
                        "-" -> operande1.toInt() - operande2.toInt()
                        "*" -> operande1.toInt() * operande2.toInt()
                        "/" -> operande1.toInt() / operande2.toInt()
                        else -> throw IllegalArgumentException();
                    }
                    calculateButton?.text = res.toString()

                    reset()
                    findViewById<TextView>(R.id.calculs).setText(operande1 + operator+operande2)
                }
            }
        }
        operandeButton.forEach{button -> button.setOnClickListener{
            when(button.id){
                R.id.ButtonAdd -> operator="+"
                R.id.ButtonMinus-> operator ="-"
                R.id.ButtonProduct-> operator="*"
                R.id.ButtonDivide-> operator ="/"
            }
            numberButton.filter { e-> e != calculateButton }.filter { e-> !hiddedButtons.contains(e) }.forEach { e-> e.isEnabled=true}
            operandeButton.forEach {e->e.isEnabled=false}
            findViewById<TextView>(R.id.calculs).setText(operande1 + operator+" ? ")
        }}
    }

    fun reset(){
        calculateButton=null
        hideButton = null
        operande1=""
        operande2=""
        operator=""
    }

/*
    fun cancel(){
    }
    fun solution(){
    }
*/

}