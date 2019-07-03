package com.esphereal.bair.neighborhood.retrofit

import com.esphereal.bair.neighborhood.dummyAdding.IventNews
import com.google.firebase.auth.FirebaseAuth

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


class RetrofitSingleton private constructor() {
    private val mRetrofit: Retrofit

    init {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)

        val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
        mRetrofit = Retrofit.Builder()
                //.baseUrl("https://funloot.herokuapp.com/")
                .baseUrl("http://10.0.110.108:3000/") //My local IP
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .client(client.build())
                .build()
    }

    private fun getIdToken(idTokenCallBack: com.esphereal.bair.neighborhood.retrofit.IdTokenCallBack) {
        FirebaseAuth.getInstance().currentUser!!.getIdToken(false).addOnSuccessListener { getTokenResult -> idTokenCallBack.onIdTokenBack(getTokenResult.token) }
    }





    fun GetApi(): FunlootApi {

        return mRetrofit.create(FunlootApi::class.java)
    }


    companion object {

        @Volatile
        private var instance: RetrofitSingleton? = null

        fun getInstance(): RetrofitSingleton {
            var localInstance = instance
            if (localInstance == null) {
                synchronized(RetrofitSingleton::class.java) {
                    localInstance = instance
                    if (localInstance == null) {
                        localInstance = RetrofitSingleton()
                        instance = localInstance
                    }
                }
            }
            return localInstance as RetrofitSingleton
        }


        private val TAG = RetrofitSingleton.javaClass.simpleName
    }

}
