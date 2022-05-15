package com.nanaka.status.authentification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.appbar.MaterialToolbar
import com.nanaka.status.R
import com.nanaka.status.services.Navigation


class RegisterFragment : Fragment() {
    private lateinit var appBar: MaterialToolbar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        appBar = view.findViewById(R.id.appbar)



        appBar.setNavigationOnClickListener { Navigation.back() }
        return view
    }
}