package com.kinectpro.whattowear

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kinectpro.whattowear.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    //   private val TAG_MAIN_FRAGMENT = "main_fragment"
    //  private var mainFragment: MainFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment, R.id.secondTripFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
/*
        val fm = supportFragmentManager

        mainFragment = fm.findFragmentByTag(TAG_MAIN_FRAGMENT) as MainFragment?

        if (mainFragment == null) {
            fm.beginTransaction().replace(
                R.id.nav_host_fragment,
                MainFragment(), TAG_MAIN_FRAGMENT
            ).commit()
        }

 */
    }
}
