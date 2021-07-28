package by.paulharokh.footballvalues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {



    val apiRequest = ApiRequest.create()

    private lateinit var navController: NavController
    lateinit var viewModel: gmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(gmViewModel::class.java)

        val modes = arrayOf(
            R.drawable.draw_ronaldo to "Strikers",
            R.drawable.draw_xavi to "Midfielders",
            R.drawable.draw_maldini to "Defenders",
            R.drawable.draw_yashin to "Goalkeepers",
        )

        fun addToGM(pair: Pair<Int, String>) {
            viewModel.modes.add(GameMode(pair.first, pair.second, 0))
        }

        for (i in modes.indices) {
            addToGM(modes[i])
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(toolbar_id)

        setupActionBarWithNavController(navController, drawer_lay)
        toolbar_id.setupWithNavController(navController, drawer_lay)
        nav_view_id.setupWithNavController(navController)



        CoroutineScope(Dispatchers.Default).launch {

            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://transfermarket.p.rapidapi.com/players/get-market-value?id=74842")
                .get()
                .addHeader("x-rapidapi-key", "186305a549mshfe32eafb57a74a1p1531f2jsnc6d24c94dab5")
                .addHeader("x-rapidapi-host", "transfermarket.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()

            response.body()?.let { Log.d("!!!r", it.string()) }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}
