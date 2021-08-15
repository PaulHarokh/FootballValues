package by.paulharokh.footballvalues

import android.os.Bundle
import android.util.Log
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
import by.paulharokh.stolendb.idsDatabase
import kotlinx.android.synthetic.main.fragment_main_mode.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModeMenu : Fragment() {

    lateinit var navController: NavController
    lateinit var viewModelGM: GMViewModel
    lateinit var viewModelF: FViewModel

    val apiRequest = ApiRequest.create()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModelGM = ViewModelProvider(this).get(GMViewModel::class.java)

        val modes = arrayOf(
            R.drawable.draw_ronaldo to "Strikers",
            R.drawable.draw_xavi to "Midfielders",
            R.drawable.draw_maldini to "Defenders",
            R.drawable.draw_yashin to "Goalkeepers",
        )

        fun addToGM(pair: Pair<Int, String>) {
            viewModelGM.modesVM.add(
                GameMode(
                    pair.first,
                    pair.second,
                    0
                )
            )
        }

        if (viewModelGM.modesVM.isEmpty()) {
            for (i in modes.indices) {
                addToGM(modes[i])
            }
        }



        return inflater.inflate(R.layout.fragment_main_mode, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        rv_myList_id.adapter = GameModeAdapter(viewModelGM, this)
        rv_myList_id.layoutManager = LinearLayoutManager(context)
    }

    suspend fun startGame(adapterPosition: Int) {

        viewLifecycleOwner.lifecycleScope.launch {

            val db =
                Room.databaseBuilder(activity as MainActivity, idsDatabase::class.java, "idsDB.db")
                    .createFromAsset("footballersIDS.db")
                    .allowMainThreadQueries()
                    .build()

            val strIDS = db.idsDao().getStrikers().shuffled().first()
            val midIDS = db.idsDao().getMidfielders().shuffled().first()
            val defIDS = db.idsDao().getDefenders().shuffled().first()
            val gkIDS = db.idsDao().getGoalkeepers().shuffled().first()


            viewLifecycleOwner.lifecycleScope.launch {

                viewModelF = ViewModelProvider(activity as MainActivity).get(FViewModel::class.java)

                val totalID= when (adapterPosition) {
                    0 -> strIDS
                    1 -> midIDS
                    2 -> defIDS
                    3 -> gkIDS
                    else -> strIDS
                }

                val footballerReq = apiRequest.getFootballer(
                    totalID,
                    "transfermarket.p.rapidapi.com",
                    "186305a549mshfe32eafb57a74a1p1531f2jsnc6d24c94dab5"
                )

                footballerReq.enqueue(object : Callback<FootballerHeader> {
                    override fun onResponse(
                        call: Call<FootballerHeader>,
                        response: Response<FootballerHeader>
                    ) {
                        response.body()?.let {

                            viewModelF.footballerVM = it

                            navController.navigate(R.id.deal)
                        }
                    }

                    override fun onFailure(call: Call<FootballerHeader>, t: Throwable) {
                        Log.d("!!!t", t.toString())
                    }
                })
            }
        }
    }

}