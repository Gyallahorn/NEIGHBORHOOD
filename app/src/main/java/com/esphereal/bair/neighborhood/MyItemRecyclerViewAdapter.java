package com.esphereal.bair.neighborhood;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.esphereal.bair.funloot.R;
import com.esphereal.bair.neighborhood.jsoup.JsoupSingleton;
import com.esphereal.bair.neighborhood.NewsListFragment.OnListFragmentInteractionListener;
import com.esphereal.bair.neighborhood.dummy.DummyContent.DummyItem;
import com.esphereal.bair.neighborhood.dummy.DummyContent;
import com.esphereal.bair.neighborhood.jsoup.JsoupSingleton;

import java.io.InputStream;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyContent.DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<JsoupSingleton.NewsItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<JsoupSingleton.NewsItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).text);
        holder.mDate.setText(mValues.get(position).date);

        //Log.d("DEBUG", mValues.get(position).imageSrc + " " + mValues.get(position).href);
        holder.mImageView.setVisibility(View.GONE);

        holder.mProgressBar.setVisibility(View.VISIBLE);
        new DownloadImageTask(holder.mImageView, holder.mProgressBar)
                .execute(mValues.get(position).imageSrc);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem,holder.mProgressBar);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView mTitle;
        public final TextView mContentView;
        public final ImageView mImageView;
        public final ProgressBar mProgressBar;
        public JsoupSingleton.NewsItem mItem;
        public final TextView mDate;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mTitle = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.text);
            mImageView = (ImageView) view.findViewById(R.id.item_image);
            mProgressBar = (ProgressBar) view.findViewById(R.id.news_image_loader);
            mDate = (TextView) view.findViewById(R.id.date1);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";

        }


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        ProgressBar mProgressBar;

        public DownloadImageTask(ImageView bmImage, ProgressBar mProgressBar) {
            this.bmImage = bmImage;
            this.mProgressBar = mProgressBar;

        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            bmImage.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
