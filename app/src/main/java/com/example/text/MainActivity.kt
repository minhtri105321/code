package com.example.text


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.text.databinding.ActivityMainBinding
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.*


class MainActivity : AppCompatActivity() {
    lateinit var db: SQLiteDatabase
    lateinit var  rc: Cursor
    var regex  = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.dangnhap.setOnClickListener{
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        var helper = data(applicationContext)
        db = helper.readableDatabase
        binding.dangky.setOnClickListener {
            var cv =ContentValues()
            var b = binding.email.text.toString()
            var c = binding.matkhau.text.toString()
            var d = binding.nlmatkhau.text.toString()
            cv.put("email",binding.email.text.toString() )
            cv.put("matkhau", binding.matkhau.text.toString())
            if(b == "") {
                Toast.makeText(this, "vui lòng nhập email",Toast.LENGTH_SHORT).show()
            }else
                if(c == "") {
                    Toast.makeText(this, "vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show()
                }else
                    if(d == "") {
                        Toast.makeText(this, "vui lòng nhập lại mật khẩu",Toast.LENGTH_SHORT).show()
                    }else
                        if(c!=d) {
                            Toast.makeText(this, "Nhập sai mật khẩu",Toast.LENGTH_SHORT).show()

                        }else
                            if(b.matches(regex)) {
                                rc = db.rawQuery("SELECT * FROM TUHOC WHERE email = '$b'", null)
                                if(rc.getCount()>0) {
                                    Toast.makeText(this, "Email da ton tai", Toast.LENGTH_SHORT).show()
                                }else {
                                    db.insert("TUHOC", null, cv)
                                    Toast.makeText(this, "đăng ký thành công",Toast.LENGTH_SHORT).show()

                                    binding.email.setText("")
                                    binding.matkhau.setText("")
                                    binding.nlmatkhau.setText("")
                                }} else {
                                Toast.makeText(this, "Email sai", Toast.LENGTH_SHORT).show()

                            }


        }
    }
}