package com.nanaka.status.misc.http

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



        fun post(path: String, body: String, contentType: ContentType, callback: (JSONObject?, String?) -> Unit){
            val url = URL(baseUrl + path)

            val content: RequestBody = getContentType(body, contentType)

            val request: Request = Request.Builder()
                .url(url)
                .post(content)
                .build()

            Thread {
                try {
                    val res = client.newCall(request).execute()
                    val input = res.body?.string()

                    if(input != null) {
                        try {
                            callback(JSONObject(input), null)

                        }catch (e: Exception){
                            callback(null, input)
                        }


                        return@Thread
                    }

                    callback(null, null)
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback(null, null)
                }
            }.start()
        }

        fun put(path: String){
            val url = URL(baseUrl + path)
        }

        fun delete(path: String){
            val url = URL(baseUrl + path)
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