package com.example.kotlinproficiency1
import android.os.AsyncTask
import com.beust.klaxon.Klaxon
import okhttp3.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
class FactService{


    fun GetFacts  (): List<Fact>?{
      ApiData.apiData(object: ApiData.Response{
          override fun data(data: List<Fact>, status:Boolean) : List<Fact>?{
              if (status){
                  return data
              }
              return null
          }
      })
        return null
    }
}
