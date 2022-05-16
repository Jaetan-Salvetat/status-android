package com.nanaka.status.authentification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.nanaka.status.R
import com.nanaka.status.home.HomePageFragment
import com.nanaka.status.misc.http.HttpRequest
import com.nanaka.status.misc.UiMisc
import com.nanaka.status.misc.http.ContentType
import com.nanaka.status.services.Navigation
import org.json.JSONObject


class LoginFragment : Fragment() {
    private lateinit var appBar: MaterialToolbar

    private lateinit var inputAuth: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var errorMessage: TextView
    private lateinit var loginButton: Button

    private lateinit var loadingDialog: AlertDialog


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
        loadingDialog = context?.let { UiMisc.getLoadingDialog(it) }!!


        appBar.setNavigationOnClickListener { Navigation.back() }
        loginButton.setOnClickListener { login() }

        return view
    }

    private fun login(){
        loadingDialog.show()

        val body = JSONObject()
        body.put("auth", inputAuth.text.toString())
        body.put("password", inputPassword.text.toString())

        HttpRequest.post(
            "/auth/login",
            body.toString(),
            ContentType.Json
        ) { data, msg ->
            loadingDialog.dismiss()
            if(data == null){
                errorMessage.post {
                    errorMessage.text = msg
                }
                return@post
            }
            saveUserInfo()
            Navigation.pushReplacement(HomePageFragment())
        }
    }

    private fun saveUserInfo(){

    }
}