package by.paulharokh.footballvalues.app_ui.drawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.paulharokh.footballvalues.R


class StatsModeAdapter (var stats: Array<StatsMode>) :
    RecyclerView.Adapter<StatsModeAdapter.StatsModeHolder>() {

    class StatsModeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvStatsMode: TextView? = null
        var tvTotalScore: TextView? = null
        var tvWinRate: TextView? = null

        init {
            tvStatsMode = itemView.findViewById(R.id.tv_stat_name_id)
            tvTotalScore = itemView.findViewById(R.id.tv_total_score_id)
            tvWinRate = itemView.findViewById(R.id.tv_win_rate_id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsModeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(R.layout.stat_layout, parent, false)

        return StatsModeHolder(layout)
    }

    override fun getItemCount(): Int {
        return stats.size
    }

    override fun onBindViewHolder(holder: StatsModeHolder, position: Int) {
        val statMode = stats[position]
        holder.tvStatsMode?.text =  statMode.nameStat
        holder.tvTotalScore?.text = statMode.totalScore
        holder.tvWinRate?.text = statMode.winrate+"%"
    }

}