package com.esphereal.bair.funloot

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.esphereal.bair.funloot.profile.ProfileSingletone
import com.esphereal.bair.funloot.retrofit.RetrofitSingleton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

import java.util.concurrent.TimeUnit

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var mGoogleButton: Button? = null
    private val mSignOut: Button? = null

    private var mPhoneNumberEdit: EditText? = null
    private var mCodeEdit: EditText? = null

    private var mStartVerifyButton: Button? = null
    private var mResendVerifyButton: Button? = null
    private var mCodeVerifyButton: Button? = null

    private var mProgressBar: ProgressBar? = null

    private var mContentLoginLayout: android.support.constraint.ConstraintLayout? = null

    private var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null

    private var mVerificationId: String? = null
    private var mResendToken: PhoneAuthProvider.ForceResendingToken? = null

    var mProgressDialog: ProgressDialog? = null

    private enum class uiState {
        STATE_BEGIN,
        STATE_STARTED,
        STATE_CODESEND,
        STATE_FAILURE,
        STATE_CODEPRESSED


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mGoogleButton = findViewById(R.id.google_button_login)
        mGoogleButton!!.setOnClickListener { signIn() }
        /*   mSignOut = findViewById(R.id.button_sign_out);
        mSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });*/
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance()


        mStartVerifyButton = findViewById(R.id.start_verify_button_login)
        mResendVerifyButton = findViewById(R.id.resend_verify_button)
        mCodeVerifyButton = findViewById(R.id.code_verify_button)
        mCodeEdit = findViewById(R.id.code_edit_login)
        mPhoneNumberEdit = findViewById(R.id.phone_edit_login)
        mProgressBar = findViewById(R.id.progress_bar_login)
        mContentLoginLayout = findViewById(R.id.content_login)

        /*   mCodeVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCodeVerify();
            }
        });*/

        mResendVerifyButton!!.setOnClickListener { onResendVerify() }
        mStartVerifyButton!!.setOnClickListener { onStartVerify() }

        mCodeEdit!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {

                if (s.length >= 6) {
                    onCodeVerify()
                }
            }
        }
        )


        mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted!")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.d(TAG, "FUCK! nVerificationStateChangedCallbacks")
                changeUi(uiState.STATE_BEGIN)
                val toast = Toast.makeText(applicationContext,
                        "Ошибка", Toast.LENGTH_LONG)
                toast.show()
                //+79991737803
                Log.d(TAG, e.message)
            }

            override fun onCodeSent(verificationId: String?,
                                    token: PhoneAuthProvider.ForceResendingToken?) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId!!)

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId
                mResendToken = token
                changeUi(uiState.STATE_CODESEND)

            }
        }


        changeUi(uiState.STATE_BEGIN)
    }

    private fun changeUi(state: uiState) {
        when (state) {
            LoginActivity.uiState.STATE_BEGIN -> {
                mCodeVerifyButton!!.visibility = View.GONE
                mResendVerifyButton!!.visibility = View.GONE
                mStartVerifyButton!!.visibility = View.VISIBLE
                mPhoneNumberEdit!!.visibility = View.VISIBLE
                mCodeEdit!!.visibility = View.GONE
                mProgressBar!!.visibility = View.GONE
                mContentLoginLayout!!.visibility = View.VISIBLE
            }
            LoginActivity.uiState.STATE_STARTED -> {
                mContentLoginLayout!!.visibility = View.GONE
                mPhoneNumberEdit!!.visibility = View.GONE
                mCodeEdit!!.visibility = View.GONE
                mCodeVerifyButton!!.visibility = View.GONE
                mResendVerifyButton!!.visibility = View.GONE
                mStartVerifyButton!!.visibility = View.GONE
                mProgressBar!!.visibility = View.VISIBLE
            }
            LoginActivity.uiState.STATE_CODESEND -> {
                mContentLoginLayout!!.visibility = View.VISIBLE
                //mCodeVerifyButton.setVisibility(View.VISIBLE);
                //mResendVerifyButton.setVisibility(View.VISIBLE);
                mStartVerifyButton!!.visibility = View.GONE
                mPhoneNumberEdit!!.visibility = View.GONE
                mCodeEdit!!.visibility = View.VISIBLE
                mProgressBar!!.visibility = View.GONE
            }
            LoginActivity.uiState.STATE_FAILURE -> {
            }
            LoginActivity.uiState.STATE_CODEPRESSED -> {
                mPhoneNumberEdit!!.visibility = View.GONE
                mCodeEdit!!.visibility = View.GONE
                mCodeVerifyButton!!.visibility = View.GONE
                mResendVerifyButton!!.visibility = View.GONE
                mStartVerifyButton!!.visibility = View.GONE
                mProgressBar!!.visibility = View.VISIBLE
                mContentLoginLayout!!.visibility = View.GONE
            }
        }
    }

    private fun onCodeVerify() {
        val imm = mCodeEdit!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mCodeEdit!!.windowToken, 0)
        val code = mCodeEdit!!.text.toString()
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code)
        signInWithPhoneAuthCredential(credential)


        changeUi(uiState.STATE_CODEPRESSED)


    }

    private fun onResendVerify() {
        changeUi(uiState.STATE_BEGIN)
    }


    private fun onStartVerify() {
        val imm = mPhoneNumberEdit!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mPhoneNumberEdit!!.windowToken, 0)
        changeUi(uiState.STATE_STARTED)
        val number = mPhoneNumberEdit!!.text.toString()
        if (mResendToken == null)
            PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, this, mCallBack!!)
        else
            PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, this, mCallBack!!, mResendToken)

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Log.d(TAG, "signInWithCredential:success")
                        loginOnBackEnd()
                        //FirebaseUser user = task.getResult().getUser();

                    } else {
                        // Sign in failed, display a message and update the UI
                        changeUi(uiState.STATE_BEGIN)
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        val toast = Toast.makeText(applicationContext,
                                "Не удалось подключиться через телефон", Toast.LENGTH_LONG)
                        toast.show()
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {

                        }

                    }
                }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                val toast = Toast.makeText(applicationContext,
                        "Ошибка", Toast.LENGTH_LONG)
                toast.show()
                changeUi(uiState.STATE_BEGIN)
            }

        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        showProgressDialog()

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success")
                        loginOnBackEnd()


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        changeUi(uiState.STATE_BEGIN)
                        val toast = Toast.makeText(applicationContext,
                                "не удалось подключиться к google auth", Toast.LENGTH_LONG)
                        toast.show()

                    }
                    hideProgressDialog()
                }
    }

    private fun loginOnBackEnd() {
        val callback = object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                Log.d(TAG, "get response from backend code " + response.code())
                if (!response.isSuccessful) {
                    changeUi(uiState.STATE_BEGIN)
                    val toast = Toast.makeText(applicationContext,
                            "Серверная ошибка", Toast.LENGTH_LONG)
                    toast.show()

                } else {
                    goToMainActivity()
                }


            }


            override fun onFailure(call: Call<Void>, t: Throwable) {
                changeUi(uiState.STATE_BEGIN)
                val toast = Toast.makeText(applicationContext,
                        "Ошибка соединения", Toast.LENGTH_LONG)
                toast.show()
                Log.d(TAG, t.message)
            }
        }
        val user = mAuth!!.currentUser
        val idToken = user!!.getIdToken(false).result!!.token
        val sharedPreferences = this@LoginActivity.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("idToken", idToken)
            commit()

        }
        RetrofitSingleton.getInstance().GetLogin(callback, idToken!!)
    }

    private fun goToMainActivity() {
        Log.d(TAG, "goToMainActivity")
        ProfileSingletone.getInstance().GetUser(null)
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun signIn() {
        Log.d(TAG, "on click google signin")
        changeUi(uiState.STATE_CODEPRESSED)
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onStart() {
        super.onStart()
        //mAuth.addAuthStateListener();
    }

    fun showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
            mProgressDialog!!.setMessage("Загрузка...")
            mProgressDialog!!.isIndeterminate = true
        }

        mProgressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }

    private fun signOut() {
        // Firebase sign out
        mAuth!!.signOut()

        // Google sign out
        mGoogleSignInClient!!.signOut().addOnCompleteListener(this
        ) { }
    }

    companion object {
        private val TAG = "LoginActivity"
        private val RC_SIGN_IN = 2
    }


}
