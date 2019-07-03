package com.esphereal.bair.funloot.NEIGHBORHOOD

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.esphereal.bair.funloot.R
import kotlinx.android.synthetic.main.nibba_main_activity.*

class NibbaMainActivity : AppCompatActivity(),View.OnClickListener{
    override fun onClick(v: View?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nibba_main_activity)
        nibba_button.setOnClickListener(object : View.OnClickListener{
            override fun onClick (v:View?){
                val intent = Intent (this@NibbaMainActivity,NibbaNewsActivity::class.java)
                startActivity(intent)
            }

        })
        nibba_button2.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent (this@NibbaMainActivity, NibbaProblemsActivity::class.java)
                startActivity(intent)  }


        })

    }


}