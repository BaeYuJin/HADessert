package com.example.had.activity

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.had.AppInfoActivity
import com.example.had.PreferenceUtil
import com.example.had.databinding.ActivityProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

//import com.google.firebase.storage.ktx.storage

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storage = Firebase.storage
        val storageRef = storage.reference
        val storageReference = Firebase.storage.reference

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

        val pathReference = storageRef.child("profileImages/${user?.uid}.jpg")
        val gsReference = storage.getReferenceFromUrl("gs://hadessert-c6192.appspot.com/profileImages/${user?.uid}.jpg")

        //displayImageRef(gsReference, binding.imageView12)

        //Glide.with(this).load(gsReference).into(binding.profileImage)


        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeProfile.setOnClickListener{
            startActivity(Intent(this, ChangeProfileActivity::class.java))
        }

        binding.likedStore.setOnClickListener{
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.logout.setOnClickListener{
            Firebase.auth.signOut()
            PreferenceUtil.setAutoLogin(this, "false")
            Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    private fun displayImageRef(imageRef: StorageReference?, view: ImageView) {
        imageRef?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            view.setImageBitmap(bmp)
        }?.addOnFailureListener {
            // Failed to download the image
        } }

}