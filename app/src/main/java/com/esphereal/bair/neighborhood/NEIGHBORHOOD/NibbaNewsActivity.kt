package com.esphereal.bair.funloot.NEIGHBORHOOD

import android.media.Image
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.esphereal.bair.funloot.R
import kotlinx.android.synthetic.main.news_item.view.*

class NibbaNewsActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nibba_news)

        val recyclerView: RecyclerView = findViewById(R.id.nibba_news)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyRecyclerViewAdapter(NibbaList.ITEMS)

    }

    class MyRecyclerViewAdapter(private val mValues: List<NibbaList.NibbaListItem>) : RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyRecyclerViewAdapter.ViewHolder {
            val view = LayoutInflater.from(p0.context)
                    .inflate(R.layout.nibba_item, p0, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        override fun onBindViewHolder(p0: MyRecyclerViewAdapter.ViewHolder, position: Int) {
            p0.mTitle.text = mValues[position].toString()
            p0.mBody.text = mValues[position].toString()
            p0.mImage.setImageResource(mValues[position].imageUrl)

        }

        class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mTitle: TextView
            val mBody: TextView
            val mImage: ImageView


            init {
                mTitle = mView.findViewById(R.id.nibba_title) as TextView
                mBody = mView.findViewById(R.id.nibba_body) as TextView
                mImage = mView.findViewById(R.id.nibba_image) as ImageView
                /*     mView.setOnClickListener{if (adapterPosition != RecyclerView.NO_POSITION)

                     }*/
            }
        }

    }

}

