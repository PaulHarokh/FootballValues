package by.paulharokh.footballvalues.ids_db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [FootballerID::class],version = 1)
abstract class IdsDatabase : RoomDatabase(){
    abstract fun idsDao(): DaoIDS
}