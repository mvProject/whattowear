package com.testapp.whattowear

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.testapp.whattowear.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.hostFragment,
            MainFragment()
        ).commit()
    }
}
