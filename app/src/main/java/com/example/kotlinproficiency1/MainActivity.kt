package com.example.kotlinproficiency1

import android.app.Application
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.MainThread
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var factList : ArrayList<Fact> = ArrayList()
    private val client = OkHttpClient()
    lateinit var adapter : FactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factListView = findViewById<ListView>(R.id.factListView)
        adapter = FactAdapter(this, R.layout.item_list_content, factList)
        factListView.adapter = adapter
        updateList()
        val refreshButton = findViewById<Button>(R.id.refresh_button)

        refreshButton.setOnClickListener{ run() }
    }

    fun run(){
        val request = Request.Builder()
            .url(Url.BASE_URL+Url.URL)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                val container = Klaxon().parse<Container>(body ?: "")
                factList = container?.rows ?: ArrayList<Fact>()
                this@MainActivity.runOnUiThread {
                    factListView.adapter = FactAdapter(this@MainActivity, R.layout.item_list_content, factList)
                    adapter.notifyDataSetChanged()  }
            }
        })
    }
    fun updateList(){
       run()
    }
}
