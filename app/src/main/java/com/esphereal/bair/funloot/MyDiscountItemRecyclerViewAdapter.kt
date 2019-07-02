package com.esphereal.bair.funloot

import android.content.Context
import android.os.Handler
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView

import com.esphereal.bair.funloot.DiscountFragment.OnListFragmentInteractionListener
import com.esphereal.bair.funloot.dummy.DiscountDummyContent
import com.esphereal.bair.funloot.dummy.DummyContent.DummyItem
import java.util.logging.StreamHandler

import android.view.View.VISIBLE

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyDiscountItemRecyclerViewAdapter(private val mValues: List<DiscountDummyContent.DiscountDummyItem>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyDiscountItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_discount_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mTitle.text = mValues[position].text
        holder.mValue.text = mValues[position].discountValue
        holder.mLogo.setImageResource(mValues[position].imageUrl)
        holder.mAbout.text = mValues[position].detailsText


        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem)
        }
    }


    override fun getItemCount(): Int {
        return mValues.size
    }


    class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        val mTitle: TextView
        val mValue: TextView
        val mLogo: ImageView
        var mItem: DiscountDummyContent.DiscountDummyItem? = null
        val mAbout: TextView
        val mImage: ImageButton
        val mImage2: ImageButton
        val mCardView: CardView


        init {
            mTitle = mView.findViewById<View>(R.id.discount_item_title) as TextView
            mValue = mView.findViewById<View>(R.id.discount_item_value) as TextView
            mLogo = mView.findViewById(R.id.logo_discount_item)
            mAbout = mView.findViewById<View>(R.id.discount_item_about) as TextView
            mImage = mView.findViewById<View>(R.id.discount_Image_button) as ImageButton
            mImage.setOnClickListener(this)
            mImage2 = mView.findViewById<View>(R.id.discount_Image_button_show) as ImageButton
            mImage2.setOnClickListener(this)
            mCardView = mView.findViewById<View>(R.id.FunLootExpandableCardview) as CardView


        }

        override fun toString(): String {
            return super.toString() + " '" + mValue.text + "'"
        }

        fun Visible_mAbout() {
            mAbout.visibility = VISIBLE
        }


        override fun onClick(v: View) {
            val p = 150

            when (v.id) {
                R.id.discount_Image_button -> {
                    expand(mCardView)
                    val handler = Handler()
                    handler.postDelayed({ Visible_mAbout() }, 400)


                    mImage.visibility = View.INVISIBLE
                    mImage2.visibility = VISIBLE
                }
                R.id.discount_Image_button_show -> {
                    mAbout.visibility = View.INVISIBLE
                    mImage.visibility = VISIBLE
                    mImage2.visibility = View.INVISIBLE
                    collapse(mCardView)
                }
            }// mAbout.setHeight(600);
        }

        companion object {

            fun expand(v: View) {
                v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                val targetHeight = v.measuredHeight
                val star = targetHeight
                v.visibility = VISIBLE
                val a = object : Animation() {
                    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                        val layoutParams = v.layoutParams as LinearLayout.LayoutParams
                        layoutParams.height = (targetHeight + (600 - targetHeight) * interpolatedTime).toInt()
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        v.layoutParams = layoutParams
                        Log.d(TAG, "$interpolatedTime interplotatedTime")
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
        }


    }

    companion object {
        private val TAG = MyDiscountItemRecyclerViewAdapter::class.java.simpleName
    }


}


