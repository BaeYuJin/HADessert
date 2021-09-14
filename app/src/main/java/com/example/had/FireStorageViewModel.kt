package com.example.had

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore.Images.Media.getBitmap
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class FireStorageViewModel : ViewModel() {
    private val TAG = this.javaClass.simpleName
    private val storage = Firebase.storage
    val user = Firebase.auth.currentUser
    val storageRef = storage.reference
    val imageRefChild = storageRef.child("profileImages/${user?.uid}.jpg")
    val imageRefUrl = storage.getReferenceFromUrl("gs://hadessert-c6192.appspot.com/profileImages/${user?.uid}.jpg")
    lateinit var result : Bitmap

    fun getImageRef(){
        imageRefUrl?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            result = bmp
        }?.addOnFailureListener {
            // Failed to download the image
        }
    }

    public fun setImage(view: ImageView){
        view?.setImageBitmap(result)
    }
}