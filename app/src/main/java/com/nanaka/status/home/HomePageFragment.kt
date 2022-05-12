package com.nanaka.status.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nanaka.status.R
import com.nanaka.status.register.RegisterFragment
import com.nanaka.status.services.Navigation


class HomePageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?



    ): View? {
        // Inflate the layout for this fragment

        var notConnected : Boolean = false

        if(notConnected == false)
        {
            Navigation.push(RegisterFragment())
        }

        return inflater.inflate(R.layout.fragment_home_page, container, false)
    }
}