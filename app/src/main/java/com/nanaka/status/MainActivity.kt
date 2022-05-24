package com.nanaka.status

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.nanaka.status.authentication.AuthController
import com.nanaka.status.authentication.NotConnectedFragment
import com.nanaka.status.home.HomePageFragment
import com.nanaka.status.services.LocalStorage
import com.nanaka.status.services.http.HttpRequest
import com.nanaka.status.services.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        LocalStorage.init(this)
        HttpRequest.init("https://status.jaetan.fr")


        val firstFragment = if(AuthController(this).userHasConnected()){
            HomePageFragment()
        }else{
            NotConnectedFragment()
        }

        Navigation.init(supportFragmentManager, firstFragment, R.id.main_layout)
    }

    override fun onBackPressed() {
        Navigation.back()
    }
}