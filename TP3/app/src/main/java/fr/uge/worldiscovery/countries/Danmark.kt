package fr.uge.worldiscovery.countries

import androidx.compose.runtime.Composable

data object Danemark : Country(
    name = "Danemark",
    population = 5973136,
    area = 43094F,
    gdp = 4.2838501E11F,
    unemployment = 5.14F,
    imports = 2.40269001E11F,
    exports = 2.78916989E11F,
    flag = @Composable { DanemarkFlag() }
){

}