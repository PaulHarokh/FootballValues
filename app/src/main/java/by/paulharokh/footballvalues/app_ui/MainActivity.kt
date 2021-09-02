package by.paulharokh.footballvalues.app_ui

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
import by.paulharokh.footballvalues.R
import by.paulharokh.footballvalues.view_model.ViewModelLvl
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var lvlVM: ViewModelLvl
    private lateinit var lvlSettings: SharedPreferences

    override fun onStop() {
        super.onStop()
        val editor: SharedPreferences.Editor = lvlSettings.edit()
        editor.clear()
        editor.putInt("lvlSettings", lvlVM.cLvlVM)
        editor.apply()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvlVM = ViewModelProvider(this).get(ViewModelLvl::class.java)
        lvlSettings = getSharedPreferences("lvlSettings", Context.MODE_PRIVATE)
        if (lvlSettings.getInt("lvlSettings", 0) == 0) {
            val editor: SharedPreferences.Editor = lvlSettings.edit()
            editor.putInt("lvlSettings", 5)
            editor.apply()
        }
        lvlVM.cLvlVM = lvlSettings.getInt("lvlSettings", 0)
        Log.d("!!!vm", lvlVM.cLvlVM.toString())
        Log.d("!!!sp", lvlSettings.getInt("lvlSettings", 0).toString())


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

    fun actualLvl(item: MenuItem) {
        when (lvlVM.cLvlVM) {
            5 -> Toast.makeText(applicationContext, "Actual lvl is Hard", Toast.LENGTH_SHORT).show()
            10 -> Toast.makeText(applicationContext, "Actual lvl is Medium", Toast.LENGTH_SHORT)
                .show()
            15 -> Toast.makeText(applicationContext, "Actual lvl is Low", Toast.LENGTH_SHORT).show()
        }
    }

    fun lowClicked(item: MenuItem) {
        Toast.makeText(applicationContext, "Low lvl selected", Toast.LENGTH_SHORT).show()
        lvlVM.cLvlVM = 15
        Log.d("!!!", lvlVM.cLvlVM.toString())
    }

    fun mediumClicked(item: MenuItem) {
        Toast.makeText(applicationContext, "Medium lvl selected", Toast.LENGTH_SHORT).show()
        lvlVM.cLvlVM = 10
        Log.d("!!!", lvlVM.cLvlVM.toString())
    }

    fun highClicked(item: MenuItem) {
        Toast.makeText(applicationContext, "Hard lvl selected", Toast.LENGTH_SHORT).show()
        lvlVM.cLvlVM = 5
        Log.d("!!!", lvlVM.cLvlVM.toString())

    }


}