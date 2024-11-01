package fr.uge.worldiscovery.countries

import androidx.compose.runtime.Composable

data object Germany : Country(
    "Germany",
    84_119_100,
    357_022f,
    5.23E11f,
    3.05f,
    1.927E11f,
    2.104E11f,
    @Composable { GermanFlag() }
){}