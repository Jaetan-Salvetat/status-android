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


      fun getUserInfo(body: JSONObject, callback: (User) -> Unit){
            HttpRequest.post("/users/me", body.toString(), ContentType.Json) {data: JSONObject?, msg: String? ->
                  (context as Activity).runOnUiThread {
                        if(msg != null && data != null && msg == "success"){
                              val jsonUser = data.getJSONObject("user")
                              val jsonStatus = jsonUser.getJSONObject("Status")
                              val userReader = StringReader(jsonUser.toString())
                              val statusReader = StringReader(jsonStatus.toString())

                              val user = Gson().fromJson(userReader, User::class.java)
                              user.status = Gson().fromJson(statusReader, Status::class.java)
                              user.followedLen = data.getInt("followedLen")
                              user.followerLen = data.getInt("followerLen")

                              callback(user)
                        }
                  }
            }
      }


      fun searchUsers(searchValue: String, callback: (MutableList<User>) -> Unit){
            loadingDialog.show()
            HttpRequest.get("/users/search/${LocalStorage.username}?q=$searchValue") { data: JSONObject?, msg: String? ->
                  (context as Activity).runOnUiThread {
                        loadingDialog.dismiss()
                        val jsonUsers = data?.getJSONArray("users")
                        val jsonFollows = data?.getJSONArray("follows")

                        if(jsonUsers != null && jsonFollows != null && msg == "success"){
                              try {
                                    val readerUsers = StringReader(jsonUsers.toString())
                                    val readerFollows = StringReader(jsonFollows.toString())

                                    val users = Gson().fromJson(readerUsers, Array<User>::class.java).toMutableList()
                                    val follows = Gson().fromJson(readerFollows, Array<Int>::class.java).toMutableList()

                                    for(id in users.indices){
                                          if(follows.contains(users[id].id)){
                                                users[id].isFollowed = true
                                          }
                                    }

                                    callback(users)
                              }catch (e: Exception){
                                    Log.d("XXXXXXXXXXXXXXXXXXX", "error: $e")
                              }
                        }
                  }
            }
      }

      fun findMyFollows(callback: (MutableList<User>) -> Unit){
            val body = JSONObject()
            body.put("username", LocalStorage.username)
            body.put("token", LocalStorage.token)

            HttpRequest.post("/users/followers", body.toString(), ContentType.Json) { data: JSONObject?, msg: String? ->
                  (context as Activity).runOnUiThread {

                        val jsonUsers = data?.getJSONArray("users")

                        if(jsonUsers != null && msg == "success"){
                              val users = mutableListOf<User>()

                              try {
                                    for(i in 0 until jsonUsers.length()){
                                          val user = jsonUsers.getJSONObject(i)
                                          val status = user.getJSONObject("Status")
                                          val userReader = StringReader(user.toString())
                                          val statusReader = StringReader(status.toString())

                                          users.add(Gson().fromJson(userReader, User::class.java))
                                          users[i].status = Gson().fromJson(statusReader, Status::class.java)
                                    }

                                    Log.d("XXXXXXXXXXXXXXXXXXXXX", "users: $users")
                                    callback(users)
                              }catch (e: Exception){
                                    Log.d("XXXXXXXXXXXXXXXXXXX", "error: $e")
                              }
                        }
                  }
            }
      }

      fun follow(followedUsername: String, callback: (Boolean, String) -> Unit){
            val body = JSONObject()
            body.put("username", LocalStorage.username)
            body.put("token", LocalStorage.token)
            body.put("followedUsername", followedUsername)

            HttpRequest.post("/follow", body.toString(), ContentType.Json) { _, msg: String? ->
                  (context as Activity).runOnUiThread {
                        Log.d("XXXXXXXXXXXXXXXXX", "data: $msg")
                        if(msg == "success") {
                              callback(true, msg)
                              return@runOnUiThread
                        }
                        callback(false, msg ?: "Error")
                  }
            }
      }

      fun unfollow(followed: Int, callback: (Boolean, String) -> Unit){
            val body = JSONObject()
            body.put("username", LocalStorage.username)
            body.put("token", LocalStorage.token)
            body.put("followed", followed)


            HttpRequest.post("/follow/remove", body.toString(), ContentType.Json) { _, msg: String? ->
                  (context as Activity).runOnUiThread {
                        if(msg == "success") {
                              callback(true, msg)
                              return@runOnUiThread
                        }
                        callback(false, msg ?: "Error")
                  }
            }
      }
}