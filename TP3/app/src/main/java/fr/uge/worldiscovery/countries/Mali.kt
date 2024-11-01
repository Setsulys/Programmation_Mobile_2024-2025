package fr.uge.worldiscovery.countries

import androidx.compose.runtime.Composable

data object Mali : Country(
    name = "Mali",
    population = 21990607,
    area = 1240192F,
    gdp = 5.7235001E10F,
    unemployment = 3.01F,
    imports = 7.9420001E9F,
    exports = 5.8550001E9F,
    flag = @Composable {MaliFlag()}
){}