package by.paulharokh.footballvalues

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import by.paulharokh.footballvalues.points_db.PointsDatabase
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlin.math.roundToInt

class FragmentStatistics : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val statsDB = Room.databaseBuilder(
            activity as MainActivity,
            PointsDatabase::class.java,
            "points"
        )
            .allowMainThreadQueries()
            .createFromAsset("points_GM.db")
            .build()

        val strP = statsDB.pointsDao().getStrP().gmP
        val midP = statsDB.pointsDao().getMidP().gmP
        val defP = statsDB.pointsDao().getDefP().gmP
        val gkP = statsDB.pointsDao().getGkP().gmP
        val strPs = statsDB.pointsDao().getStrP().gmPs.toDouble()
        val midPs = statsDB.pointsDao().getMidP().gmPs.toDouble()
        val defPs = statsDB.pointsDao().getDefP().gmPs.toDouble()
        val gkPs = statsDB.pointsDao().getGkP().gmPs.toDouble()

        if (strP == 0 || midP == 0 || defP == 0 || gkP == 0) {
            tv_stats_title_id.text = "Please play every game mode!"
        } else {

            val statsScores = arrayListOf(strP, midP, defP, gkP)

            val sortedStatsScores = statsScores.sorted()

            when (sortedStatsScores[sortedStatsScores.lastIndex]) {
                statsScores[0] -> {
                    tv_stats_title_id.text = tv_stats_title_id.text.toString() + " Strikers"
                    im_favourite_game_mode_id.setImageResource(R.drawable.draw_ronaldo)
                }
                statsScores[1] -> {
                    tv_stats_title_id.text = tv_stats_title_id.text.toString() + " Midfielders"
                    im_favourite_game_mode_id.setImageResource(R.drawable.draw_xavi)
                }
                statsScores[2] -> {
                    tv_stats_title_id.text = tv_stats_title_id.text.toString() + " Defenders"
                    im_favourite_game_mode_id.setImageResource(R.drawable.draw_maldini)
                }
                statsScores[3] -> {
                    tv_stats_title_id.text = tv_stats_title_id.text.toString() + " Goalkeepers"
                    im_favourite_game_mode_id.setImageResource(R.drawable.draw_yashin)
                }
            }

            fun roundStats(num: Double): Double {
                return ((num * 100.0) * 100).roundToInt() / 100.0
            }

            val stats = arrayOf(
                StatsMode("Strikers", strP, roundStats(strPs / strP)),
                StatsMode("Midfielders", midP, roundStats(midPs / midP)),
                StatsMode("Defenders", defP, roundStats(defPs / defP)),
                StatsMode("Goalkeepers", gkP, roundStats(gkPs / gkP))
            )

            rv_stats_id.adapter = StatsModeAdapter(stats)
            rv_stats_id.layoutManager = LinearLayoutManager(context)
        }

    }


}