package by.paulharokh.footballvalues.points_db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update


@Dao
interface DaoPoints {
    @Update
    fun update(upPoints: Points)

    @Query("SELECT * FROM points WHERE gmName = 'strikers'")
    fun getStrP(): Points

    @Query("SELECT * FROM points WHERE gmName = 'midfielders'")
    fun getMidP(): Points

    @Query("SELECT * FROM points WHERE gmName = 'defenders'")
    fun getDefP(): Points

    @Query("SELECT * FROM points WHERE gmName = 'goalkeepers'")
    fun getGkP(): Points

}
