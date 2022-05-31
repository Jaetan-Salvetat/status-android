package com.nanaka.status.controllers

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.TextView
import com.nanaka.status.authentication.NotConnectedFragment
import com.nanaka.status.home.HomePageFragment
import com.nanaka.status.misc.UiMisc
import com.nanaka.status.services.LocalStorage
import com.nanaka.status.services.Navigation
import com.nanaka.status.services.http.ContentType
import com.nanaka.status.services.http.HttpRequest
import org.json.JSONObject
import java.util.concurrent.LinkedBlockingQueue

class AuthController(private val context: Context) {
    private var loadingDialog = UiMisc.getLoadingDialog(context)


    fun login(auth: String, password: String, errorMessage: TextView){
        loadingDialog.show()
        val body = JSONObject()
        body.put("auth", auth)
        body.put("password", password)

        HttpRequest.post(
            "/auth/login",
            body.toString(),
            ContentType.Json
        ) { data, msg ->
            (context as Activity).runOnUiThread {
                loadingDialog.dismiss()
                Log.d("XXXXXXXXXXXXXXXXXXXXX", "data: $data; msg: $msg")

                if(data != null && msg == "success"){
                    LocalStorage.username = data.getString("username")
                    LocalStorage.token = data.getString("token")
                    Navigation.pushClear(HomePageFragment())
                }

                errorMessage.text = msg
                return@runOnUiThread
            }
        }
    }

    fun register(username: String, email: String, password: String, errorMessage: TextView){
        loadingDialog.show()
        val body = JSONObject()
        body.put("username", username)
        body.put("email", email)
        body.put("password", password)

        HttpRequest.post(
            "/auth/register",
            body.toString(),
            ContentType.Json
        ) { data, msg ->
            (context as Activity).runOnUiThread {
                loadingDialog.dismiss()
                Log.d("XXXXXXXXXXXXXXXXXXXXX", "data: $data; msg: $msg")

                if(data != null && msg == "success"){
                    LocalStorage.username = data.getString("username")
                    LocalStorage.token = data.getString("token")
                    Navigation.pushClear(HomePageFragment())
                }

                errorMessage.text = msg
                return@runOnUiThread
            }
        }
    }

    fun userHasConnected() : Boolean {
        Log.d("XXXXXXXXXXXXXXXXXXXXX", "username: ${LocalStorage.username.isNotEmpty()}; token: ${LocalStorage.token.isNotEmpty()}")

        if(LocalStorage.username.isEmpty() || LocalStorage.token.isEmpty()){
            return false
        }

        val body = JSONObject()
        body.put("username", LocalStorage.username)
        body.put("token", LocalStorage.token)
        val queue = LinkedBlockingQueue<Boolean>(1)

        HttpRequest.post(
            "/auth/validate-user-session",
            body.toString(),
            ContentType.Json
        ) { data, msg ->
            if(data != null && msg == "success"){
                queue.add(true)
            }
            queue.add(false)
        }

        return queue.take()
    }

    fun disconnect(){
        LocalStorage.username = ""
        LocalStorage.token = ""
        Navigation.pushClear(NotConnectedFragment())
    }
}