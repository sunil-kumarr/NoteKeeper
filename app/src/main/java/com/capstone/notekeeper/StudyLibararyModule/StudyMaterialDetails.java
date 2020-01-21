package com.capstone.notekeeper.StudyLibararyModule;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.notekeeper.CommonFiles.Utils;
import com.capstone.notekeeper.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class StudyMaterialDetails extends AppCompatActivity {

    private TextView mNotesTitle, mNotesDesc, mNotesAuthor, mNotesTime, mRatingMain, mNoRatingText;
    private ImageView mDownloadButton, mShareButton, mRatingButton, mAuthorImage, mOwnerDetails;
    private Button mReadNowbtn, mSubmitRating;
    private RatingBar mRatingBar;
    private EditText mRatingReviewComment;
    private Toolbar mToolbar;
    private NoteBookModel mNoteBookModel;
    private LinearLayout mRatingCArd, mProfileCArd, mCombineCArd;
    private RecyclerView mRatingViewList;

    private FirebaseUser mCurrentUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private RatingAdapter ratingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_material_details);
        mToolbar = findViewById(R.id.book_tool_bar);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

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
        mShareButton = findViewById(R.id.book_share_link);
        mNotesAuthor = findViewById(R.id.authorName);
        mAuthorImage = findViewById(R.id.authorImage);
        mNotesTime = findViewById(R.id.uploadTime);
        mRatingReviewComment = findViewById(R.id.edt_review_text);
        mRatingBar = findViewById(R.id.book_rating_bar);
        mSubmitRating = findViewById(R.id.submit_rating_button);
        mRatingMain = findViewById(R.id.rating_main);
        mNoRatingText = findViewById(R.id.no_rating);
        mRatingViewList = findViewById(R.id.recycler_rating);

        ratingAdapter = new RatingAdapter(this);
        mRatingViewList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
        mRatingViewList.setAdapter(ratingAdapter);
        mRatingViewList.setItemAnimator(new DefaultItemAnimator());

        Animation bounce = AnimationUtils.loadAnimation(this, R.anim.bounce);
        mDownloadButton.setOnClickListener(pView -> {
            mDownloadButton.startAnimation(bounce);
            downloaderCustom(mNoteBookModel);
        });
        mShareButton.setOnClickListener(view -> {
            String bookname = String.valueOf(mNotesTitle.getText());
            Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                    + "/drawable/" + "books");
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Read this awesome notes " + bookname + " on Notekeeper app download now from playstore");
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("*/*");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(shareIntent, "send"));
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
        setRatingFromDatabase();
        getRatingAndSaveToDatabase();
        setBookDetails();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCurrentUser = mFirebaseAuth.getCurrentUser();
        if (mCurrentUser == null) {
            finish();
        }
    }

    void getRatingAndSaveToDatabase() {
        mSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mSubmitRating.setVisibility(View.GONE);
                String comment = String.valueOf(mRatingReviewComment.getText());
                float rating = mRatingBar.getRating();
                if (mCurrentUser != null) {
                    String userid = mCurrentUser.getUid();
                    String name = mCurrentUser.getDisplayName();
                    long time = System.currentTimeMillis();
                    String image = String.valueOf(mCurrentUser.getPhotoUrl());
                    RatiingBookModel ratingBookModel = new RatiingBookModel(userid, image, name, comment, time, rating);
                    if (mNoteBookModel != null) {
                        String bookId = mNoteBookModel.getNotebookID();
                        String course = mNoteBookModel.getBookCourse();
                        DatabaseReference reference = mFirebaseDatabase.getReference("notesRating")
                                .child(bookId);
                        DatabaseReference bookRef = mFirebaseDatabase.getReference("notes")
                                .child(course).child(bookId);
                        reference.child(String.valueOf(time)).setValue(ratingBookModel)
                                .addOnSuccessListener(StudyMaterialDetails.this, new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void pVoid) {
                                        long totalcount = mNoteBookModel.getTotalRatings();
                                        float totalrating = mNoteBookModel.getRating();
                                        totalrating += rating;
                                        totalcount++;
                                        Map<String, Object> update = new HashMap<>();
                                        update.put("rating", totalrating);
                                        update.put("totalRatings", totalcount);
                                        bookRef.updateChildren(update);
                                        Toast.makeText(StudyMaterialDetails.this, "Thank you for review.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception pE) {
                                Toast.makeText(StudyMaterialDetails.this, "Failed sorry!", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }
        });
    }

    void setRatingFromDatabase() {
        if (mNoteBookModel != null) {
            String bookId = mNoteBookModel.getNotebookID();
            DatabaseReference reference = mFirebaseDatabase.getReference("notesRating")
                    .child(bookId);
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {
                    if (pDataSnapshot.exists()) {
                        mNoRatingText.setVisibility(View.GONE);
                        RatiingBookModel model = pDataSnapshot.getValue(RatiingBookModel.class);
                        if (model != null && mCurrentUser != null &&
                                model.getUserID().equals(mCurrentUser.getUid())) {
                            mRatingBar.setRating(model.getGivenRating());
                            mRatingBar.setEnabled(false);
                            if (model.getReviewComment() != null) {
                                mRatingReviewComment.setText(model.getReviewComment());
                            }
                            mRatingReviewComment.setEnabled(false);
                            mSubmitRating.setVisibility(View.GONE);
                        }
                        ratingAdapter.addRatingModel(model);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {
                    ratingAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot pDataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot pDataSnapshot, @Nullable String pS) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError pDatabaseError) {

                }
            });
        }
    }

    void setBookDetails() {
        if (mNoteBookModel != null) {
            mNotesTitle.setText(mNoteBookModel.getBookTitle());
            mNotesDesc.setText(mNoteBookModel.getBookDescription());
            if (mNoteBookModel.getAuthorImage() != null) {
                Picasso.get()
                        .load(mNoteBookModel.getAuthorImage())
                        .placeholder(R.drawable.ic_general_user_image)
                        .into(mAuthorImage);
            }
            if (mNoteBookModel.getAuthorName() != null)
                mNotesAuthor.setText(mNoteBookModel.getAuthorName());
            float rated = 0;
            if(mNoteBookModel.getTotalRatings()!=0) {
                rated = mNoteBookModel.getRating() / mNoteBookModel.getTotalRatings();
            }
            mRatingMain.setText(String.format("%.2f",rated));
            mNotesTime.setText(String.format("Uploaded On: %s", Utils.getFormatedDate(mNoteBookModel.getTimsetamp())));
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

}
