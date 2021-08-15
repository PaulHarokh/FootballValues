package by.paulharokh.stolendb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FootballerID::class],version = 1)
abstract class idsDatabase : RoomDatabase(){
    abstract fun idsDao(): DaoIDS
}