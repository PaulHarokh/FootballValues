package by.paulharokh.stolendb

import androidx.room.Dao
import androidx.room.Query

@Dao
interface DaoIDS {
    @Query("SELECT id FROM ids WHERE position = 'striker'")
    suspend fun getStrikers(): List<Int>

    @Query("SELECT id FROM ids WHERE position = 'midfielder'")
    suspend fun getMidfielders(): List<Int>

    @Query("SELECT id FROM ids WHERE position = 'defender'")
    suspend fun getDefenders(): List<Int>

    @Query("SELECT id FROM ids WHERE position = 'goalkeeper'")
    suspend fun getGoalkeepers(): List<Int>
}
