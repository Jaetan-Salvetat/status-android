package com.nanaka.status.services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlin.system.exitProcess

class Navigation {
    companion object {
        private var history: ArrayList<Fragment> = arrayListOf()
        private var mainLayout: Int = 0
        private lateinit var supportFragmentManager: FragmentManager

        fun init(parent: FragmentManager, mainFragment: Fragment, layout: Int){
            supportFragmentManager = parent
            mainLayout = layout
            push(mainFragment)
        }

        fun push(fragment: Fragment){
            history.add(fragment)
            navigate()
        }

        fun pushReplacement(fragment: Fragment){
            history.removeLastOrNull()
            history.add(fragment)
            navigate()
        }

        fun pushClear(fragment: Fragment){
            history.clear()
            history.add(fragment)
            navigate()
        }

        fun back() {
            if(history.isNotEmpty() && history.count() > 1) {
                history.removeLast()
            }
            else{
                exitProcess(0)
            }
            navigate()
        }

        private fun navigate(){
            supportFragmentManager
                .beginTransaction()
                .replace(mainLayout, history.last())
                .commit()
        }
    }
}