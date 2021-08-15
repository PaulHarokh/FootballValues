package by.paulharokh.footballvalues



import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import by.paulharokh.stolendb.idsDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(toolbar_id)



        setupActionBarWithNavController(navController, drawer_lay)
        toolbar_id.setupWithNavController(navController, drawer_lay)
        nav_view_id.setupWithNavController(navController)


//
//        val db = Room.databaseBuilder(applicationContext, idsDatabase::class.java, "idsDB.db")
//            .createFromAsset("footballersIDS.db")
//            .allowMainThreadQueries()
//            .build()
//
//        val idsList = db.idsDao().getStrikers().shuffled()
//        Log.d("!!!",idsList.toString())
//
//        val idsList1 = db.idsDao().getMidfielders().shuffled()
//        Log.d("!!!",idsList1.toString())
//
//        val idsList2 = db.idsDao().getDefenders().shuffled()
//        Log.d("!!!",idsList2.toString())
//
//        val idsList3 = db.idsDao().getGoalkeepers().shuffled()
//        Log.d("!!!",idsList3.toString())

    }


    override fun onBackPressed() {
        val destination = navController.currentDestination
            if (destination!!.id == R.id.dealResult)
                return
                super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}