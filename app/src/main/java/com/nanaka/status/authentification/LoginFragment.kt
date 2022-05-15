package com.nanaka.status.authentification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.nanaka.status.R
import com.nanaka.status.services.Navigation

class LoginFragment : Fragment() {
    private lateinit var appBar: MaterialToolbar

    private lateinit var inputUsername: TextInputEditText
    private lateinit var inputMail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var registerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        appBar = view.findViewById(R.id.appbar)



        appBar.setNavigationOnClickListener { Navigation.back() }

        return view
    }

}