package com.nanaka.status.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nanaka.status.R
import com.nanaka.status.models.User

class MainAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var users: ArrayList<User> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_main_adapter, parent, false)

        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(view: ViewHolder, position: Int) {
        val id = position
    }

    override fun getItemCount(): Int {
        return users.count()
    }
}

class ViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {

}