/*
package com.esphereal.bair.neighborhood.dummyAdding

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import org.jetbrains.anko.toast

import com.esphereal.bair.funloot.R
import com.esphereal.bair.funloot.retrofit.RetrofitSingleton
import com.esphereal.bair.funloot.util.PrefUtil
import com.esphereal.bair.neighborhood.NEIGHBORHOOD.NibbaPostBody
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.adding_dummy.*

class AddingActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.adding_dummy)

        val scoreBody = NibbaPostBody()


ivent_button.setOnClickListener(object: View.OnClickListener{
    override fun onClick(v: View?) {
        scoreBody.team_1_name= findViewById<EditText>(R.id.ivent_team_name_1).text.toString()
        scoreBody.team_2_name=findViewById<EditText>(R.id.ivent_team_name_2).text.toString()
        scoreBody.team_1_score=findViewById<EditText>(R.id.ivent_score1).text.toString()
        scoreBody.team_2_score=findViewById<EditText>(R.id.ivent_score2).text.toString()
                 RetrofitSingleton.getInstance()
                         .GetApi()
                         .postScore(PrefUtil.getIdToken(this@AddingActivity),scoreBody)
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe({result ->
                    Log.d("Test", result.toString())

                },{trowable -> {}})
    toast("DONE")
    }
})

        ivent_go_to_list.setOnClickListener(object: View.OnClickListener{
            override fun onClick (v:View?){
                val intent = Intent(this@AddingActivity, IventNewsActivity::class.java)
                startActivity(intent)
            }
        })
    }
}


*/
