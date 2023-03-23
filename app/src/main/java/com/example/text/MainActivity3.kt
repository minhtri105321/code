package com.example.text

import android.content.ContentValues
import android.content.DialogInterface
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.text.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    lateinit var db: SQLiteDatabase
    lateinit var rs : Cursor
    lateinit var rc : Cursor
    lateinit var adapter: SimpleCursorAdapter
    lateinit var binding: ActivityMain3Binding
    var regex  = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    var a: Int =-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var helper = data(applicationContext)
        db = helper.readableDatabase
        rs = db.rawQuery("SELECT * FROM TUHOC LIMIT 20",null)




        //6. code phần lvFull
        adapter = SimpleCursorAdapter(
            applicationContext,android.R.layout.simple_expandable_list_item_2,rs,
            arrayOf("email","matkhau"), intArrayOf(android.R.id.text1,android.R.id.text2),0
        )
        binding.lvFull.adapter = adapter

        binding.lvFull.setOnItemClickListener(
            AdapterView.OnItemClickListener { adapterView, view, i, l ->
                binding.edtemail.setText(rs.getString(1))
                binding.edtmatkhau.setText(rs.getString(2))
                 a = rs.getString(0).toInt()

            }
        )
        //code cho phần tìm kiếm nội dung
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            //ctrl + i
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                rs = db.rawQuery("SELECT * FROM TUHOC WHERE email LIKE '${newText}%' ",
                    null)
                adapter.changeCursor(rs)
                return true
            }
        })

        //8. code cho nút insert
        binding.btnInsert.setOnClickListener {
            var cv =ContentValues()
            var b = binding.edtemail.text.toString()
            var c = binding.edtmatkhau.text.toString()
            cv.put("email",binding.edtemail.text.toString() )
            cv.put("matkhau", binding.edtmatkhau.text.toString())
            if(b.matches(regex)) {
                rc = db.rawQuery("SELECT * FROM TUHOC WHERE email = '$b'", null)
                if(rc.getCount()>0) {
                    Toast.makeText(this, "Email da ton tai", Toast.LENGTH_SHORT).show()
                }else {
                    db.insert("TUHOC", null, cv)
                    Toast.makeText(this, "đăng ký thành công",Toast.LENGTH_SHORT).show()
                    rs.requery()
                    adapter.notifyDataSetChanged()
                    binding.edtemail.setText("")
                    binding.edtmatkhau.setText("")
                }} else {
                Toast.makeText(this, "Email sai", Toast.LENGTH_SHORT).show()

            }


        }

        //9. code cho nút update
        binding.btnUpdate.setOnClickListener {
            var cv = ContentValues()
            cv.put("email", binding.edtemail.text.toString())
            cv.put("matkhau", binding.edtmatkhau.text.toString())
            db.update("TUHOC",cv,"_id=?", arrayOf(a.toString()))
            rs.requery()
            adapter.notifyDataSetChanged()

            var ad = AlertDialog.Builder(this)
            ad.setTitle("Update record")
            ad.setMessage("Update thành công")
            ad.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

            })
            ad.show()

        }

        //10. code nút clear
        binding.btnClear.setOnClickListener {
            binding.edtemail.setText("")
            binding.edtmatkhau.setText("")
            binding.edtemail.requestFocus()
        }

        //11.code delete
        binding.btnDelete.setOnClickListener {
            var ad = AlertDialog.Builder(this)
            ad.setTitle("Bạn có muốn xóa không")

            ad.setPositiveButton("Có") { dialog, which ->
                db.delete("TUHOC","_id=?", arrayOf(rs.getString(0)))
                rs.requery()
                adapter.notifyDataSetChanged()

            }
            ad.setNegativeButton("Không" ) { dialog, which ->

            }

            val dialog = ad.create()
            dialog.show()
        }



    }





}