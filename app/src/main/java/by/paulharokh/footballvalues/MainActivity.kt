package by.paulharokh.footballvalues

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    lateinit var lvlVM: ViewModelLvl
    lateinit var lvlSettings: SharedPreferences

    override fun onStop() {
        super.onStop()
            val editor: SharedPreferences.Editor = lvlSettings.edit()
            editor.clear()
            editor.putInt("lvlSettings", lvlVM.clvlVM)
            editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvlVM = ViewModelProvider(this).get(ViewModelLvl::class.java)
        lvlSettings = getSharedPreferences("lvlSettings", Context.MODE_PRIVATE)
        if(lvlSettings.getInt("lvlSettings", 0) == 0){
            val editor: SharedPreferences.Editor = lvlSettings.edit()
            editor.putInt("lvlSettings", 5)
            editor.apply()
        }
        lvlVM.clvlVM = lvlSettings.getInt("lvlSettings", 0)
        Log.d("!!!vm",lvlVM.clvlVM.toString())
        Log.d("!!!sp",lvlSettings.getInt("lvlSettings", 0).toString())


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setSupportActionBar(toolbar_id)

        setupActionBarWithNavController(navController, drawer_lay)
        toolbar_id.setupWithNavController(navController, drawer_lay)
        nav_view_id.setupWithNavController(navController)

    }

    override fun onBackPressed() {
        val destination = navController.currentDestination
        if (destination!!.id == R.id.dealResult) {
            navController.navigate(R.id.mainMode)
        }
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    fun actualLvl(item: MenuItem){
        when(lvlVM.clvlVM){
            5 ->  Toast.makeText(applicationContext, "Actual lvl is Hard", Toast.LENGTH_SHORT).show()
            10 ->  Toast.makeText(applicationContext, "Actual lvl is Medium", Toast.LENGTH_SHORT).show()
            15 ->  Toast.makeText(applicationContext, "Actual lvl is Low", Toast.LENGTH_SHORT).show()
        }
    }

    fun lowClicked(item: MenuItem) {
        Toast.makeText(applicationContext, "Low lvl selected", Toast.LENGTH_SHORT).show()
        lvlVM.clvlVM = 15
        Log.d("!!!", lvlVM.clvlVM.toString())
    }

    fun mediumClicked(item: MenuItem) {
        Toast.makeText(applicationContext, "Medium lvl selected", Toast.LENGTH_SHORT).show()
        lvlVM.clvlVM = 10
        Log.d("!!!", lvlVM.clvlVM.toString())
    }

    fun highClicked(item: MenuItem) {
        Toast.makeText(applicationContext, "High lvl selected", Toast.LENGTH_SHORT).show()
        lvlVM.clvlVM = 5
        Log.d("!!!", lvlVM.clvlVM.toString())

    }


}