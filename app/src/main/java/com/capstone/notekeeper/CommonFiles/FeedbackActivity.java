package com.capstone.notekeeper.CommonFiles;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.capstone.notekeeper.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {


    private EditText EdtMain,EdtSub;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        EdtMain=findViewById(R.id.feedback_text);
        EdtSub=findViewById(R.id.feedback_about);
        Toolbar toolbar = findViewById(R.id.feedback_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();


        findViewById(R.id.send_feedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= String.valueOf(EdtMain.getText());
                String about= String.valueOf(EdtSub.getText());
                if(text.length()>10 ) {
                    Map<String, Object> feed = new HashMap<>();
                    feed.put("improve", text);
                    feed.put("how", about);
                    firebaseDatabase.getReference("feedbacks")
                            .setValue(feed);
                    Snackbar.make(findViewById(R.id.snackbar_show),"Thank you so much.",Snackbar.LENGTH_LONG).show();
                    EdtMain.setText("");
                    EdtSub.setText("");
                }
                else{
                    EdtMain.setError("Length > 10");
                }
            }
        });


    }
}