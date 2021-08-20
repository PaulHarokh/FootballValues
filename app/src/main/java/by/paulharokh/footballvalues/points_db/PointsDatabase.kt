package by.paulharokh.footballvalues.points_db


import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Points::class],version = 1)
abstract class PointsDatabase : RoomDatabase(){
    abstract fun pointsDao(): DaoPoints
}