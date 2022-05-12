package com.example.recyclerview

import android.content.Context
import android.graphics.Color
import android.graphics.Color.BLACK
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class recyclerview( private val tasklist: ArrayList<task>, val context:Context,private val dbh:sqlite): RecyclerView.Adapter<recyclerview.ViewHolder>() {

    var isdeleted=false
    class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val tv=view.findViewById<TextView>(R.id.tv)
        val description=view.findViewById<TextView>(R.id.description)
        val del=view.findViewById<ImageButton>(R.id.delete)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(context).inflate(R.layout.activity_rcvc,parent,false)
        val r1=v.findViewById<LinearLayout>(R.id.rl1)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.description.text=tasklist[position].name
        holder.tv.text=tasklist[position].description
        holder.del.setOnClickListener {
            dbh.delete(holder.tv.text.toString(), holder.description.text.toString())
            isdeleted=true
        }

    }

    override fun getItemCount(): Int {
        return tasklist.size
    }


}