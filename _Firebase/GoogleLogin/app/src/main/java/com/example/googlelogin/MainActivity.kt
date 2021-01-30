package com.example.googlelogin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.facebook.FacebookSdk
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val LOGIN_CODE = 111
    val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),

            AuthUI.IdpConfig.FacebookBuilder().build()
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }



        // 다양한 로그인 방식 제공
        signBtn.setOnClickListener {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                LOGIN_CODE)
        }

//        signBtn.setOnClickListener {
//            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                    .requestIdToken(getString(R.string.default_web_client_id))
//                    .requestEmail()
//                    .build()
//            startActivityForResult(GoogleSignIn.getClient(this, gso).signInIntent, LOGIN_CODE)
//        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == LOGIN_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                startActivity(Intent(this, MainActivity2::class.java))
                finish()
            }
        }
    }

}