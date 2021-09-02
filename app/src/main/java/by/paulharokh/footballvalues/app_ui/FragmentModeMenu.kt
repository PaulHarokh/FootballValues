package by.paulharokh.footballvalues.app_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import by.paulharokh.footballvalues.*
import by.paulharokh.footballvalues.ids_db.IdsDatabase
import by.paulharokh.footballvalues.points_db.PointsDatabase
import by.paulharokh.footballvalues.remote_model.ApiRequest
import by.paulharokh.footballvalues.remote_model.FootballerHeader
import by.paulharokh.footballvalues.view_model.ViewModelFootballer
import by.paulharokh.footballvalues.view_model.ViewModelGM
import kotlinx.android.synthetic.main.fragment_main_mode.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentModeMenu : Fragment() {

    lateinit var navController: NavController
    lateinit var viewModelGM: ViewModelGM
    lateinit var viewModelFootballer: ViewModelFootballer

    val apiRequest = ApiRequest.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        viewModelGM = ViewModelProvider(activity as MainActivity).get(ViewModelGM::class.java)

        viewModelGM.modesScoreVM = Room.databaseBuilder(
            activity as MainActivity,
            PointsDatabase::class.java,
            "points"
        )
            .allowMainThreadQueries()
            .createFromAsset("points_GM.db")
            .build()

        val strP = viewModelGM.modesScoreVM!!.pointsDao().getStrP().gmPs
        val midP = viewModelGM.modesScoreVM!!.pointsDao().getMidP().gmPs
        val defP = viewModelGM.modesScoreVM!!.pointsDao().getDefP().gmPs
        val gkP = viewModelGM.modesScoreVM!!.pointsDao().getGkP().gmPs

        val modes = arrayOf(
            GameMode(R.drawable.draw_ronaldo,"Strikers", strP),
            GameMode(R.drawable.draw_xavi,"Midfielders", midP),
            GameMode(R.drawable.draw_maldini,"Defenders", defP),
            GameMode(R.drawable.draw_yashin,"Goalkeepers", gkP)
        )

        rv_game_mode_id.adapter = GameModeAdapter(modes, this)
        rv_game_mode_id.layoutManager = LinearLayoutManager(context)

    }

    suspend fun startGame(adapterPosition: Int) {

        viewModelGM.adapterPosVM = adapterPosition

        tv_menu_id.visibility = View.INVISIBLE
        rv_game_mode_id.visibility = View.INVISIBLE
        tv_load_id.visibility = View.VISIBLE
        progressBar_id.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {

            val db =
                Room.databaseBuilder(activity as MainActivity, IdsDatabase::class.java, "idsDB.db")
                    .createFromAsset("footballersIDS.db")
                    .build()

            viewLifecycleOwner.lifecycleScope.launch {

                viewModelFootballer = ViewModelProvider(activity as MainActivity).get(ViewModelFootballer::class.java)

                val totalID = when (adapterPosition) {
                    0 -> db.idsDao().getStrikers().shuffled().first()
                    1 -> db.idsDao().getMidfielders().shuffled().first()
                    2 -> db.idsDao().getDefenders().shuffled().first()
                    3 -> db.idsDao().getGoalkeepers().shuffled().first()
                    else -> 0
                }

                val footballerReq = apiRequest.getFootballer(
                    totalID,
                    "transfermarket.p.rapidapi.com",
                    "0a7260e53dmsh17e49502b2ef372p1880d6jsn36f76282f78a"
                )
                //key1 186305a549mshfe32eafb57a74a1p1531f2jsnc6d24c94dab5
                //key2 0a7260e53dmsh17e49502b2ef372p1880d6jsn36f76282f78a

                footballerReq.enqueue(object : Callback<FootballerHeader> {
                    override fun onResponse(
                        call: Call<FootballerHeader>,
                        response: Response<FootballerHeader>
                    ) {
                        response.body()?.let {

                            viewModelFootballer.footballerVM = it

                            navController.navigate(R.id.deal)
                        }
                    }

                    override fun onFailure(call: Call<FootballerHeader>, t: Throwable) {
                    }
                })
            }
        }
    }

}