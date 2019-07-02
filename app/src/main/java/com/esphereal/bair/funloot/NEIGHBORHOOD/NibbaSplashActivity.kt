package com.esphereal.bair.funloot.NEIGHBORHOOD

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.esphereal.bair.funloot.R

class NibbaSplashActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    setContentView(R.layout.nibba_splash_activity)
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)}


        private var mDelayHandler: Handler? = null
        private val SPLASH_DELAY: Long = 3000 //3 seconds

        internal val mRunnable: Runnable = Runnable {
            if (!isFinishing) {

                val intent = Intent(applicationContext, NMainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    public override fun onDestroy() {

        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }

}