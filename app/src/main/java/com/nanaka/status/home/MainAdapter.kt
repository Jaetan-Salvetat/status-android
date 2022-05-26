package com.nanaka.status.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nanaka.status.R
import com.nanaka.status.models.User

class MainAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var users: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_main_adapter, parent, false)

        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(view: ViewHolder, id: Int) {
        val user = users[id]
        view.usernameText.text = user.username
        view.statusText.text = user.status?.name ?: "error"

        if(!user.profilePicture.isNullOrBlank()){
            Glide.with(view.context).load(user.profilePicture).into(view.profilePicture)
        }

        when(user.status?.type){
            "online" -> {
                view.statusColor.backgroundTintList = ColorStateList.valueOf(view.context.resources.getColor(R.color.online, null))
                view.statusText.setTextColor(view.context.resources.getColor(R.color.online, null))
            }
            "offline" -> {
                view.statusColor.backgroundTintList = ColorStateList.valueOf(view.context.resources.getColor(R.color.offline, null))
                view.statusText.setTextColor(view.context.resources.getColor(R.color.offline, null))
            }
            "away" -> {
                view.statusColor.backgroundTintList = ColorStateList.valueOf(view.context.resources.getColor(R.color.away, null))
                view.statusText.setTextColor(view.context.resources.getColor(R.color.away, null))
            }
        }


        view.container.setOnClickListener { /* go to profile */ }
    }

    override fun getItemCount(): Int {
        return users.count()
    }


    @SuppressLint("NotifyDataSetChanged")
    fun update(u: MutableList<User>){
        users = u
        notifyDataSetChanged()
    }
}

class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
    val container: LinearLayout = view.findViewById(R.id.container)
    val usernameText: TextView = view.findViewById(R.id.username)
    val statusText: TextView = view.findViewById(R.id.status)
    val statusColor: View = view.findViewById(R.id.status_color)
    val profilePicture: ImageView = view.findViewById(R.id.profile_picture)
}