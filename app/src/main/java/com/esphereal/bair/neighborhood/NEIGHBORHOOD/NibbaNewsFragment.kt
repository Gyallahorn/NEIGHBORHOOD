package com.esphereal.bair.neighborhood.NEIGHBORHOOD

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.esphereal.bair.funloot.R
import com.esphereal.bair.neighborhood.NewsListFragment
import kotlinx.android.synthetic.main.news_item.view.*
import kotlinx.android.synthetic.main.nibba_news.*
import org.jetbrains.anko.toast

class NibbaNewsFragment : Fragment(), View.OnClickListener {

    public val rating = 500
    override fun onClick(v: View?) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.nibba_news, container, false)


        val context = view.context
        val recyclerView: RecyclerView = view.findViewById(R.id.nibba_news) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = MyRecyclerViewAdapter(NibbaList.ITEMS)

        return view
    }



}







