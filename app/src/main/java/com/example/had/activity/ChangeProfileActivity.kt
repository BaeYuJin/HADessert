package com.example.had.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.had.databinding.ActivityChangeProfileBinding
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult


class ChangeProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileBinding
    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getImageB.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            intent.putExtra("crop", true)
            launcher.launch(intent)
        }
    }

    var launcher = registerForActivityResult(
        StartActivityForResult()
    ) {
        result ->
        if (result.resultCode == RESULT_OK) {
            Log.e(TAG, "result : $result")
            val intent = result.data
            Log.e(TAG, "intent : $intent")
            val uri = intent!!.data
            Log.e(TAG, "uri : $uri")
            binding.NewProfileImage.setImageURI(uri)
        }
    }
}