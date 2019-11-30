package com.capstone.notekeeper.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.notekeeper.Models.NoteBookModel;
import com.capstone.notekeeper.R;

public class StudyMaterialDetails extends AppCompatActivity {

    private TextView mNotesTitle, mNotesDesc;
    private ImageView mDownloadButton, mShareButton, mRatingButton, mOwnerDetails;
    private Button mReadNowbtn;
    private Toolbar mToolbar;
    private NoteBookModel mNoteBookModel;
    private LinearLayout mRatingCArd, mProfileCArd, mCombineCArd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material_details);
        mToolbar = findViewById(R.id.book_tool_bar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        }
        Intent intent = getIntent();
        mNoteBookModel = (NoteBookModel) intent.getSerializableExtra("pdf");
        mNotesTitle = findViewById(R.id.book_title);
        mNotesDesc = findViewById(R.id.book_description);
        mReadNowbtn = findViewById(R.id.book_read_now_btn);
        mRatingButton = findViewById(R.id.book_add_rating);
        mOwnerDetails = findViewById(R.id.auther_btn);
        mDownloadButton = findViewById(R.id.book_download_Btn);
        mRatingCArd = findViewById(R.id.rating_Card);
        mProfileCArd = findViewById(R.id.profile_card);
        mCombineCArd = findViewById(R.id.combinecard);

        Animation bounce = AnimationUtils.loadAnimation(this,R.anim.bounce);
        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mDownloadButton.startAnimation(bounce);
                downloaderCustom(mNoteBookModel);
            }
        });
        mRatingButton.setOnClickListener(view -> {
             mRatingButton.startAnimation(bounce);
            if (mProfileCArd.getVisibility() == View.VISIBLE) {
                mProfileCArd.setVisibility(View.GONE);
            }
            mCombineCArd.setVisibility(View.VISIBLE);
            mRatingCArd.setVisibility(View.VISIBLE);
        });
        mOwnerDetails.setOnClickListener(view -> {
            mOwnerDetails.startAnimation(bounce);
            if (mRatingCArd.getVisibility() == View.VISIBLE) {
                mRatingCArd.setVisibility(View.GONE);
            }
            mProfileCArd.setVisibility(View.VISIBLE);
            mCombineCArd.setVisibility(View.VISIBLE);

        });
        findViewById(R.id.close_main_card).setOnClickListener(v -> {
            mCombineCArd.setVisibility(View.GONE);
            mRatingCArd.setVisibility(View.GONE);
            mProfileCArd.setVisibility(View.GONE);
        });
        mReadNowbtn.setOnClickListener(pView -> {
            Intent intentbook = new Intent(StudyMaterialDetails.this, NotesPDFViewActivity.class);
            if (mNoteBookModel != null && mNoteBookModel.getBookLink() != null) {
                intentbook.putExtra("book_title", mNoteBookModel.getBookTitle());
                intentbook.putExtra("book_url", mNoteBookModel.getBookLink());
                startActivity(intentbook);
            }
        });
        setBookDetails();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(android.R.id.home == item.getItemId()){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void setBookDetails() {
        if (mNoteBookModel != null) {
            mNotesTitle.setText(mNoteBookModel.getBookTitle());
            mNotesDesc.setText(mNoteBookModel.getBookDescription());
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void downloaderCustom(NoteBookModel fileUrl) {
        long output = 0;
        Toast.makeText(this, "Download Started...", Toast.LENGTH_SHORT).show();
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl.getBookLink()));
        request.setTitle(fileUrl.getBookTitle());
        request.setDescription(fileUrl.getBookDescription());
        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "NoteKeeper");
        if (downloadManager != null) {
            output = downloadManager.enqueue(request);
        }
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadcomplete, intentFilter);
    }

    BroadcastReceiver downloadcomplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                Toast.makeText(context, "Download Complete.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_details_menu, menu);
        return true;
    }
}
