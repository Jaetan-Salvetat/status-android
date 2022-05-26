package com.nanaka.status.controllers

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.nanaka.status.misc.UiMisc
import com.nanaka.status.models.Status
import com.nanaka.status.models.User
import com.nanaka.status.services.LocalStorage
import com.nanaka.status.services.http.ContentType
import com.nanaka.status.services.http.HttpRequest
import org.json.JSONObject
import java.io.StringReader

class UserController(private val context: Context) {
    private var loadingDialog = UiMisc.getLoadingDialog(context)

    fun searchUsers(searchValue: String, callback: (MutableList<User>) -> Unit){
        loadingDialog.show()
        HttpRequest.get("/users/search/$searchValue") { data: JSONObject?, msg: String? ->
            (context as Activity).runOnUiThread {
                loadingDialog.dismiss()
                val jsonUsers = data?.getJSONArray("users")
                if(jsonUsers != null && msg == "success"){
                    try {
                        val reader = StringReader(jsonUsers.toString())
                        val users = Gson().fromJson(reader, Array<User>::class.java).toMutableList()
                        callback(users)
                    }catch (e: Exception){
                        Log.d("XXXXXXXXXXXXXXXXXXX", "error: $e")
                    }
                }
            }
        }
    }

    fun findMyFollows(callback: (MutableList<User>) -> Unit){
        loadingDialog.show()

        val body = JSONObject()
        body.put("username", LocalStorage.username)
        body.put("token", LocalStorage.token)

        HttpRequest.post("/users/followers", body.toString(), ContentType.Json) { data: JSONObject?, msg: String? ->
            (context as Activity).runOnUiThread {
                loadingDialog.dismiss()
                val jsonUsers = data?.getJSONArray("users")
                if(jsonUsers != null && msg == "success"){
                    val users = mutableListOf<User>()
                    try {
                        for(i in 0 until jsonUsers.length()){
                            val user = jsonUsers.getJSONObject(i).getJSONObject("User")
                            val status = user.getJSONObject("Status")
                            val userReader = StringReader(user.toString())
                            val statusReader = StringReader(status.toString())

                            users.add(Gson().fromJson(userReader, User::class.java))
                            users[i].status = Gson().fromJson(statusReader, Status::class.java)
                        }

                        Log.d("XXXXXXXXXXXXXXXXXXXXX", "users: $data")
                        callback(users)
                    }catch (e: Exception){
                        Log.d("XXXXXXXXXXXXXXXXXXX", "error: $e")
                    }
                }
            }
        }
    }

    fun follow(followedUsername: String, callback: (Boolean, String) -> Unit){
        loadingDialog.show()
        val body = JSONObject()
        body.put("username", LocalStorage.username)
        body.put("token", LocalStorage.token)
        body.put("followedUsername", followedUsername)

        HttpRequest.post("/users/follow", body.toString(), ContentType.Json) { _, msg: String? ->
            (context as Activity).runOnUiThread {
                loadingDialog.dismiss()
                if(msg == "success") {
                    callback(true, msg)
                    return@runOnUiThread
                }
                callback(false, msg ?: "Error")
            }
        }
    }
}