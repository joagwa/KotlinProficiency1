package com.example.kotlinproficiency1.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import com.beust.klaxon.Klaxon
import com.example.kotlinproficiency1.Adapters.FactAdapter
import com.example.kotlinproficiency1.Models.Container
import com.example.kotlinproficiency1.Models.Fact
import com.example.kotlinproficiency1.Models.Url
import com.example.kotlinproficiency1.R
import com.example.kotlinproficiency1.Services.FactService
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var factList : ArrayList<Fact> = ArrayList()
    lateinit var adapter : FactAdapter
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factListView = findViewById<ListView>(R.id.factListView)
        adapter = FactAdapter(this, R.layout.item_list_content, factList)
        factListView.adapter = adapter
        callUpdatelist()
        val refreshButton = findViewById<Button>(R.id.refresh_button)

        refreshButton.setOnClickListener{ updateList() }
    }

        //This method is more elegate, separating the login into a services class, but it didnt update the UI, so moved to callUpdateList method
        fun updateList() {
            FactService().getFacts {
                this@MainActivity.runOnUiThread {
                    factListView.adapter = FactAdapter(
                        this@MainActivity,
                        R.layout.item_list_content,
                        factList
                    )
                    adapter.notifyDataSetChanged()
                }
            }
        }
        fun callUpdatelist(){
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
                    factList = container?.rows ?: ArrayList<Fact>()
                    this@MainActivity.runOnUiThread {
                        factListView.adapter = FactAdapter(
                            this@MainActivity,
                            R.layout.item_list_content,
                            factList
                        )
                        adapter.notifyDataSetChanged()   }
                }
            })
        }
}
