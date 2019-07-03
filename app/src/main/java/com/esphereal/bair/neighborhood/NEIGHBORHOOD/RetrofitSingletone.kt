package com.esphereal.bair.neighborhood.NEIGHBORHOOD

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.esphereal.bair.funloot.R
import com.esphereal.bair.neighborhood.jsoup.JsoupSingleton
import com.esphereal.bair.neighborhood.retrofit.RetrofitSingleton
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_problems.*
import okhttp3.internal.Internal
import okhttp3.internal.Internal.instance

//That activity created for test
/*class NibbaProblemsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val retro = com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitSingleton
        retro.GetData()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problems)
        //That is MAIN THING, dunno wat doing, but it works!
        RetrofitSingleton.getInstance().GetApi().getProblems().observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    //This replace TextView to mongoDB field
                    textProblem.text = result[0].problems_body

                }
        //val textView: TextView = findViewById(R.id.textProblem) as TextView

        }




    }*/
class RetrofitSingleton : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        GetData()
      /*  val retro = com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitSingleton
        retro.GetData()*/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nibba_news)

    }



    private val listeners = ArrayList<com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitOnDataBackListener>()
    val nibba_data : ArrayList<NibbaItems>?=null

    fun addListener (toAdd:com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitOnDataBackListener ){
        listeners.add(toAdd)
    }
    class NibbaItems(val title : String , var body :String) {

        override fun toString(): String{
            return  title
        }
    }
    fun GetData() {

        if (nibba_data == null) {
            getData()
        } else
            sendToListeners()
    }

    private fun sendToListeners(){
        Handler(Looper.getMainLooper()).post{
            for (hl in listeners)
                nibba_data?.let { hl.onDataBack(it) }
        }
    }
    private fun getData() {
        val data = ArrayList<NibbaItems>()
        var nibbaItem: NibbaItems? = null
        RetrofitSingleton.getInstance().GetApi().getProblems().observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    //This replace TextView to mongoDB field
                    val title= result[0].problems_title.toString()
                    val body= result[0].problems_body.toString()
                    nibbaItem = NibbaItems(title,body)
                    data.add(nibbaItem!!)
                }


    }
    companion object {
        private val TAG = "RettrofitSingletone"

        @Volatile
        private var instance: com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitSingleton? = null

        fun getInstance(): com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitSingleton? {
            var localInstance = instance
            if (localInstance == null) {
                synchronized(com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitSingleton::class.java) {
                    localInstance = instance
                    if (localInstance == null) {
                        localInstance = RetrofitSingleton()
                        instance = localInstance
                    }
                }
            }
            return localInstance
        }
    }





}


