package com.example.had

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore.Images.Media.getBitmap
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.had.PreferenceUtil.BitmaptoString
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

    fun setImageFile(context : Context, view: ImageView){
        val user = Firebase.auth.currentUser
        val storageRef = storage.reference
        val imageRefChild = storageRef.child("profileImages/${user?.uid}.jpg")
        val imageRefUrl = storage.getReferenceFromUrl("gs://hadessert-c6192.appspot.com/profileImages/${user?.uid}.jpg")

        imageRefUrl?.getBytes(Long.MAX_VALUE)?.addOnSuccessListener {
            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
            BitmaptoString(context, bmp)
            view?.setImageBitmap(bmp)
        }?.addOnFailureListener {
            // Failed to download the image
        }
    }

    fun setImage(context: Context, view:ImageView){
        val bmp = PreferenceUtil.StringtoBitmap(context)
        view?.setImageBitmap(bmp)
    }
}