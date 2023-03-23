package com.example.text

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class data(context: Context): SQLiteOpenHelper(context, "tuhocdb", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE TUHOC(_id integer primary key autoincrement, email TEXT, matkhau TEXT)")
        db?.execSQL("Insert into TUHOC(email,matkhau) values ('pham21555@gmail.com','1')")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}