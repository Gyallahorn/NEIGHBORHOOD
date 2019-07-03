package com.esphereal.bair.funloot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.webkit.WebView
import android.widget.LinearLayout
import android.widget.TextView
import com.esphereal.bair.funloot.retrofit.RetrofitSingleton

import com.esphereal.bair.funloot.util.ProblemsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_problems.*

class ProblemListJava : AppCompatActivity() {

    private var numbersList: RecyclerView? = null
    private var problemsAdapter: ProblemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problems)

        numbersList = findViewById<RecyclerView>(R.id.problems_list)

        val layoutManager = LinearLayoutManager(this)
        numbersList!!.layoutManager = layoutManager

        //numbersList!!.setHasFixedSize(true)

        RetrofitSingleton.getInstance().GetApi().getProblems().observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    //This replace TextView to mongoDB field
                    //textProblem.text = result[0].problems_body
                    numbersList!!.setAdapter(ProblemsAdapter(result))
                }



    }
}

