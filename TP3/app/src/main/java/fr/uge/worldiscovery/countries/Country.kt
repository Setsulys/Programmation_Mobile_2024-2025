package fr.uge.worldiscovery.countries

import androidx.compose.runtime.Composable

sealed class Country(
    val name: String,
    val population: Int,
    val area: Float,
    val gdp: Float,
    val unemployment: Float,
    val imports: Float,
    val exports: Float,
    val flag: @Composable () -> Unit
)