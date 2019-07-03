package com.esphereal.bair.neighborhood.NEIGHBORHOOD

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.esphereal.bair.funloot.R

class MyRecyclerViewAdapter(private val mValues: ArrayList<RetrofitSingleton.NibbaItems>) : RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>(), View.OnClickListener {
    override fun onClick(v: View?) {

    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(p0.context)
                .inflate(R.layout.nibba_item, p0, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    override fun onBindViewHolder(p0: MyRecyclerViewAdapter.ViewHolder, position: Int) {
        p0.mTitle.text = mValues[position].title
        p0.mBody.text = mValues[position].body
//        p0.mImage.setImageResource(mValues[position].imageUrl)
//      p0.mUser.text = mValues[position].toUser()
       /// p0.mRating.autoLinkMask = mValues[position].toRating()


    }

    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        override fun onClick(v: View?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        val mTitle: TextView
        val mBody: TextView
        val mImage: ImageView
        val mNibbaCardView: CardView
        val mUser: TextView
    //  val mRating: TextView
        val i = 0

        init {
            mTitle = mView.findViewById(R.id.nibba_title) as TextView
            mBody = mView.findViewById(R.id.nibba_body) as TextView
            mImage = mView.findViewById(R.id.nibba_image) as ImageView
            mUser = mView.findViewById(R.id.nibba_name) as TextView
          //  mRating = mView.findViewById(R.id.nibba_rating) as TextView
            mNibbaCardView = mView.findViewById(R.id.nibba_cardView) as CardView
            /*     mView.setOnClickListener{if (adapterPosition != RecyclerView.NO_POSITION)

                 }*/
        }




    }

    /*companion object {
        fun expand(v: View) {
            v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val targetHeight = v.measuredHeight
            val star = targetHeight
            v.visibility = View.VISIBLE
            val a = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    val layoutParams = v.layoutParams as LinearLayout.LayoutParams
                    layoutParams.height = (targetHeight + (600 - targetHeight) * interpolatedTime).toInt()
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    v.layoutParams = layoutParams
                    // super.applyTransformation(interpolatedTime, t)
                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }
            a.duration = 400
            v.startAnimation(a)
        }

        fun collapse(v: View) {
            val initialHeight = v.measuredHeight

            val a = object : Animation() {

                override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                    val layoutParams = v.layoutParams as LinearLayout.LayoutParams
                    layoutParams.height = (initialHeight + (329 - initialHeight) * interpolatedTime).toInt()
                    layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    v.layoutParams = layoutParams

                }

                override fun willChangeBounds(): Boolean {
                    return true
                }
            }
            a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
            v.startAnimation(a)
        }
    }*/

}