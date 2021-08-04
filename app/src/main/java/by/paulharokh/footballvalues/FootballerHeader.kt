package by.paulharokh.footballvalues

data class PlayerHeader(
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
    val fullName: String,
    val image: String
)
