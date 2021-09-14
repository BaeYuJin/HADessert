package com.example.had.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import com.example.had.databinding.ActivityChangeProfileBinding
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream
import java.io.File
import android.widget.Toast

import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.android.gms.tasks.OnSuccessListener

import com.google.firebase.storage.StorageReference

import com.google.firebase.storage.FirebaseStorage





class ChangeProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeProfileBinding
    private val TAG = this.javaClass.simpleName
    private val storage = Firebase.storage
    val user = Firebase.auth.currentUser
    val storageRef = storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getFirebaseImage()
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }



        binding.getImageB.setOnClickListener { // 이미지 불러오기

            //val ref = storageRef.child("profileImages/${user?.uid}.jpg")
            /*var file =
            var uploadTask = ref.putFile(file)

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    getFirebaseImage()
                } else {
                    // Handle failures
                    // ...
                }
            }*/

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
        //val storageRef = storage.reference
        val profileImageRef = storageRef.child("profileImages/${user?.uid}.jpg")
        binding.NewProfileImage.isDrawingCacheEnabled = true
        binding.NewProfileImage.buildDrawingCache()
        val bitmap = (binding.NewProfileImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val profileUpdates = userProfileChangeRequest {
            displayName = "Jane Q. User"
            if (user != null) {
                photoUri = Uri.parse("profileImages/${user.uid}.jpg")
            }
        }
        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }

        var uploadTask = profileImageRef.putBytes(data)
        uploadTask.addOnFailureListener {

        }.addOnSuccessListener { taskSnapshot -> }

    }

    private fun getFirebaseImage(){
        val storageRef = storage.reference
        val imageRefChild = storageRef.child("profileImages/${user?.uid}.jpg")
        val imageRefUrl = storage.getReferenceFromUrl("gs://hadessert-c6192.appspot.com/profileImages/${user?.uid}.jpg")
        displayImageRef(imageRefUrl, binding.NewProfileImage)
    }

    private fun displayImageRef(imageRef: StorageReference?, view: ImageView) {
        imageRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            view.setImageBitmap(bmp)
        }?.addOnFailureListener {
            // Failed to download the image
        }
    }


}
