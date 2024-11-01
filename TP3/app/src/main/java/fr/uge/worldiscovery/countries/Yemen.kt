package fr.uge.worldiscovery.countries

import androidx.compose.runtime.Composable

data object Yemen : Country(
    name = "Yemen",
    population = 32140443,
    area = 527968F,
    gdp = 7.3629998E9F,
    unemployment = 17.22F,
    imports = 4.07900006E9F,
    exports = 384500000F,
    flag = @Composable { YemenFlag() }
)