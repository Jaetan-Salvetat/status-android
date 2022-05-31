package com.nanaka.status.controllers

import android.app.Activity
import android.content.Context
import com.nanaka.status.services.LocalStorage
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URI

class WsController(val context: Context) {
    private var socket: Socket = IO.socket(URI(
        "https://status.jaetan.fr" +
                "?username=${LocalStorage.username}" +
                "&token=${LocalStorage.username}"
    ))
    init {
        socket.connect()
    }

    fun editStatusListener(callback: Emitter.Listener){
        socket.on("edit-status", callback)
    }

    fun editStatus(statusType: String, statusName: String){
        val body = JSONObject()
        body.put("username", LocalStorage.username)
        body.put("name", statusName)
        body.put("type", statusType)

        socket.emit("edit-status", body.toString())
    }
}