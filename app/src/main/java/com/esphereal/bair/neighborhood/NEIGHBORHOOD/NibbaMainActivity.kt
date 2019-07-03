package com.esphereal.bair.neighborhood.NEIGHBORHOOD

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.esphereal.bair.funloot.R
import com.esphereal.bair.neighborhood.NewsDetails
import kotlinx.android.synthetic.main.nibba_main_activity.*

class NibbaMainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nibba_main_activity)
       // toolbar = supportActionBar!!
     //   val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)

        val fragmentAdapter = NibbaPagerAdapter (supportFragmentManager)
        viewpager.adapter=fragmentAdapter
        tabs.setupWithViewPager(viewpager)






    }


}