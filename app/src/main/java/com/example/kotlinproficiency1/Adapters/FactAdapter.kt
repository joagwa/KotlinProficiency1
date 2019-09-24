package com.example.kotlinproficiency1.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.kotlinproficiency1.Models.Fact
import com.example.kotlinproficiency1.R

class FactAdapter(private val context: Context,
                  private val view: Int,
                  private val dataSource:ArrayList<Fact>) : BaseAdapter(){
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(p0: Int): Any {
        return dataSource[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(view,parent, false)

        val titleTextView = rowView.findViewById(R.id.title) as TextView
        val descriptionTextView = rowView.findViewById(R.id.description) as TextView
        val imageView = rowView.findViewById(R.id.image) as ImageView

        val fact = getItem(p0) as Fact
        titleTextView.text = fact.title
        descriptionTextView.text = fact.description
        Glide.with(this.context).load(fact.imageHref).into(imageView)
        return rowView
    }
}