package com.nanaka.status

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nanaka.status.home.HomePageFragment
import com.nanaka.status.misc.http.HttpRequest
import com.nanaka.status.services.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Navigation.init(supportFragmentManager, HomePageFragment(), R.id.main_layout)
        HttpRequest.init("https://status.jaetan.fr")
    }

    override fun onBackPressed() {
        Navigation.back()
    }
}