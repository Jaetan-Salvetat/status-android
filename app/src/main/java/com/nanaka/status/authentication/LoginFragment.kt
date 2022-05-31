package com.nanaka.status.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.nanaka.status.R
import com.nanaka.status.controllers.AuthController
import com.nanaka.status.services.Navigation


class LoginFragment : Fragment() {
    private lateinit var appBar: MaterialToolbar
    private lateinit var inputAuth: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var errorMessage: TextView
    private lateinit var loginButton: Button

    private var authController: AuthController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        appBar = view.findViewById(R.id.appbar)
        inputAuth = view.findViewById(R.id.input_auth)
        inputPassword = view.findViewById(R.id.input_password)
        errorMessage = view.findViewById(R.id.error_message)
        loginButton = view.findViewById(R.id.login_button)
        authController = context?.let { AuthController(it) }

        appBar.setNavigationOnClickListener { Navigation.back() }
        loginButton.setOnClickListener {
            authController?.login(inputAuth.text.toString(), inputPassword.text.toString(), errorMessage)
        }

        return view
    }
}