package com.example.googlelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.ktx.options
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val user = FirebaseAuth.getInstance().currentUser

        user?.let {
            Toast.makeText(this, "${user.photoUrl}", Toast.LENGTH_SHORT).show()

            var str = textView.text.toString() + "\n"
            str += user.displayName + "\n"
            str += user.email + "\n"
            str += user.photoUrl


            // Glide.with(this).load(user.photoUrl).into(imageView)
            Glide.with(this).load(user.photoUrl)
                .transform(CenterCrop(), RoundedCorners(50))
                .into(imageView)
            textView.text = str
        }
    }

    fun signout(view: View) {
        // AuthUI.getInstance().delete(this)
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}