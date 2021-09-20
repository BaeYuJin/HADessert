package com.example.had.activity

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.had.AppInfoActivity
import com.example.had.FireStorageViewModel
import com.example.had.PreferenceUtil
import com.example.had.PreferenceUtil.setImage
import com.example.had.databinding.ActivityProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

//import com.google.firebase.storage.ktx.storage

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    val user = Firebase.auth.currentUser
    private val viewModel: FireStorageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storage = Firebase.storage
        val storageRef = storage.reference
        val storageReference = Firebase.storage.reference
        val database = Firebase.database
        val reference = database.getReference("Users")


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

        if (user != null) {
            reference.child(user.uid).child("name").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val name = dataSnapshot.value.toString()
                    binding.userName.text = name
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //viewModel.setImage(this, binding.profileImage) //onResume로 옮김
        binding.changeProfile.setOnClickListener{
            startActivity(Intent(this, ChangeProfileActivity::class.java))
        }

        binding.likedStore.setOnClickListener{
            startActivity(Intent(this, StarActivity::class.java))
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
        }
    }

    override fun onResume() {
        super.onResume()
        binding.profileImage.setImageBitmap(PreferenceUtil.StringtoBitmap(this))
    }

}