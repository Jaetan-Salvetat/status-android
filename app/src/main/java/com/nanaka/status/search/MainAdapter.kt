package com.nanaka.status.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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

        if(!user.profilePicture.isNullOrBlank()){
            Glide.with(view.context).load(user.profilePicture).into(view.profilePicture)
        }



        view.container.setOnClickListener { /* go to profile */ }
        view.followBtn.setOnClickListener { follow(view, user.username) }
    }

    override fun getItemCount(): Int {
        return users.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun update(u: MutableList<User>){
        users = u
        notifyDataSetChanged()
    }


    private fun follow(view: ViewHolder, followedUsername: String){
        UserController(view.context).follow(followedUsername) { hasFollowed: Boolean, msg: String ->
            if(hasFollowed){
                view.btnContainer.removeAllViewsInLayout()
                return@follow
            }

            Snackbar.make(view.container, msg, Snackbar.LENGTH_LONG).show()
        }
    }
}

class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
    val container: LinearLayout = view.findViewById(R.id.container)
    val btnContainer: LinearLayout = view.findViewById(R.id.btn_container)
    val usernameText: TextView = view.findViewById(R.id.username)
    val profilePicture: ImageView = view.findViewById(R.id.profile_picture)
    val followBtn: Button = view.findViewById(R.id.follow_btn)
}