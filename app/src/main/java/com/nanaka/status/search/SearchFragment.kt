package com.nanaka.status.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputLayout
import com.nanaka.status.R
import com.nanaka.status.controllers.UserController
import com.nanaka.status.services.Navigation


class SearchFragment : Fragment() {
    private lateinit var toolBar: MaterialToolbar
    private lateinit var usersList: RecyclerView
    private lateinit var adapter: MainAdapter
    private lateinit var searchInput: TextInputLayout
    private lateinit var noUsersText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        usersList = view.findViewById(R.id.main_adapter)
        toolBar = view.findViewById(R.id.tool_bar)
        searchInput = view.findViewById(R.id.search_input_layout)
        noUsersText = view.findViewById(R.id.no_users_text)
        adapter = MainAdapter()
        usersList.adapter = adapter
        usersList.layoutManager = LinearLayoutManager(context)


        searchInput.requestFocus()
        noUsersTextManager(true)
        ViewCompat.setNestedScrollingEnabled(usersList, true)

        toolBar.setNavigationOnClickListener { Navigation.back() }
        searchInput.setEndIconOnClickListener { searchUsers() }

        return view
    }

    private fun searchUsers(){
        searchInput.editText?.clearFocus()
        context?.let { it1 ->
            UserController(it1).searchUsers(searchInput.editText?.text.toString()) {
                adapter.updateAll(it)
                noUsersTextManager(it.isEmpty())
                Log.d("XXXXXXXXXXXXXXXXXX", "it.isEmpty(): ${it.isEmpty()}")
            }
        }
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
}