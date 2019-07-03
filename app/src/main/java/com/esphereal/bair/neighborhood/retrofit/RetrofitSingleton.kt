package com.esphereal.bair.funloot.retrofit

import com.esphereal.bair.funloot.dummyAdding.IventNews
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

    private fun getIdToken(idTokenCallBack: IdTokenCallBack) {
        FirebaseAuth.getInstance().currentUser!!.getIdToken(false).addOnSuccessListener { getTokenResult -> idTokenCallBack.onIdTokenBack(getTokenResult.token) }
    }

    fun GetUser(callback: Callback<FunlootUser>) {
        val idTokenCallBack = IdTokenCallBack { idToken -> mRetrofit.create(FunlootApi::class.java).getUser(idToken).enqueue(callback) }
        getIdToken(idTokenCallBack)


    }

    fun GetLogin(callback: Callback<Void>, idToken: String) {
        mRetrofit.create(FunlootApi::class.java).getLogin(idToken).enqueue(callback)

    }

    fun PostUser(callback: Callback<FunlootUser>, user: FunlootUser) {
        val idTokenCallBack = IdTokenCallBack { idToken -> mRetrofit.create(FunlootApi::class.java).postUser(idToken, user).enqueue(callback) }
        getIdToken(idTokenCallBack)


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
