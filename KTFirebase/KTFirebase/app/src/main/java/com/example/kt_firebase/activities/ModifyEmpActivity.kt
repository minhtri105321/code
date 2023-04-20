package com.example.kt_firebase.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.kt_firebase.R
import com.example.kt_firebase.modules.Employees
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class ModifyEmpActivity : AppCompatActivity() {

    private lateinit var editId : TextView
    private lateinit var editName: TextView
    private lateinit var editAge: TextView
    private lateinit var editJob: TextView
    private lateinit var editBirthday: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var imageUser: ImageView
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify_emp)

        initView()
        setValuesToViews()
        btnUpdate.setOnClickListener{
            openUpdateDialog(intent.getStringExtra("empId").toString(), intent.getStringExtra("empName").toString())
        }

        btnDelete.setOnClickListener{
            deleteRecord(intent.getStringExtra("empId").toString())
        }
    }


    //lấy biến ánh xạ qua id
    private fun initView(){
        editId = findViewById(R.id.editID)
        editName = findViewById(R.id.editName)
        editAge = findViewById(R.id.editAge)
        editJob = findViewById(R.id.editJob)
        editBirthday = findViewById(R.id.editBirthday)
        btnDelete = findViewById(R.id.btnDelete)
        btnUpdate = findViewById(R.id.btnUpdate)
        imageUser = findViewById(R.id.imageUser)
    }

    // nhận giá trị thông qua intent
    private fun setValuesToViews(){
        editId.text = intent.getStringExtra("empId")
        editName.text = intent.getStringExtra("empName")
        editAge.text = intent.getStringExtra("empAge")
        editJob.text = intent.getStringExtra("empJob")
        editBirthday.text = intent.getStringExtra("empBirthday")
        val imageId = intent.getStringExtra("empId")
        storageRef = FirebaseStorage.getInstance().getReference("User/$imageId")



        // lấy ảnh qua database
        val localFile: File = File.createTempFile("tempfile", ".jpeg")
        storageRef.getFile(localFile).addOnSuccessListener {

            val bitmap: Bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            imageUser.setImageBitmap(bitmap)

        }

    }


    // Xóa người đang dùng
    private fun deleteRecord(id: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        dbRef.removeValue().addOnCompleteListener{
            Toast.makeText(this, "Xóa người dùng thành công", Toast.LENGTH_LONG).show()

            val intent =  Intent(this@ModifyEmpActivity, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{
            error -> Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }


    // Cập nhật lại người dùng
    private fun openUpdateDialog(empId: String, empName: String){
        val mdialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_user, null)

        mdialog.setView(mDialogView)

        val upName = mDialogView.findViewById<EditText>(R.id.upName)
        val upAge = mDialogView.findViewById<EditText>(R.id.upAge)
        val upJob = mDialogView.findViewById<EditText>(R.id.upJob)
        val upBirthday = mDialogView.findViewById<EditText>(R.id.upBirthDay)

        val btnUpUser = mDialogView.findViewById<Button>(R.id.UpdateUser)

        upName.setText(intent.getStringExtra("empName"))
        upAge.setText(intent.getStringExtra("empAge"))
        upJob.setText(intent.getStringExtra("empJob"))
        upBirthday.setText(intent.getStringExtra("empBirthday"))

        mdialog.setTitle("Cập nhật thông tin của $empName")

        val alertDialog = mdialog.create()
        alertDialog.show()

        btnUpUser.setOnClickListener{
            updateUser(empId, upName.text.toString(), upAge.text.toString(), upJob.text.toString(), upBirthday.text.toString())

        Toast.makeText(applicationContext, "Thông tin đã được cập nhật", Toast.LENGTH_LONG).show()

        editName.text = upName.text.toString()
        editAge.text = upAge.text.toString()
        editJob.text = upJob.text.toString()
        editBirthday.text = upBirthday.text.toString()

        alertDialog.dismiss()
        }
    }


    private fun updateUser(id: String, name: String, age: String, job: String, bday: String){
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val emp = Employees(id, name, age, job, bday)
        dbRef.setValue(emp)
    }
}