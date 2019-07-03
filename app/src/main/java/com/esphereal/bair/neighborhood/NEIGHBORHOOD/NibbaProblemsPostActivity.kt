package com.esphereal.bair.neighborhood.NEIGHBORHOOD

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.esphereal.bair.funloot.R
import kotlinx.android.synthetic.main.nibba_problems_post.*
import com.esphereal.bair.neighborhood.retrofit.RetrofitSingleton
import com.esphereal.bair.neighborhood.util.PrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers

class NibbaProblemsPostFragment : Fragment(), View.OnClickListener{
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.nibba_problems_post, container, false)
        return view




    val nibbaBody = NibbaPostBody()
    val nibba_bibba : Button = view.findViewById(R.id.nibba_button_send)


        nibba_button_send.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                nibbaBody.problems=view.findViewById<EditText>(R.id.nibbaEditAbout).text.toString()
                nibbaBody.title=view.findViewById<EditText>(R.id.nibbaEditTitle).text.toString()
                nibbaBody.date=view.findViewById<EditText>(R.id.nibbaEditDate).text.toString()
                RetrofitSingleton.getInstance()
                        .GetApi()
                        .postProblems(PrefUtil.getIdToken(this@NibbaProblemsPostFragment),nibbaBody)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({result ->
                            Log.d("Test", result.toString())

                        },{trowable -> {}})

                      }


        })
    }
}