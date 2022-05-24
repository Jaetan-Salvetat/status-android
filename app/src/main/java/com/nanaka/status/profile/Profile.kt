package com.nanaka.status.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.nanaka.status.R
import com.nanaka.status.authentication.AuthController

class Profile : Fragment() {

    private val statusKeys : Map<Int, String> = mapOf(
        R.id.online_toggle to "online",
        R.id.away_toggle to "away",
        R.id.offline_toggle to "offline")

    private lateinit var statusMessageInput : EditText
    private lateinit var saveButton : Button
    private lateinit var disconnectButton : Button
    private lateinit var errorMessageView: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        statusMessageInput = view.findViewById(R.id.status_message)
        saveButton = view.findViewById(R.id.save_button)
        disconnectButton = view.findViewById(R.id.disconnect_button)
        errorMessageView = view.findViewById(R.id.error_message)


        disconnectButton.setOnClickListener {
            context?.let { AuthController(it).disconnect() }
        }
        saveButton.setOnClickListener { saveButton() }

        return view
    }


    private fun saveButton()
    {
        val statusMessage : String = statusMessageInput.text.toString()
        if(statusMessage.isEmpty())
        {
            errorMessageView.visibility = View.VISIBLE
            return
        }
        errorMessageView.visibility = View.INVISIBLE



    }
}