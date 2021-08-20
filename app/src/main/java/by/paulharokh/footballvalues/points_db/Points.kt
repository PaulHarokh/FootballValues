package by.paulharokh.footballvalues.points_db


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points")
data class Points(
    @PrimaryKey val gmName: String,
    var gmP: Int,
    var gmPs: Int
)
