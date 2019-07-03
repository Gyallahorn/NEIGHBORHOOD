package com.esphereal.bair.funloot.dummyAdding

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.esphereal.bair.funloot.R
import com.esphereal.bair.funloot.retrofit.RetrofitSingleton
import com.esphereal.bair.funloot.util.PrefUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.ivent_item.view.*
import kotlinx.android.synthetic.main.ivent_list.*
import org.w3c.dom.Text

class IventNewsActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

    }

/*
    val mNewsList: ArrayList<String> = ArrayList()
    private var mIvent_news_title: TextView? = null
    private var mIvent_news_body: TextView? = null
    private var news_title: IventNews? = null
    private var news_body: IventNews? = null
*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ivent_list)

        val recyclerView: RecyclerView = findViewById(R.id.ivent_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyRecycleViewIventAdapter(IventData.ITEMS)

        GettingIventNewsSingletone.getInstance().Get_title(context = this)
/*
        mIvent_news_title = findViewById(R.id.ivent_news_title)
        mIvent_news_body = findViewById(R.id.ivent_news_body)
        // mNewsList.add(news_title.toString())
        // mNewsList.вadd(news_body.toString())
        mNewsList.add("bla bla")
        mNewsList.add("ololol")
*/
    }

    class MyRecycleViewIventAdapter(private val mValues: List<IventData.IventDataItem>) : RecyclerView.Adapter<MyRecycleViewIventAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyRecycleViewIventAdapter.ViewHolder {
            val view = LayoutInflater.from(p0.context)
                    .inflate(R.layout.ivent_item, p0, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        override fun onBindViewHolder(p0: MyRecycleViewIventAdapter.ViewHolder, position: Int) {
            p0.mTitle.text = mValues[position].toString()
            p0.mBody.text = mValues[position].toString()
        }

        class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mTitle: TextView
            val mBody: TextView

            init {
                mTitle = mView.findViewById(R.id.ivent_news_title) as TextView
                mBody = mView.findViewById(R.id.ivent_news_body) as TextView
            }
        }
    }
}



