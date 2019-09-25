package com.example.kotlinproficiency1.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.beust.klaxon.Klaxon
import com.example.kotlinproficiency1.Adapters.FactAdapter
import com.example.kotlinproficiency1.Models.Container
import com.example.kotlinproficiency1.Models.Fact
import com.example.kotlinproficiency1.Models.Url
import com.example.kotlinproficiency1.R
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import android.view.animation.AlphaAnimation
import android.view.View


class MainActivity : AppCompatActivity() {
    var factList : ArrayList<Fact> = ArrayList()
    lateinit var adapter : FactAdapter
    private val client = OkHttpClient()
    var inAnimation: AlphaAnimation? = null
    var outAnimation: AlphaAnimation? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var IsBusy : Boolean
        adapter = FactAdapter(this, R.layout.item_list_content, factList)
        factListView.adapter = adapter
        callUpdateList()
        refresh_button.setOnClickListener{ callUpdateList() }
    }

        //This method is more elegate, separating the login into a services class, but it didnt update the UI, so moved to callUpdateList method
    /**
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
    **/

        private fun callUpdateList(){
            refresh_button.isEnabled = false
            inAnimation = AlphaAnimation(0f,1f)
            inAnimation?.duration = 200
            progressBarHolder.animation = inAnimation
            progressBarHolder.visibility = View.VISIBLE
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
                        adapter.notifyDataSetChanged()
                        outAnimation = AlphaAnimation(1f,0f)
                        outAnimation?.duration = 200
                        progressBarHolder.animation = outAnimation
                        progressBarHolder.visibility = View.GONE
                        refresh_button.isEnabled = true}

                }
            })
        }
}
