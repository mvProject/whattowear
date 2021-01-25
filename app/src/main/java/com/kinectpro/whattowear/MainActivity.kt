package com.kinectpro.whattowear

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kinectpro.whattowear.ui.fragment.TripFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.background = null
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.TripFragment, R.id.TripListFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        fabAddTrip.setOnClickListener {
            when (navController.currentDestination?.id) {
                R.id.TripFragment -> {
                    val tripFragment =
                        nav_host_fragment.childFragmentManager.primaryNavigationFragment as TripFragment?
                    tripFragment?.let {
                        when (it.isTripReadyForSave()) {
                            true -> {
                                it.showDialog()
                            }
                            false -> {
                                Toast.makeText(
                                    this,
                                    getString(R.string.error_empty_trip_selected),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
}
