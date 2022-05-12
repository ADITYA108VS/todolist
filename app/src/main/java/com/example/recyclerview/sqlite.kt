package com.example.recyclerview

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Long.getLong
import kotlin.math.log

class sqlite(context: MainActivity,private val DBNAME:String ,version:Int): SQLiteOpenHelper(context,DBNAME,null,version) {
    private val dbw=this.writableDatabase
    private val dbr=this.readableDatabase
    var isdeleted=false
    override fun onCreate(db: SQLiteDatabase?) {
        val query="CREATE TABLE $DBNAME  (NAME VARCHAR,DESCRIPTION VARCHAR)"
        db?.execSQL(query)

    }
    fun add(name:String,Description:String){
        val values=ContentValues()
        values.put("NAME",name.trim())
        values.put("DESCRIPTION",Description.trim())
        val x=dbw.insert(DBNAME,  null,values)
    }
    fun add(dbh:sqlite , name:String,Description:String){
        val values=ContentValues()
        values.put("NAME",name.trim())
        values.put("DESCRIPTION",Description.trim())
        val x=dbh.writableDatabase.insert(DBNAME,  null,values)
    }


    fun getall(): ArrayList<task> {
        val data=ArrayList<task>()
        val query="SELECT * FROM $DBNAME"
        val cursor=dbr.rawQuery(query,null)
        cursor!!.moveToFirst()
            while (cursor.moveToNext())
            {
                val name=cursor.getString(cursor.getColumnIndexOrThrow("NAME")).toString()
                val description=cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION")).toString()
                val obj:task=task(name)
                obj.description=description
                data.add(obj)
            }


        return data
    }

    fun delete(name:String,description:String ){

        //val query="DELETE FROM $DBNAME WHERE NAME='$description' AND DESCRIPTION='$name'"
        //val d= dbw.execSQL(query,null) as Boolean
        val d=dbw.delete(DBNAME,"NAME='$description' and DESCRIPTION='$name'",null)
        isdeleted=true

    }
    fun stop(){
        dbr.close()
        dbw.close()
    }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}