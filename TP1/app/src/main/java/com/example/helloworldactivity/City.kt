package com.example.helloworldactivity

import android.content.Context
import java.io.Serializable
import java.lang.Math.*
import java.util.*

class City(val name: String, val latitude: Float, val longitude: Float, val population: Int, val elevation: Float, val timeZone: TimeZone): Serializable {

    companion object {
        /** Load a city from a CSV text line */
        fun loadFromLine(line: String): City {
            val c = line.split("\t")
            return City("${c[0]} ${c[3]}", c[1].toFloat(), c[2].toFloat(), c[4].toInt(), c[5].toFloat(), TimeZone.getTimeZone(c[6]))
        }

        /** Load all the cities from a CSV text file */
        fun loadFromAsset(context: Context, id: Int): List<City> {
            return context.resources.openRawResource(id).reader().readLines().map { loadFromLine(it) }
        }

        const val R = 6372.8 // in kilometers

        /** Compute the distance between two geographical points */
        fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val λ1 = toRadians(lat1)
            val λ2 = toRadians(lat2)
            val Δλ = toRadians(lat2 - lat1)
            val Δφ = toRadians(lon2 - lon1)
            return 2 * R * asin(sqrt(pow(sin(Δλ / 2), 2.0) + pow(sin(Δφ / 2), 2.0) * cos(λ1) * cos(λ2)))
        }

        fun findNearest(cities: List<City>, latitude: Float, longitude: Float): City? {
            return cities.minByOrNull { haversine(latitude.toDouble(), longitude.toDouble(), it.latitude.toDouble(), it.longitude.toDouble()) }
        }
    }
}