package com.nanaka.status.search

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nanaka.status.R
import com.nanaka.status.controllers.UserController
import com.nanaka.status.models.User

class MainAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var users: MutableList<User> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_main_adapter, parent, false)

        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(view: ViewHolder, id: Int) {
        val user = users[id]
        view.usernameText.text = user.username


        Log.d("XXXXXXXXXXXXXXXXXXXXXXX", "isFollowed: ${user.isFollowed}")
        followedManger(view, id)

        if(!user.profilePicture.isNullOrBlank()){
            Glide.with(view.context).load(user.profilePicture).into(view.profilePicture)
        }



        view.container.setOnClickListener { /* go to profile */ }
        view.followBtn.setOnClickListener { follow(view, id, user.username) }
        view.unfollowBtn.setOnClickListener { unfollow(view, id) }
    }

    override fun getItemCount(): Int {
        return users.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun updateAll(u: MutableList<User>){
        users = u
        notifyDataSetChanged()
    }

    private fun followedManger(view: ViewHolder, id: Int){
        if(users[id].isFollowed){
            view.followBtn.visibility = View.INVISIBLE
            view.unfollowBtn.visibility = View.VISIBLE
            return
        }
        view.followBtn.visibility = View.VISIBLE
        view.unfollowBtn.visibility = View.INVISIBLE
    }

    private fun follow(view: ViewHolder, id: Int, followedUsername: String){
        UserController(view.context).follow(followedUsername) { hasFollowed: Boolean, msg: String ->
            if(hasFollowed){
                users[id].isFollowed = true
                followedManger(view, id)
                return@follow
            }

            MaterialAlertDialogBuilder(view.context)
                .setTitle("Error")
                .setMessage(msg)
                .setNegativeButton("Ok") {dialog, _ -> dialog.dismiss()}
                .show()
        }
    }

    private fun unfollow(view: ViewHolder, id: Int){
        users[id].id?.let {
            UserController(view.context).unfollow(it) { hasUnfollowed: Boolean, msg: String ->
              if(hasUnfollowed){
                  users[id].isFollowed = false
                  followedManger(view, id)
                  return@unfollow
              }

              MaterialAlertDialogBuilder(view.context)
                  .setTitle("Error")
                  .setMessage(msg)
                  .setNegativeButton("Ok") {dialog, _ -> dialog.dismiss()}
                  .show()
            }
        }
    }
}

class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
    val container: LinearLayout = view.findViewById(R.id.container)
    val usernameText: TextView = view.findViewById(R.id.username)
    val profilePicture: ImageView = view.findViewById(R.id.profile_picture)
    val followBtn: Button = view.findViewById(R.id.follow_btn)
    val unfollowBtn: Button = view.findViewById(R.id.unfollow_btn)
}