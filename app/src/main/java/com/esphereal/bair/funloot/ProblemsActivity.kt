package com.esphereal.bair.funloot

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.esphereal.bair.funloot.retrofit.RetrofitSingleton
import com.esphereal.bair.funloot.util.ProblemsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_problems.*

//That activity created for test
class ProblemsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problems)
        var recyclerView = findViewById<RecyclerView>(R.id.problems_list)
        //That is MAIN THING, dunno wat doing, but it works!
        RetrofitSingleton.getInstance().GetApi().getProblems().observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    //This replace TextView to mongoDB field
                    textProblem.text = result[0].problems_body
                    recyclerView.setAdapter(ProblemsAdapter(result))

                }
        }

    }


