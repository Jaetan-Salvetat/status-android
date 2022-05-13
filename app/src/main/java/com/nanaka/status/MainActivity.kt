package com.nanaka.status

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.nanaka.status.home.HomePageFragment
import com.nanaka.status.services.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigation.init(supportFragmentManager, HomePageFragment(), R.id.main_layout)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Navigation.back()
    }
}