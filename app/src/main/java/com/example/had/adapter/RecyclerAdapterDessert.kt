package com.example.had.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.had.dataclass.DataDessert
import com.example.had.databinding.DessertListBinding
import com.example.had.dataclass.StarData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class RecyclerAdapterDessert(private val items: MutableList<DataDessert>) : RecyclerView.Adapter<RecyclerAdapterDessert.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DessertListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val listener = View.OnClickListener {
            Log.d("ON CLICK", item.toString())
        }
        holder.apply {
            bind(listener, item, position)
            itemView.tag = item
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val binding: DessertListBinding) : RecyclerView.ViewHolder(binding.root) {
        private val TAG = "RecyclerAdapterDessert"
        private val db = Firebase.firestore
        val user = Firebase.auth.currentUser

        fun bind(listener: View.OnClickListener, item : DataDessert, position: Int){

            user?.let {
                val name = user.displayName
                val uid = user.uid
            }
            binding.image1.setImageDrawable(item.img)
            binding.shopname.text = item.name
            binding.distance.text = item.address
            binding.starscore.text = item.tel
            //binding.heart.setImageDrawable(item.heart)
            //binding.heartcnt.text = item.heartcnt
            val listDessert = hashMapOf(
                "name" to "${binding.shopname.text}",
                "address" to "${binding.distance.text}",
                "phone" to "${binding.starscore.text}"
            )

            if (user != null) {
                db.collection(user.uid).document("${binding.shopname.text}").get()
                    .addOnSuccessListener { documentSnapshot ->
                        var star = documentSnapshot.toObject<StarData>()
                        //if (documentSnapshot != null) {
                        if (star != null) {
                            Log.d(TAG, "star data: ${star.name}")
                            binding.heart.setVisibility(View.VISIBLE)
                            binding.blankheart.setVisibility(View.INVISIBLE)
                        }

                       // } else {
                    // Log.d(TAG, "No such document")
                      //  }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                        //binding.heart.setVisibility(View.INVISIBLE)
                    }
            }
            /*if (user != null) {
                Log.d("test", "${db.collection(user.uid).document("${binding.shopname.text}").get()}")
            }*/

            binding.blankheart.setOnClickListener {
                binding.heart.setVisibility(View.VISIBLE)
                binding.blankheart.setVisibility(View.INVISIBLE)
                if (user != null) {
                    db.collection(user.uid)
                        .document("${binding.shopname.text.toString()}")
                        .set(listDessert).addOnSuccessListener {
                    }
                }

            }
            binding.heart.setOnClickListener {
                binding.blankheart.setVisibility(View.VISIBLE)
                binding.heart.setVisibility(View.INVISIBLE)
                if (user != null) {
                    db.collection(user.uid)
                        .document("${binding.shopname.text.toString()}")
                        .delete()
                }
            }

        }

    }
}