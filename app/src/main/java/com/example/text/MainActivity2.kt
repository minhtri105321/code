package com.example.text

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.text.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var db: SQLiteDatabase
    lateinit var  rc: Cursor
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        var helper = data(applicationContext)
        db = helper.readableDatabase

        binding.dangky.setOnClickListener {
            val inten = Intent(this, MainActivity::class.java)
            startActivity(inten)
        }
        binding.dangnhap.setOnClickListener {
            var cv = ContentValues()
            var b = binding.email.text.toString()
            var c = binding.matkhau.text.toString()
            cv.put("email", binding.email.text.toString())
            cv.put("matkhau", binding.matkhau.text.toString())
            rc = db.rawQuery("SELECT * FROM TUHOC WHERE email = '$b' and  matkhau ='$c'" , null)

            if (b == "") {
                Toast.makeText(this, "vui lòng nhập email", Toast.LENGTH_SHORT).show()
            } else if (c == "") {
                Toast.makeText(this, "vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show()
            } else if (rc.getCount()>0) {
                Toast.makeText(this,"Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                if (b=="pham21555@gmail.com") {
                    val intent1 = Intent(this, MainActivity3::class.java)
                    startActivity(intent1)
                }
            }
            else {
                Toast.makeText(this,"Email hoặc mật khẩu sai", Toast.LENGTH_SHORT).show()

            }


        }
    }
}