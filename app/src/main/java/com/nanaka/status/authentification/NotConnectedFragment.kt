package com.nanaka.status.authentification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.nanaka.status.R
import com.nanaka.status.services.Navigation

class NotConnectedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_not_connected, container, false)

        val loginButton = view.findViewById<Button>(R.id.login_button)
        val registerButton = view.findViewById<Button>(R.id.register_button)

        loginButton.setOnClickListener { login() }
        registerButton.setOnClickListener { register() }

        return view
    }

    private fun login()
    {
        Navigation.push(LoginFragment())
    }

    private fun register()
    {
        Navigation.push(RegisterFragment())
    }
}