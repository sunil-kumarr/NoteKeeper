package com.capstone.notekeeper.CommonFiles;

import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.capstone.notekeeper.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    ExpandableListView mListView;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_activity);
        Toolbar toolbar = findViewById(R.id.toolbasr_Faq);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
         mListView=findViewById(R.id.listview);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference("faq").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot pDataSnapshot) {
                List<FAQModelClass> list_faq = new ArrayList<>();
                for (DataSnapshot x : pDataSnapshot.getChildren()) {
                    if (x.exists()) {
                        FAQModelClass question = x.getValue(FAQModelClass.class);
                        list_faq.add(question);
                    }
                }
                mListView.post(() -> mListView.setAdapter(new AdapterListExpand(FAQActivity.this,list_faq)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError pDatabaseError) {

            }
        });

    }
}
