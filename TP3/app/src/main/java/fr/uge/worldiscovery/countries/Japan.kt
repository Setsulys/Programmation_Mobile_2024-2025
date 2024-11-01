package fr.uge.worldiscovery.countries

data object Japan : Country(
    "Japan",
    population = 123_201_945,
    area = 377_915f,
    gdp = 5_761_000_000_000f,
    unemployment = 2.58f,
    imports = 989_843_000_000f,
    exports = 920_737_000_000f,
    flag = { JapanFlag() }
) {}