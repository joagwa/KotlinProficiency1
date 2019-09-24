package com.example.kotlinproficiency1.Services

import com.beust.klaxon.Klaxon
import com.example.kotlinproficiency1.Models.Container
import com.example.kotlinproficiency1.Models.Fact
import com.example.kotlinproficiency1.Models.Url
import okhttp3.*
import java.io.IOException

class FactService{
    private val client = OkHttpClient()

    /**
     * calls Facts service, returns a list of Facts, by way of updating through the callback method to the list.
     */
    fun getFacts (callback: (factList: ArrayList<Fact>) -> Unit){
        val request = Request.Builder()
            .url(Url.BASE_URL + Url.URL)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                print(e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val container = Klaxon().parse<Container>(body ?: "")
                callback.invoke(container?.rows ?: ArrayList<Fact>())
            }
        })
    }
}