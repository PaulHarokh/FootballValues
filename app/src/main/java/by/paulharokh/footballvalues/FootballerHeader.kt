package by.paulharokh.footballvalues

data class FootballerHeader(
    val data: Data
)

data class Data(
    val player: Player,
    val club: Club
)

data class Player(
    val name: String,
    val image: String,
    val marketValue: MarketValue
)

data class MarketValue(
    val value: Int
)

data class Club(
    val name: String,
    val image: String,
)
