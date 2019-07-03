package com.esphereal.bair.neighborhood

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.ProgressBar

class NewsDetails : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        var webView = findViewById(R.id.webView) as WebView
        var loader = findViewById(R.id.loader) as ProgressBar
        val intent = getIntent()
        var url = intent.getStringExtra("url")
        webView.getSettings().setBuiltInZoomControls(true)
        webView.getSettings().setDisplayZoomControls(false)
        webView.loadUrl(url)
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                Log.d("DEBUG", "progress = " + progress.toString());
                if (progress == 100) {
                    loader.setVisibility(View.GONE)

                } else {
                    loader.setVisibility(View.VISIBLE)
                }
            }
        })

        val button = findViewById<Button>(R.id.news_details_back_button)
        button.setOnClickListener {
            onBackPressed()

        }


    }
}
