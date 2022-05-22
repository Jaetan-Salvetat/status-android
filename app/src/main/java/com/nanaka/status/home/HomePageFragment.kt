package com.nanaka.status.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.appbar.AppBarLayout
import com.nanaka.status.R
import com.nanaka.status.authentification.AuthController
import com.nanaka.status.profile.Profile
import com.nanaka.status.services.Navigation


class HomePageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        view.findViewById<ImageView>(R.id.edit_profile).setOnClickListener() { goToProfile() }

        return view
    }

    fun goToProfile()
    {
        Navigation.push(Profile())
    }


}