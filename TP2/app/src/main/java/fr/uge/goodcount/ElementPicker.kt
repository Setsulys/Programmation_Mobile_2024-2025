package fr.uge.goodcount

import android.content.Context
import java.io.BufferedReader


fun <T> Map<T, Int>.bagToList(): List<T>{
    return flatMap { (key,count)-> List(count) {key} }
}
class ElementPicker<T>(private val map :Map<T,Int>) {

    fun pickElements(n: Int): List<T>{
        return map.bagToList().shuffled().subList(0,n)
    }

    companion object{
        fun <T> buildFromResource(context: Context, resourceId: Int, elementParser: (String) -> T): ElementPicker<T>{
            val map = context.resources.openRawResource(resourceId).bufferedReader().useLines { lineSeq -> lineSeq.map{
                val element  = it.split(Regex("\\s+"));
                elementParser(element[0])to element[1].toInt()
            }.toMap()}
            return ElementPicker(map)
        }
    }
}


