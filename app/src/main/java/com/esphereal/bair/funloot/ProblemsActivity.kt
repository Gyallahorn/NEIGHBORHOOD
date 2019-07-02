package com.esphereal.bair.funloot

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.esphereal.bair.funloot.retrofit.RetrofitSingleton
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_problems.*

//That activity created for test
class ProblemsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problems)
        //That is MAIN THING, dunno wat doing, but it works!
        RetrofitSingleton.getInstance().GetApi().getProblems().observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    //This replace TextView to mongoDB field
                    textProblem.text = result[2].problems_body

                }
        //val textView: TextView = findViewById(R.id.textProblem) as TextView

        }

    }



