package com.capstone.notekeeper.StudyLibararyModule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.capstone.notekeeper.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class NotesPDFViewActivity extends AppCompatActivity {
    private PDFView pdfView;
    private ProgressBar mProgress;
    private Toolbar mToolbar;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;
    private FileDownloadTask mFileDownloadTask;
    private String book_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_show);
        mFirebaseStorage = FirebaseStorage.getInstance();
        mToolbar = findViewById(R.id.book_pdf_toolbar);
        setSupportActionBar(mToolbar);
        Intent intent = getIntent();
        book_url = intent.getStringExtra("book_url");
        String book_title = intent.getStringExtra("book_title");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            getSupportActionBar().setTitle(book_title);
        }

        pdfView = findViewById(R.id.pdfView);
        mProgress = findViewById(R.id.book_progress_bar_load);

        if (book_url != null) {
            Toast.makeText(this, "Opening File...", Toast.LENGTH_SHORT).show();
            mToolbar.setTitle(book_title);
            downloadFileSomewhere();

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFileDownloadTask.cancel();
    }

    void downloadFileSomewhere() {
        if (book_url != null) {
            mStorageReference = mFirebaseStorage.getReferenceFromUrl(book_url);
            try {
                File localFile = File.createTempFile("temp", "pdf");
                mFileDownloadTask = mStorageReference.getFile(localFile);
                mFileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot pTaskSnapshot) {
                        mProgress.setVisibility(View.GONE);
                        pdfView.fromFile(localFile)
                                .enableSwipe(true)
                                .swipeHorizontal(false)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .enableAnnotationRendering(false)
                                .password(null)
                                .scrollHandle(null)
                                .enableAntialiasing(true)
                                .spacing(0)
                                .onError(new OnErrorListener() {
                                    @Override
                                    public void onError(Throwable t) {
                                        Toast.makeText(NotesPDFViewActivity.this, "Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .onLoad(new OnLoadCompleteListener() {
                                    @Override
                                    public void loadComplete(int nbPages) {
                                        Toast.makeText(NotesPDFViewActivity.this, "pages: " + nbPages, Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .pageFitPolicy(FitPolicy.WIDTH)
                                .load();
                    }
                })
                        .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull FileDownloadTask.TaskSnapshot pTaskSnapshot) {
                                double progress = (100.0 * pTaskSnapshot.getBytesTransferred()) / pTaskSnapshot.getTotalByteCount();
                                mProgress.setProgress((int) progress);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception pE) {
                                Toast.makeText(NotesPDFViewActivity.this, "Failed to load!", Toast.LENGTH_SHORT).show();
                            }
                        });

            } catch (IOException pE) {
                pE.printStackTrace();
            }

        }
    }
}
