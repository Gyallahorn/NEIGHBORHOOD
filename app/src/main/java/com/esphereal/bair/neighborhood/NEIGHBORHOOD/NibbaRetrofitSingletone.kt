package com.esphereal.bair.neighborhood.NEIGHBORHOOD

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.esphereal.bair.funloot.R
import com.esphereal.bair.neighborhood.jsoup.JsoupSingleton
import com.esphereal.bair.neighborhood.retrofit.FunlootApi
import com.esphereal.bair.neighborhood.retrofit.RetrofitSingleton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_problems.*
import okhttp3.OkHttpClient
import okhttp3.internal.Internal
import okhttp3.internal.Internal.instance
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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
class RetrofitSingletone : AppCompatActivity() {
    private val mRetrofit: Retrofit
    init {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)

        val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
        mRetrofit = Retrofit.Builder()
                //.baseUrl("https://funloot.herokuapp.com/")
                .baseUrl("http://192.168.137.1:3000/") //My local IP
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .client(client.build())
                .build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        GetData()
        /*  val retro = com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitSingleton
          retro.GetData()*/
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nibba_news)

    }
    fun GetApi(): FunlootApi {

        return mRetrofit.create(FunlootApi::class.java)
    }




    private val listeners = ArrayList<com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitOnDataBackListener>()
    val nibba_data: ArrayList<NibbaItems>? = null

    fun addListener(toAdd: com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitOnDataBackListener) {
        listeners.add(toAdd)
    }

    class NibbaItems(val title: String, val body: String, val createDate: String, val userName: String) {

        override fun toString(): String {
            return title
        }
    }

    class ProblemsItems(val title: String, val body: String){
        override fun toString() : String {
            return title
        }
    }

    fun GetData() {

        if (nibba_data == null) {
            getData()
        } else
            sendToListeners()
    }

    private fun sendToListeners() {
        Handler(Looper.getMainLooper()).post {
            for (hl in listeners)
                nibba_data?.let { hl.onDataBack(it) }
        }
    }

    private fun getData() {
        val data = ArrayList<NibbaItems>()
        var nibbaItem: NibbaItems
        RetrofitSingleton.getInstance().GetApi().getProblems().observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->

                }


    }

/*    private fun PostProblem() {
        val prob_data = ArrayList<ProblemsItems>()
        var probItems: ProblemsItems
        RetrofitSingletone.getInstance().G
    }*/

    companion object {
        private val TAG = "RettrofitSingletone"

        @Volatile
        private var instance: com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitSingletone? = null

        fun getInstance(): com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitSingletone? {
            var localInstance = instance
            if (localInstance == null) {
                synchronized(com.esphereal.bair.neighborhood.NEIGHBORHOOD.RetrofitSingletone::class.java) {
                    localInstance = instance
                    if (localInstance == null) {
                        localInstance = RetrofitSingletone()
                        instance = localInstance
                    }
                }
            }
            return localInstance
        }
    }


}


