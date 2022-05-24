package com.nanaka.status.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nanaka.status.R
import com.nanaka.status.home.MainAdapter
import com.nanaka.status.services.LocalStorage


class SearchFragment : Fragment() {
    private lateinit var usersList: RecyclerView
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        usersList = view.findViewById(R.id.main_adapter)
        adapter = MainAdapter()

        usersList.adapter = adapter
        usersList.layoutManager = LinearLayoutManager(context)

        return view
    }
}