package com.nanaka.status.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.nanaka.status.R
import com.nanaka.status.services.Navigation


class RegisterFragment : Fragment() {
    private lateinit var appBar: MaterialToolbar
    private lateinit var inputUsername: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var errorMessage: TextView
    private lateinit var registerButton: Button

    private var authController: AuthController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        appBar = view.findViewById(R.id.appbar)
        inputUsername = view.findViewById(R.id.input_username)
        inputEmail = view.findViewById(R.id.input_mail)
        inputPassword = view.findViewById(R.id.input_password)
        errorMessage = view.findViewById(R.id.error_message)
        registerButton = view.findViewById(R.id.register_button)
        authController = context?.let { AuthController(it) }


        appBar.setNavigationOnClickListener { Navigation.back() }
        registerButton.setOnClickListener {
            authController?.register(inputUsername.text.toString(), inputEmail.text.toString(), inputPassword.text.toString(), errorMessage)
        }



        return view
    }
}