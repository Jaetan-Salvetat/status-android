package com.nanaka.status.home

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nanaka.status.R
import com.nanaka.status.controllers.UserController
import com.nanaka.status.controllers.WsController
import com.nanaka.status.profile.Profile
import com.nanaka.status.search.SearchFragment
import com.nanaka.status.services.LocalStorage
import com.nanaka.status.services.Navigation
import io.socket.emitter.Emitter


class HomePageFragment : Fragment() {
    private lateinit var toolBar: MaterialToolbar
    private lateinit var usernameText: TextView
    private lateinit var noUsersText: TextView
    private lateinit var searchBtn: FloatingActionButton
    private lateinit var usersList: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var wsController: WsController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_page, container, false)

        toolBar = view.findViewById(R.id.tool_bar)
        usernameText = view.findViewById(R.id.username)
        usersList = view.findViewById(R.id.main_adapter)
        searchBtn = view.findViewById(R.id.go_to_search)
        noUsersText = view.findViewById(R.id.no_users_text)
        adapter = MainAdapter()
        wsController = context?.let { WsController(it) }!!
        usernameText.text = LocalStorage.username
        usersList.adapter = adapter
        usersList.layoutManager = LinearLayoutManager(context)


        wsController.editStatusListener(listener())
        ViewCompat.setNestedScrollingEnabled(usersList, true)
        findMyFollows()


        toolBar.setNavigationOnClickListener { Navigation.push(Profile()) }
        toolBar.setOnMenuItemClickListener { onMenuClick(it) }
        searchBtn.setOnClickListener { Navigation.push(SearchFragment()) }

        return view
    }

    private fun findMyFollows(){
        context?.let { UserController(it).findMyFollows { users ->
            adapter.update(users)
            noUsersTextManager(users.isEmpty())
        } }
    }

    private fun noUsersTextManager(userListIsEmpty: Boolean){
        if(userListIsEmpty){
            noUsersText.visibility = View.VISIBLE
            usersList.visibility = View.INVISIBLE
        }else{
            noUsersText.visibility = View.INVISIBLE
            usersList.visibility = View.VISIBLE
        }
    }

    private fun onMenuClick(menu: MenuItem) : Boolean {
        return when(menu.itemId){
            R.id.go_to_settings -> true
            else -> false
        }
    }

    private fun listener(): Emitter.Listener {
        return  Emitter.Listener {
            (context as Activity).runOnUiThread {
                Log.d("XXXXXXXXXXXXXXXXXXXX", it.toString())
            }
        }
    }
}