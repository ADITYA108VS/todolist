package com.example.recyclerview

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {
    private lateinit var rcv:RecyclerView
    private lateinit var dbh: sqlite
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val todolist= ArrayList<task>()
        val descriptione=findViewById<EditText>(R.id.descriptione)
        val rb=findViewById<FloatingActionButton>(R.id.rootb)
        val add=findViewById<FloatingActionButton>(R.id.add1)
        val edt=findViewById<FloatingActionButton>(R.id.edit1)
        var isvisible:Boolean=false
       dbh=sqlite(this,"todolist",1)
        rcv=findViewById<RecyclerView>(R.id.rcv)
       printall(dbh)
        rcv.layoutManager=GridLayoutManager(this,2)
        rb.setOnClickListener{
           rb.animate().apply {
               rotation(180f)
               duration=500
           }.withEndAction{
               rb.animate().apply {
                   rotation(180f)
                   duration=500
               }
           }.start()
            if(!isvisible){
                isvisible=true
                add.visibility=View.VISIBLE
                edt.visibility=View.VISIBLE
            }
            else
            {
                isvisible=false
                add.visibility=View.GONE
                edt.visibility=View.GONE
            }
        }
        add.setOnClickListener {
            dg()

        }
        GlobalScope.launch(Dispatchers.Default){
            while(true)
            {
                if(dbh.isdeleted){
                    GlobalScope.launch(Dispatchers.Main) {
                        printall(dbh)
                    }
                    dbh.isdeleted=false
                }


            }

        }


    }
     fun printall(dbh:sqlite){
        var data:ArrayList<task>?=null
        data=dbh.getall()
        val adapter=recyclerview(data,this,dbh)
        rcv.adapter=adapter
    }
    private fun dg()
    {
        val builder=AlertDialog.Builder(this)
        val view=layoutInflater.inflate(R.layout.addnp,null)
                builder.setView(view)
            .setPositiveButton("ADD",
            DialogInterface.OnClickListener{dialogInterface, id ->
                val name=view.findViewById<EditText>(R.id.namef)
                val description=view.findViewById<EditText>(R.id.description)
                Log.d("entering","${name.text.toString()}+${description.text.toString()}")
                if (name.text.isNotEmpty() && description.text.isNotEmpty()) {
                    dbh.add(name.text.toString(), description.text.toString())
                }
                printall(dbh)
            })
        builder.create()
        builder.show()
    }
    private fun scrollto(){

    }
    override fun onDestroy() {
        dbh.stop()
        super.onDestroy()
    }

}
