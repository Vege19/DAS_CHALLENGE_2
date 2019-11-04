package com.example.das_challenge_2.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.setImageWithGlide(context: Context, url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun getFirebaseReference(path: String): DatabaseReference {
    return FirebaseDatabase.getInstance().getReference(path)
}