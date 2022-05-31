package com.nanaka.status.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButtonToggleGroup
import com.nanaka.status.R
import com.nanaka.status.controllers.AuthController
import com.nanaka.status.controllers.UserController
import com.nanaka.status.controllers.WsController
import com.nanaka.status.services.LocalStorage
import com.nanaka.status.services.Navigation
import org.json.JSONObject

class Profile : Fragment() {

    private val statusKeys : Map<Int, String> = mapOf(
        R.id.online_toggle to "online",
        R.id.away_toggle to "away",
        R.id.offline_toggle to "offline")

    private lateinit var statusMessageInput : EditText
    private lateinit var statusTypeManager : MaterialButtonToggleGroup
    private lateinit var saveButton : Button
    private lateinit var disconnectButton : Button
    private lateinit var errorMessageView: TextView
    private lateinit var followedLen: TextView
    private lateinit var followerLen: TextView
    private lateinit var toolBar: MaterialToolbar
    private lateinit var profilePicture: ImageView
    private lateinit var wsController: WsController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        statusMessageInput = view.findViewById(R.id.status_message)
        saveButton = view.findViewById(R.id.save_button)
        disconnectButton = view.findViewById(R.id.disconnect_button)
        errorMessageView = view.findViewById(R.id.error_message)
        followerLen = view.findViewById(R.id.follower_value)
        followedLen = view.findViewById(R.id.followed_value)
        toolBar = view.findViewById(R.id.toolbar)
        profilePicture = view.findViewById(R.id.profile_picture)
        statusTypeManager = view.findViewById(R.id.status_button_manager)

        toolBar.title = LocalStorage.username
        wsController = context?.let { WsController(it) }!!



        getUserInfo()



        toolBar.setNavigationOnClickListener { Navigation.back() }
        saveButton.setOnClickListener { saveButton() }
        disconnectButton.setOnClickListener {
            context?.let { AuthController(it).disconnect() }
        }

        return view
    }


    private fun saveButton()
    {
        val statusMessage : String = statusMessageInput.text.toString()
        val statusType = statusTypeManager.checkedButtonId

        if(statusMessage.isEmpty() || statusType == View.NO_ID) {
            errorMessageView.visibility = View.VISIBLE
            return
        }

        errorMessageView.visibility = View.INVISIBLE
        statusKeys[statusType]?.let {
            wsController.editStatus(it, statusMessage)
            Log.d("XXXXXXXXXXXXXXXXXXXXXXXXXXX", "status: $it")
        }
    }

    private fun getUserInfo(){
        val body = JSONObject()
        body.put("username", LocalStorage.username)
        body.put("token", LocalStorage.token)

        context?.let { c ->
            UserController(c).getUserInfo(body) { user ->
                val followed = user.followedLen ?: 0
                val follower = user.followerLen ?: 0

                user.status?.let { initStatus(it.type, user.status!!.name) }

                followerLen.text = follower.toString()
                followedLen.text = followed.toString()

                if(!user.profilePicture.isNullOrBlank()){
                    context?.let { Glide.with(it).load(user.profilePicture).into(profilePicture) }
                }
            }
        }
    }

    private fun initStatus(type: String, name: String){
        statusKeys.forEach {
            if(type == it.value){
                statusTypeManager.check(it.key)
                return@forEach
            }
        }

        statusMessageInput.setText(name)
    }
}