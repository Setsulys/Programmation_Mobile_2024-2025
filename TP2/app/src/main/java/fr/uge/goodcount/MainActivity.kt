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
    private val hiddedButtons : MutableSet<Int> = mutableSetOf()
    private var previousCancel : MutableList<Step> = mutableListOf()

    private val nbButtons = listOf(R.id.Plate1,R.id.Plate2,R.id.Plate3,R.id.Plate4,R.id.Plate5,R.id.Plate6)
    private val opButton = listOf(R.id.ButtonAdd,R.id.ButtonMinus,R.id.ButtonProduct,R.id.ButtonDivide)
    private val cancelId = R.id.Cancel
    private val soluceId = R.id.Solution
    private var stringRes : String = ""
    private var goalValue : Int = Random.nextInt(101,999)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val draw = findViewById<Button>(R.id.Draw)
        val cancel = findViewById<Button>(cancelId)
        val soluce = findViewById<Button>(soluceId)

        opButton.forEach { e-> findViewById<Button>(e).isEnabled=false }
        nbButtons.forEach { e-> findViewById<Button>(e).isEnabled=false}
        cancel.isEnabled=false
        soluce.isEnabled=false

        draw.setOnClickListener{
            val goal = findViewById<TextView>(R.id.goal)
            goalValue = Random.nextInt(101,999)
            goal.setText(getString(R.string.goal)+ goalValue)
            elementPicker = ElementPicker.buildFromResource(this, R.raw.frequencies) { s -> s.toInt()}.pickElements(nbButtons.size)
            IntStream.range(0,nbButtons.size).forEach { e-> findViewById<Button>(nbButtons[e]).text = elementPicker[e].toString() }
            nbButtons.forEach { e-> findViewById<Button>(e).isEnabled=true }
            cancel.isEnabled=true
            soluce.isEnabled=true
            opButton.forEach { e-> findViewById<Button>(e).isEnabled=false }
            nbButtons.forEach { e-> findViewById<Button>(e).isEnabled=true}
            hiddedButtons.clear()
            stringRes=""
            findViewById<TextView>(R.id.calculs).text = stringRes
        }


        clickOnOperation()
        operate()
        cancel()
        solvor()

    }

    fun reset(){
        calculateButton=null
        hideButton = null
        operande1=""
        operande2=""
        operator=""
    }

    private fun clickOnOperation(){
        nbButtons.forEach { button ->
            val clicked = findViewById<Button>(button)
            clicked.setOnClickListener {
                if(hiddedButtons.size==nbButtons.size-1 && previousCancel[previousCancel.size-1].value.toInt()!= goalValue){
                    findViewById<TextView>(R.id.calculs).setText(getString(R.string.you_lose))
                }
                else if (operande1.isEmpty()) {
                    operationON1(clicked,button)
                }
                else {
                    operationON2(clicked,button)
                }
            }
        }
    }

    private fun operationON1(clicked: Button,button: Int){
        operande1 = clicked.text.toString()
        previousCancel.add(Step(button,operande1))
        calculateButton = clicked
        nbButtons.forEach{ e-> findViewById<Button>(e).isEnabled =false }
        opButton.forEach { e-> findViewById<Button>(e).isEnabled=true }
        stringRes+= operande1
        findViewById<TextView>(R.id.calculs).setText(stringRes)
    }
    private fun operationON2( clicked : Button,button:Int){
        operande2 = clicked.text.toString()
        previousCancel.add(Step(button,operande2))
        hiddedButtons.add(clicked.id)
        nbButtons.forEach{ e->
            if(hiddedButtons.contains(e)) findViewById<Button>(e).isEnabled=false
            else findViewById<Button>(e).isEnabled=true
        }
        val oper = checkOperator(operande1,operande2)
        calculateButton?.text = oper.toString()
        val check = when(operator) {
            "+" -> operande1.toInt() + operande2.toInt()
            "-" -> operande1.toInt() - operande2.toInt()
            "*" -> operande1.toInt() * operande2.toInt()
            "/" -> operande1.toInt() / operande2.toInt()
            else -> throw IllegalArgumentException();
        }
        if(check<0){
            removeLastOccurence(operator)
            removeLastOccurence(operande1)
            stringRes+=operande2+"-"+operande1+"="+oper+"\n"
        }
        else{
            stringRes+= operande2+"="+oper+"\n"
        }
        reset()
        if(oper ==goalValue ){
            stringRes+= "\n"+ getString(R.string.finded)
        }
        findViewById<TextView>(R.id.calculs).setText(stringRes)
    }

    private fun operate(){
        opButton.forEach{button -> findViewById<Button>(button).setOnClickListener{
            when(button){
                R.id.ButtonAdd -> operator="+"
                R.id.ButtonMinus-> operator ="-"
                R.id.ButtonProduct-> operator="*"
                R.id.ButtonDivide-> operator ="/"
            }
            previousCancel.add(Step(button,operator))
            nbButtons.filter { e-> findViewById<Button>(e) != calculateButton  && !hiddedButtons.contains(e)}.forEach { e-> findViewById<Button>(e).isEnabled=true}
            opButton.forEach {e->findViewById<Button>(e).isEnabled=false}
            stringRes+=operator
            findViewById<TextView>(R.id.calculs).setText(stringRes)
        }}
    }

    private fun cancel(){
        println("hello")
        findViewById<Button>(cancelId).setOnClickListener{
            if(previousCancel.isNotEmpty() ) {
                val previous = previousCancel[previousCancel.size-1]
                if(operande1.isNotEmpty()&& operator.isEmpty() && operande2.isEmpty()){
                    operande1 = ""
                    removeLastOccurence(previous.value)
                    previousCancel.removeLast()
                    opButton.forEach { e -> findViewById<Button>(e).isEnabled = false }
                    nbButtons.forEach { e -> findViewById<Button>(e).isEnabled = true }
                    findViewById<TextView>(R.id.calculs).setText(stringRes)
                }
                else if (operande1.isNotEmpty() && operator.isNotEmpty()&& operande2.isEmpty()) {
                    operator = ""
                    removeLastOccurence(previous.value)
                    opButton.forEach { e -> findViewById<Button>(e).isEnabled = true }
                    nbButtons.forEach { e -> findViewById<Button>(e).isEnabled = false }
                    previousCancel.removeLast()
                    findViewById<TextView>(R.id.calculs).setText(stringRes)
                }
                else{
                    previousCancel.removeLast()
                    operande1 = previousCancel[previousCancel.size - 2].value
                    operator = previousCancel[previousCancel.size -1].value
                    removeLastOccurence("="+checkOperator(operande1,previous.value)+"\n")
                    operande2=""
                    removeLastOccurence(previous.value)
                    hiddedButtons.remove(previous.button)
                    findViewById<Button>(previous.button).isEnabled=true
                    findViewById<Button>(previous.button).setText(previous.value)
                    findViewById<Button>(previousCancel[previousCancel.size-2].button).setText(previousCancel[previousCancel.size-2].value)
                    findViewById<TextView>(R.id.calculs).setText(stringRes)
                }
            }
        }
    }
    
    private fun removeLastOccurence(subString : String){
        val lastIndex = stringRes.lastIndexOf(subString)
        println(subString)
        if(lastIndex!=-1){
            stringRes=stringRes.removeRange(lastIndex,lastIndex+subString.length)
        }
    }

    private fun checkOperator(oper1 : String, oper2:String):Int{
        return when(operator) {
            "+" -> oper1.toInt() + oper2.toInt()
            "-" -> if((oper1.toInt() - oper2.toInt())<0) oper2.toInt()-oper1.toInt() else oper1.toInt() - oper2.toInt()
            "*" -> oper1.toInt() * oper2.toInt()
            "/" -> oper1.toInt() / oper2.toInt()
            else -> throw IllegalArgumentException();
        }
    }

    private fun solvor(){
        findViewById<Button>(R.id.Solution).setOnClickListener{
            findViewById<Button>(cancelId).isEnabled=false
            findViewById<Button>(soluceId).isEnabled=false
            opButton.forEach { e-> findViewById<Button>(e).isEnabled=false }
            nbButtons.forEach { e-> findViewById<Button>(e).isEnabled=false}
            val result = solving()
            if (result != null) {
                findViewById<TextView>(R.id.calculs).setText(result)
                stringRes=""
            } else {
                stringRes=""
                findViewById<TextView>(R.id.calculs).setText(getString(R.string.nothing_found))
            }
        }
    }

    private fun solving():String?{
        return findCombination(elementPicker, goalValue, mutableSetOf())
    }


    fun findCombination(values: List<Int>, target: Int, seen: MutableSet<String>): String? {
        if (values.size == 1) {
            return if (values[0] == target) values[0].toString() else null
        }
        for (i in values.indices) {
            for (j in values.indices) {
                if (i != j) {
                    val newValues = values.filterIndexed { index, _ -> index != i && index != j }
                    val operations = mutableListOf<Pair<Int, String>>()

                    operations.add(Pair(values[i] + values[j], values[i].toString() + "+" +values[j].toString()))
                    operations.add(Pair(values[i] * values[j], values[i].toString() + "*" +values[j].toString()))

                    if (values[i] - values[j] >= 0) {
                        operations.add(Pair(values[i] - values[j], values[i].toString() + "-" +values[j].toString()))
                    }
                    if (values[j] - values[i] >= 0) {
                        operations.add(Pair(values[j] - values[i], values[i].toString() + "-" +values[j].toString()))
                    }
                    if (values[j] != 0 && values[i] % values[j] == 0) {
                        operations.add(Pair(values[i] / values[j], values[i].toString() + "/" +values[j].toString()))
                    }
                    if (values[i] != 0 && values[j] % values[i] == 0) {
                        operations.add(Pair(values[j] / values[i], values[i].toString() + "/" +values[j].toString()))
                    }

                    for ((result, operation) in operations) {
                        val operationKey = newValues.joinToString(",")+","+ result
                        if (!seen.contains(operationKey)) {
                            seen.add(operationKey)
                            val newResult = findCombination(newValues + result, target, seen)
                            if (newResult != null) {
                                return operation +"="+ result+"\n"+newResult
                            }
                        }
                    }
                }
            }
        }
        return null
    }
}