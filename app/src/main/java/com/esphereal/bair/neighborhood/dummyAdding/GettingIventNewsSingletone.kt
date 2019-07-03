package com.esphereal.bair.neighborhood.dummyAdding

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log

import com.esphereal.bair.funloot.retrofit.RetrofitSingleton
import com.esphereal.bair.funloot.util.PrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers


class GettingIventNewsSingletone {

    private var news_title: IventNews? = null
    private var  news_body: IventNews? = null

    fun SetNews_title(news_title: IventNews) {
        this.news_title = news_title
    }

    fun SetNews_body(news_body: IventNews){
        this.news_body=news_body
    }


    @SuppressLint("LongLogTag", "CheckResult")
    fun Get_title(context: Context) {
        if (news_title == null && news_body==null) {
            val idToken = PrefUtil.getIdToken(context);
            RetrofitSingleton.getInstance()
                    .GetApi()
                    .getNews(idToken)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { result ->
                        Log.d("TEST", result.toString())
                        news_title = result
                        news_body = result
                    }
        }
    }

    companion object {
        private val TAG = "GettingIventNewsSingletone"


        @Volatile
        private var instance: GettingIventNewsSingletone? = null

        fun getInstance(): GettingIventNewsSingletone {
            var localInstance = instance
            if (localInstance == null) {
                synchronized(GettingIventNewsSingletone::class.java) {
                    localInstance = instance
                    if (localInstance == null) {
                        localInstance = GettingIventNewsSingletone()
                        instance = localInstance
                    }
                }
            }
            return localInstance!!
        }
    }


}
