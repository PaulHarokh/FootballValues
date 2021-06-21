package by.paulharokh.footballvalues

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class GameModeAdapter (var viewModel: gmViewModel, val fragment: ModeMenu) :
    RecyclerView.Adapter<GameModeAdapter.GameModeHolder>() {

    class GameModeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNameMode: TextView? = null
        var tvScore: TextView? = null
        var imGameMode: ImageView? = null

        init {
            tvNameMode = itemView.findViewById(R.id.tv_name_id)
            tvScore = itemView.findViewById(R.id.tv_score_id)
            imGameMode = itemView.findViewById(R.id.im_gamemode_id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameModeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(R.layout.item_layout, parent, false)
        val holder = GameModeHolder(layout)

        holder.itemView.setOnClickListener {
            fragment.startGame(holder.adapterPosition)
        }
        return holder
    }

    override fun getItemCount(): Int {
        return viewModel.modes.size
    }

    override fun onBindViewHolder(holder: GameModeHolder, position: Int) {
        val gameMode = viewModel.modes[position]

        holder.tvNameMode?.text = gameMode.nameMode
        holder.tvScore?.text = gameMode.scoreMode.toString()
        holder.imGameMode?.setImageResource(gameMode.photoRes)
    }
}