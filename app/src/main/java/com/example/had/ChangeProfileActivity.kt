package com.example.had

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.had.databinding.ActivityChangeProfileBinding
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult


class ChangeProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileBinding
    private val OPEN_GALLERY = 1
    private val TAG = this.javaClass.simpleName

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result: ActivityResult ->binding.NewProfileImage.setImageURI(result.data?.data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getImageB.setOnClickListener {
            val intent = Intent(android.provider.MediaStore.Images.Media.CONTENT_TYPE)
            intent.setType("image/*")
            launcher.launch(intent)
        }
    }

    var launcher = registerForActivityResult(
        StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            Log.e(TAG, "result : $result")
            val intent = result.data
            Log.e(TAG, "intent : $intent")
            val uri = intent!!.data
            Log.e(TAG, "uri : $uri")
            binding.NewProfileImage.setImageURI(uri);

        }
    }

}