package com.nanaka.status.services.http

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.net.URL


class HttpRequest {
    companion object {
        private lateinit var baseUrl: String
        private val client = OkHttpClient()

        fun init(_baseUrl: String){
            baseUrl = _baseUrl
        }


        fun get(path: String, callback: (JSONObject?, String?) -> Unit){
            val url = URL(baseUrl + path)

            val request = Request.Builder()
                .url(url)
                .get()
                .build()

            Thread {
                try {
                    val req = client.newCall(request).execute()
                    val res = req.body?.string()
                    if(res != null){
                        val data = JSONObject(res)
                        val msg = data.get("msg") as String

                        if(msg == "success"){
                            callback(data, msg)
                            return@Thread
                        }

                        callback(null, msg)
                        return@Thread
                    }

                    callback(null, null)
                }catch (e: Exception){
                    callback(null, null)
                }
            }.start()
        }

        fun post(path: String, body: String, contentType: ContentType, callback: (JSONObject?, String?) -> Unit){
            val url = URL(baseUrl + path)

            val content = getContentType(body, contentType)

            val request = Request.Builder()
                .url(url)
                .post(content)
                .build()

            Thread {
                try {
                    val res = client.newCall(request).execute()
                    val input = res.body?.string()
                    if(input != null) {
                        val data = JSONObject(input)
                        val msg = data.get("msg") as String
                        if(msg == "success"){
                            callback(data, msg)
                            return@Thread
                        }

                        callback(null, msg)
                        return@Thread
                    }

                    callback(null, null)
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback(null, null)
                }
            }.start()
        }

        private fun getContentType(body: String, contentType: ContentType) : RequestBody {
            return when(contentType){
                ContentType.FormData -> body.toRequestBody("application/form-data".toMediaTypeOrNull())
                ContentType.Json -> body.toRequestBody("application/json".toMediaTypeOrNull())
                else -> body.toRequestBody(null)
            }
        }
    }
}