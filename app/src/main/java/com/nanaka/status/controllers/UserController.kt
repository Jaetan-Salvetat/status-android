package com.nanaka.status.controllers

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.nanaka.status.misc.UiMisc
import com.nanaka.status.models.User
import com.nanaka.status.search.MainAdapter
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
                        Log.d("XXXXXXXXXXXXXXXXXXX", "error: ${e.toString()}")
                    }
                }
            }
        }
    }
}