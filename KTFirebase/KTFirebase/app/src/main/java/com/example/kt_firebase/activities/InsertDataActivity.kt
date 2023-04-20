package com.example.kt_firebase.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.kt_firebase.modules.Employees
import com.example.kt_firebase.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class InsertDataActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etJob: EditText
    private lateinit var etBirthday: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnChoose: Button
    private lateinit var imageUri: Uri
    private lateinit var storageReference: StorageReference
    private lateinit var progressDialog: ProgressDialog
    private lateinit var dbreference: DatabaseReference
    private lateinit var imageUser: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_data)

        etName = findViewById(R.id.txtName)
        etAge = findViewById(R.id.txtAge)
        etJob = findViewById(R.id.txtJob)
        etBirthday = findViewById(R.id.txtBirthday)
        btnChoose = findViewById(R.id.btnChooseImage)
        btnAdd = findViewById(R.id.btnAddUser)
        imageUser = findViewById(R.id.imageUser)
        dbreference = FirebaseDatabase.getInstance().getReference("Users")

        btnChoose.setOnClickListener {
            selectImage()
        }

        btnAdd.setOnClickListener{
            saveEmployee()
//            Toast.makeText(this, "Đã nhấn", Toast.LENGTH_SHORT).show()
        }
    }

    fun selectImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && data.data != null) {
            imageUri = data.data!!
            imageUser.setImageURI(imageUri)
        }
    }

    fun saveEmployee(){

        val empName = etName.text.toString()
        val empAge = etAge.text.toString()
        val empJob = etJob.text.toString()
        val empBirthday = etBirthday.text.toString()

        if(empName.isEmpty()){
            etName.error = "Họ và tên không được bỏ trống"
        }else if(empAge.isEmpty()){
            etAge.error = "Tuổi không được bỏ trống"
        }else if(empJob.isEmpty()){
            etJob.error = "Ngành không được bỏ trống"
        }else if(empBirthday.isEmpty()){
            etBirthday.error = "Ngày sinh không được bỏ trống"
        }

        val empId = dbreference.push().key!!

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Đang thêm người dùng....")
        progressDialog.show()
//        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
//        val now = Date()
//        val fileName = formatter.format(now)
        storageReference = FirebaseStorage.getInstance().getReference("User/$empId")
        storageReference.putFile(imageUri!!)
//            .addOnSuccessListener {
//                binding.imageView.setImageURI(null)
//                Toast.makeText(this@Insert_image, "Successfully Uploaded", Toast.LENGTH_SHORT)
//                    .show()
//                if (progressDialog.isShowing) progressDialog.dismiss()
//            }.addOnFailureListener {
//                if (progressDialog.isShowing) progressDialog.dismiss()
//                Toast.makeText(this@Insert_image, "Failed to Upload", Toast.LENGTH_SHORT).show()
//            }


        val employee = Employees(empId, empName, empAge, empJob, empBirthday)
        if(!empName.isEmpty() && !empAge.isEmpty() && !empJob.isEmpty() && !empBirthday.isEmpty()){
            dbreference.child(empId).setValue(employee).addOnCompleteListener{
                etName.text.clear()
                etAge.text.clear()
                etJob.text.clear()
                etBirthday.text.clear()
                Toast.makeText(this@InsertDataActivity, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()

            }.addOnFailureListener{
                Toast.makeText(this@InsertDataActivity, "Thêm người dùng thất bại", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        }


    }
}