package com.example.kt_firebase

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kt_firebase.databinding.ActivityInsertImageBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.*

class Insert_image : AppCompatActivity() {
    private lateinit var binding: ActivityInsertImageBinding
    private lateinit var imageUri: Uri
    private lateinit var storageReference: StorageReference
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertImageBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        binding.btnChooseImage.setOnClickListener(View.OnClickListener { selectImage() })
        binding.btnUploadImage.setOnClickListener(View.OnClickListener { uploadImage() })
    }


    //upload lên database
    private fun uploadImage() {
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading File....")
        progressDialog.show()
        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA)
        val now = Date()
        val fileName = formatter.format(now)
        storageReference = FirebaseStorage.getInstance().getReference("User/$fileName")
        storageReference.putFile(imageUri!!)
            .addOnSuccessListener {
                binding.imageView.setImageURI(null)
                Toast.makeText(this@Insert_image, "Successfully Uploaded", Toast.LENGTH_SHORT)
                    .show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }.addOnFailureListener {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this@Insert_image, "Failed to Upload", Toast.LENGTH_SHORT).show()
            }
    }



    // Chọn ảnh từ file trong điện thoại thông qua drive
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 100)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && data != null && data.data != null) {
            imageUri = data.data!!
            binding.imageView.setImageURI(imageUri)
        }
    }
}