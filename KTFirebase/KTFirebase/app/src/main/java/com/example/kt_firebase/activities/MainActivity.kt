package com.example.kt_firebase.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kt_firebase.Insert_image
import com.example.kt_firebase.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        database = Firebase.database.reference

//        binding.textView.text = "Nguyen Van Vinh Nguyen"
//        database.child("Mon").setValue("Nguỷn")
        binding.btnInsert.setOnClickListener{
            val intent = Intent(this@MainActivity, InsertDataActivity::class.java)
            startActivity(intent)
        }

        binding.btnFetch.setOnClickListener{
            val intent = Intent(this@MainActivity, FetchingActivity::class.java)
            startActivity(intent)
        }

        binding.btnToImage.setOnClickListener{
            val intent = Intent(this@MainActivity, Insert_image::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)

//        val txtMonHoc = findViewById<TextView>(R.id.txtMonHoc)
//
//        val txtDanhSach = findViewById<TextView>(R.id.txtKhoaHoc)
//        database.child("MonHoc").setValue("Lập trình Android")

//        database.child("DanhSachKhoaHoc").push().setValue("Lập trình ANDROID")
//        database.child("DanhSachKhoaHoc").push().setValue("Lập trình IOS")
//        database.child("DanhSachKhoaHoc").push().setValue("Lập trình PHP")
//        database.child("DanhSachKhoaHoc").push().setValue("Lập trình PYTHON")



//        database.child("MonHoc").addValueEventListener(object : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var tenMonhoc: String = snapshot.getValue().toString()
//                txtMonHoc.text = tenMonhoc
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//
//        database.child("DanhSachKhoaHoc").addChildEventListener(object : ChildEventListener{
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                var ten: String = snapshot.getValue().toString()
//                txtDanhSach.append(ten + "\n")
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

}