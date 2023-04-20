package com.example.kt_firebase.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import com.example.kt_firebase.R
import com.example.kt_firebase.databinding.ActivityInputTypeBinding


class InputType : AppCompatActivity() {
    private lateinit var binding: ActivityInputTypeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputTypeBinding.inflate(layoutInflater)




        binding.editName.doOnTextChanged { text, start, before, count ->
            if(text!!.length > 10){
                binding.textInputLayoutName.error = "No more!"
            }else {
                binding.textInputLayoutName.error = null
            }
        }

        setContentView(binding.root)
    }
}