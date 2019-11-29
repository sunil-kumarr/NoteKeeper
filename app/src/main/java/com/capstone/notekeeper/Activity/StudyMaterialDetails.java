package com.capstone.notekeeper.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.notekeeper.Models.NotesDetails;
import com.capstone.notekeeper.R;

public class StudyMaterialDetails extends AppCompatActivity {

    private TextView mNotesTitle,mNotesDesc,mDownloadButton,
            mShareButton,mRatingButton,mOwnerDetails;
    private Button mReadNowbtn;
    private Toolbar mToolbar;
    private NotesDetails notesDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material_details);
        mToolbar = findViewById(R.id.book_tool_bar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }
        Intent intent = getIntent();
        notesDetails = (NotesDetails) intent.getSerializableExtra("pdf");
        mNotesTitle = findViewById(R.id.book_title);
        mNotesDesc = findViewById(R.id.book_description);
        mReadNowbtn = findViewById(R.id.book_read_now_btn);
        mReadNowbtn.setOnClickListener(pView -> {
               Intent intentbook = new Intent(StudyMaterialDetails.this,NotesPDFViewActivity.class);
               if (notesDetails!=null && notesDetails.getFileLink()!=null) {
                   intentbook.putExtra("book_title",notesDetails.getTitle());
                   intentbook.putExtra("book_url", notesDetails.getFileLink());
                   startActivity(intentbook);
               }
        });
        setBookDetails();
    }
    void setBookDetails(){
        if(notesDetails!=null){
            mNotesTitle.setText(notesDetails.getTitle());
            mNotesDesc.setText(notesDetails.getDescription());
        }else{
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_details_menu,menu);
        return true;
    }
}
