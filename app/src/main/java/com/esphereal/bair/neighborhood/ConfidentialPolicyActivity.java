package com.esphereal.bair.funloot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class ConfidentialPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confidential_policy);

        findViewById(R.id.back_button_top_panel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ((TextView) findViewById(R.id.text_top_panel_with_back_button)).setText("Политика конфиденциальности");

        WebView webView = findViewById(R.id.confidential_policy_webview);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setTextZoom(50);
        webView.loadUrl("https://funloot.herokuapp.com");

    }
}
