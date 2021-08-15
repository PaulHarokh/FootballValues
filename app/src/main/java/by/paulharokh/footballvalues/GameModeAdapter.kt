package by.paulharokh.footballvalues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameModeAdapter (var viewModel: GMViewModel, val fragment: ModeMenu) :
    RecyclerView.Adapter<GameModeAdapter.GameModeHolder>() {

    class GameModeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNameMode: TextView? = null
        var tvScore: TextView? = null
        var imGameMode: ImageView? = null

        init {
            tvNameMode = itemView.findViewById(R.id.tv_GM_name_id)
            tvScore = itemView.findViewById(R.id.tv_score_id)
            imGameMode = itemView.findViewById(R.id.im_gamemode_id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameModeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = inflater.inflate(R.layout.item_layout, parent, false)
        val holder = GameModeHolder(layout)

            holder.itemView.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch{
                fragment.startGame(holder.adapterPosition)
            }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return viewModel.modesVM.size
    }

    override fun onBindViewHolder(holder: GameModeHolder, position: Int) {
        val gameMode = viewModel.modesVM[position]

        holder.tvNameMode?.text = gameMode.nameMode
        holder.tvScore?.text = gameMode.scoreMode.toString()
        holder.imGameMode?.setImageResource(gameMode.photoRes)
    }
}