package com.capstone.notekeeper.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.capstone.notekeeper.R;

public class NoteShowActivity extends AppCompatActivity {
   private WebView mNoteShow;
   private ProgressBar mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_show);
        Intent intent = getIntent();
        mProgress = findViewById(R.id.progress);
        mNoteShow= findViewById(R.id.showNotesWebview);
        mNoteShow.getSettings().setJavaScriptEnabled(true);
        mNoteShow.getSettings().setSupportZoom(true);
        String url = intent.getStringExtra("pdf");
        if(url!=null) {
            //Toast.makeText(this, ""+url, Toast.LENGTH_SHORT).show();
            mNoteShow.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);
            mNoteShow.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    mProgress.setVisibility(View.GONE);
                }
            });
        }else{
            finish();
        }
    }
}
