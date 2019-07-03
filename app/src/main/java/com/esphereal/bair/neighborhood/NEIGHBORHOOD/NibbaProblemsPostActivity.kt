package com.esphereal.bair.neighborhood.NEIGHBORHOOD

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import com.esphereal.bair.funloot.R
import kotlinx.android.synthetic.main.nibba_problems_post.*
import com.esphereal.bair.neighborhood.retrofit.RetrofitSingleton
import com.esphereal.bair.neighborhood.util.PrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.toast

class NibbaProblemsPostActivity : AppCompatActivity(), View.OnClickListener{
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
setContentView(R.layout.nibba_problems_post)
        val nibbaBody = NibbaPostBody()

        nibba_button_send.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                nibbaBody.problems=findViewById<EditText>(R.id.nibbaEditAbout).text.toString()
                nibbaBody.title=findViewById<EditText>(R.id.nibbaEditTitle).text.toString()
                nibbaBody.date=findViewById<EditText>(R.id.nibbaEditDate).text.toString()
                RetrofitSingleton.getInstance()
                        .GetApi()
                        .postProblems(PrefUtil.getIdToken(this@NibbaProblemsPostActivity),nibbaBody)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({result ->
                            Log.d("Test", result.toString())

                        },{trowable -> {}})
                        toast("Готово!")
                      }


        })
    }
}