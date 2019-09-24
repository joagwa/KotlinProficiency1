package com.example.kotlinproficiency1.Models

class Fact(){
    var title: String = ""
    var description: String = ""
    var imageHref: String = ""

}

class Container(){
    lateinit var title: String
    lateinit var rows: ArrayList<Fact>
}