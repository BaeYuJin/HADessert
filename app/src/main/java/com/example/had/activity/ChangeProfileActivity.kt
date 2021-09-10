package com.example.had.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.had.databinding.ActivityChangeProfileBinding
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream


class ChangeProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileBinding
    private val TAG = this.javaClass.simpleName
    private val storage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //getFirebaseImage()

        binding.getImageB.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            intent.putExtra("crop", true)
            launcher.launch(intent)
        }

        binding.saveChangeProfileB.setOnClickListener{
            uploadProfileImage()
            finish()
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

    private fun uploadProfileImage() {
        //firebase에 사진 업로드
        val storageRef = storage.reference
        val profileImageRef = storageRef.child("profileImages/mountains.jpg")
        binding.NewProfileImage.isDrawingCacheEnabled = true
        binding.NewProfileImage.buildDrawingCache()
        val bitmap = (binding.NewProfileImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = profileImageRef.putBytes(data)
        uploadTask.addOnFailureListener {

        }.addOnSuccessListener { taskSnapshot -> }

    }

    private fun getFirebaseImage(){
        val storageRef = storage.reference
        val storageReference = Firebase.storage.reference

        val pathReference = storageRef.child("profileImages/mountains.jpg")
        val gsReference = storage.getReferenceFromUrl("gs://hadessert-c6192.appspot.com/profileImages/mountains.jpg")

        Glide.with(this).load(gsReference).into(binding.NewProfileImage)
    }

}