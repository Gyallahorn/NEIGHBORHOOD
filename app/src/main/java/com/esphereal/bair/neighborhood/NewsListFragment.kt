package com.esphereal.bair.neighborhood

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar


import com.esphereal.bair.funloot.R
import com.esphereal.bair.neighborhood.jsoup.JsoupOnDataBackListener
import com.esphereal.bair.neighborhood.jsoup.JsoupSingleton

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class NewsListFragment : Fragment() {
    // TODO: Customize parameters
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null

    private var mProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments!!.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        Log.d(TAG, "onCreate $TAG")
        // Set the adapter

        val context = view.context
        val recyclerView = view.findViewById<View>(R.id.news_list) as RecyclerView
        if (mColumnCount <= 1) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, mColumnCount)
        }
        mProgressBar = view.findViewById<View>(R.id.news_list_loader) as ProgressBar
        mProgressBar!!.visibility = View.VISIBLE
        JsoupSingleton.getInstance().addListener { data ->
            recyclerView.adapter = MyItemRecyclerViewAdapter(data, mListener)
            mProgressBar!!.visibility = View.GONE
        }
        JsoupSingleton.getInstance().GetData()


        return view
    }


    override fun onAttach(context: Context?) {
        Log.d(TAG, "$TAG onAttach")
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onResume() {
        Log.d(TAG, "onResume $TAG")
        super.onResume()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: JsoupSingleton.NewsItem, loader: ProgressBar)
    }

    companion object {


        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"
        private val TAG = "NewsListFragment"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): NewsListFragment {
            val fragment = NewsListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
