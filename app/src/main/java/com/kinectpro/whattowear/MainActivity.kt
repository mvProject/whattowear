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
import com.kinectpro.whattowear.utils.TripSavingDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TripSavingDialog.DefaultListDialogListener {

    private val TRIP_SAVE_DIALOG_TAG = "TripSaveDialog"

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
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
                    val myDialogFragment = TripSavingDialog()
                    val manager = supportFragmentManager
                    myDialogFragment.show(manager, TRIP_SAVE_DIALOG_TAG)
                }
            }
        }
    }

    override fun onFinishDialog(defaultListChecked: Boolean) {
        Toast.makeText(
            this, defaultListChecked.toString(),
            Toast.LENGTH_LONG
        ).show()
        val tripFragment =
            nav_host_fragment.childFragmentManager.primaryNavigationFragment as TripFragment?
        tripFragment?.saveTrip()
        navController.navigate(R.id.action_TripFragment_to_TripListFragment)
    }
}
