package com.nanaka.status.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.nanaka.status.R
import com.nanaka.status.authentification.AuthController

class Profile : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<Button>(R.id.disconnect_button).setOnClickListener {
            context?.let { AuthController(it).disconnect() }
        }

        return view
    }
}