package com.esphereal.bair.neighborhood

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.esphereal.bair.neighborhood.profile.ProfileSingletone
import com.esphereal.bair.funloot.util.PrefUtil
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private var mFirebaseAuth: FirebaseAuth? = null
    private var mAuthStateListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        mFirebaseAuth = FirebaseAuth.getInstance()
        mAuthStateListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser != null) {
                firebaseAuth.currentUser!!.getIdToken(false).addOnCompleteListener { result ->
                    //Log.d(TAG, result.exception!!.message)
                    if (result.isSuccessful) {
                        PrefUtil.setIdToken(result.result!!.token!!, this@SplashActivity as Context)
                        val intent = Intent(this@SplashActivity, MainActivity::class.java)
                        com.esphereal.bair.neighborhood.profile.ProfileSingletone.getInstance().GetUser(null)
                        startActivity(intent)
                        finish()

                    }

                    else  {
                        if (result.exception != null) {
                            val toast = Toast.makeText(applicationContext,
                                    result.exception!!.message, Toast.LENGTH_LONG)
                            toast.show()
                        }
                        redirectToLogin()
                    }


                }
            } else {
                redirectToLogin()
            }
        }
        mFirebaseAuth!!.addAuthStateListener(mAuthStateListener!!)


    }

    override fun onDestroy() {
        mFirebaseAuth!!.removeAuthStateListener(mAuthStateListener!!)
        super.onDestroy()
    }

    private fun redirectToLogin() {
        val intent = Intent(this@SplashActivity, com.esphereal.bair.neighborhood.LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private val TAG = "SplashActivity"
    }

}
