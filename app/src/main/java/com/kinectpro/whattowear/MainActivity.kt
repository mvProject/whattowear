package com.kinectpro.whattowear

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kinectpro.whattowear.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private val TAG_MAIN_FRAGMENT = "main_fragment"

    private var mainFragment: MainFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fm = supportFragmentManager

        mainFragment = fm.findFragmentByTag(TAG_MAIN_FRAGMENT) as MainFragment?

        if (mainFragment == null) {
            fm.beginTransaction().replace(
                R.id.hostFragment,
                MainFragment(), TAG_MAIN_FRAGMENT
            ).commit()
        }
    }
}