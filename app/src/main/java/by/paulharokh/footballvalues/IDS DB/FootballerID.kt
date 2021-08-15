package by.paulharokh.stolendb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ids")
data class FootballerID(
    @PrimaryKey val id: Int,
    val position: String,
    val name: String
)
