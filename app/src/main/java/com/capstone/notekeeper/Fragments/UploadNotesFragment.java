package com.capstone.notekeeper.Fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Adapter.CourseAdapter;
import com.capstone.notekeeper.Models.CourseType;
import com.capstone.notekeeper.Models.NoteBookModel;
import com.capstone.notekeeper.R;
import com.capstone.notekeeper.authentication.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class UploadNotesFragment extends Fragment  {
    private Context mContext;
    private Button mFileChoose, mUploadFile,mNextStep;
    private TextView mFileNameShow;
    private EditText mFileTitle, mFileDescription;
    private ProgressBar mProgressBar;
    private CardView mProgressShowCard;
    private RecyclerView mRecyclerView;
    private ConstraintLayout mStepTwo,mStepOne;

    private NoteBookModel mNoteBook;
    private FirebaseStorage firebaseStorage;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mCurrentUser;
    private Uri fileUri;

    private String filelink, fileName, title, description;
    private ArrayList<CourseType> Courses;
    private String mCategory;
    private CourseAdapter myAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_upload_notes, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mCurrentUser = mFirebaseAuth.getCurrentUser();
        if (mCurrentUser == null) {
            startActivity(new Intent(mContext, LoginActivity.class));
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFileChoose = view.findViewById(R.id.choose_file_btn);
        mUploadFile = view.findViewById(R.id.upload_file_btn);
        mFileNameShow = view.findViewById(R.id.file_name);
        mProgressBar = view.findViewById(R.id.file_upload_progressbar);
        mFileTitle = view.findViewById(R.id.edt_course_name);
        mFileDescription = view.findViewById(R.id.edt_course_description);
        mProgressShowCard = view.findViewById(R.id.upload_card_container);
        mRecyclerView = view.findViewById(R.id.category_rec_View);
        mNextStep = view.findViewById(R.id.upload_go_to_next_step);
        mStepOne = view.findViewById(R.id.course_category_container);
        mStepTwo =view.findViewById(R.id.course_details_container);

        createCourse();
         myAdapter = new CourseAdapter(Courses, mContext,"upload");
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mFileChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseDocuments();
            }
        });
        mUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataAndStartUpload();
            }
        });
        mNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                mStepOne.setVisibility(View.GONE);
                mStepTwo.setVisibility(View.VISIBLE);
                mCategory = myAdapter.getSelectedItem();
               // Toast.makeText(mContext, ""+myAdapter.getSelectedItem(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createCourse() {
        Courses = new ArrayList<>();
        String[] coursename = mContext.getResources().getStringArray(R.array.course_titles);
        Courses.add(new CourseType(coursename[0], R.drawable.ic_browser));
        Courses.add(new CourseType(coursename[1], R.drawable.ic_information));
        Courses.add(new CourseType(coursename[2], R.drawable.ic_mechanic));
        Courses.add(new CourseType(coursename[3], R.drawable.ic_electrician));
        Courses.add(new CourseType(coursename[4], R.drawable.ic_engineer));
        Courses.add(new CourseType(coursename[5], R.drawable.ic_bio));
        Courses.add(new CourseType(coursename[6], R.drawable.ic_chemical));
        Courses.add(new CourseType(coursename[7], R.drawable.ic_leaf));
        Courses.add(new CourseType(coursename[8], R.drawable.ic_pie));
        Courses.add(new CourseType(coursename[9], R.drawable.ic_dress));
        Courses.add(new CourseType(coursename[10], R.drawable.ic_balance));
        Courses.add(new CourseType(coursename[11], R.drawable.ic_idea));
        Courses.add(new CourseType(coursename[12], R.drawable.ic_idea));
        Courses.add(new CourseType(coursename[13], R.drawable.ic_idea));
        Courses.add(new CourseType(coursename[14], R.drawable.ic_idea));
    }
    private void getDataAndStartUpload() {
        title = mFileTitle.getText().toString();
        description = mFileDescription.getText().toString();
        mFileTitle.setEnabled(false);
        mFileDescription.setEnabled(false);
        mFileChoose.setEnabled(false);
        Toast.makeText(mContext, "g"+mCategory, Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            Toast.makeText(mContext, "Invalid /Incomplete input", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fileUri != null) {
            mProgressShowCard.setVisibility(View.VISIBLE);
            UploadFiles(fileUri);
        }

    }

    private void UploadFiles(Uri pFileUri) {
        if (pFileUri != null) {
            StorageReference storageReference = firebaseStorage.getReference();
            StorageReference ref = storageReference.child("files/" + fileName);
            ref.putFile(pFileUri)
                    .addOnSuccessListener(downloadUri -> {
                        ref.getDownloadUrl().addOnSuccessListener(uri -> {
                            filelink = uri.toString();
                            addNoteBookToFirebase(filelink);
                        });
                    })
                    .addOnProgressListener(pTaskSnapshot -> {
                        double progress = (100.0 * pTaskSnapshot.getBytesTransferred()) / pTaskSnapshot.getTotalByteCount();
                        mProgressBar.setProgress((int) progress);
                    })
                    .continueWithTask(task -> {
                        if (!task.isSuccessful()) {
                            throw Objects.requireNonNull(task.getException());
                        }
                        return ref.getDownloadUrl();
                    })
                    .addOnFailureListener(exception -> {
                        Toast.makeText(mContext, "Upload Failed!", Toast.LENGTH_SHORT).show();
                        mProgressShowCard.setVisibility(View.GONE);
                    });
        }
    }

    private void addNoteBookToFirebase(String fileUrl) {
        String uid = mCurrentUser.getUid();
        String name = mCurrentUser.getDisplayName();
        mCategory = myAdapter.getSelectedItem();
        mNoteBook = new NoteBookModel(name,uid, description,title, filelink, System.currentTimeMillis(), 0);
        DatabaseReference databaseReference = firebaseDatabase.getReference("notes").child(mCategory);
        String notebookID = databaseReference.push().getKey();
        if(notebookID!=null) {
            databaseReference.child(notebookID).setValue(mNoteBook).addOnSuccessListener(aVoid -> {
                Toast.makeText(mContext, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                changeLayoutSettigns();
            });
        }
    }

    private void changeLayoutSettigns() {
        mProgressShowCard.setVisibility(View.GONE);
        mStepTwo.setVisibility(View.GONE);
        mStepOne.setVisibility(View.VISIBLE);
        mFileTitle.setText("");
        mFileTitle.setEnabled(true);
        mFileDescription.setEnabled(true);
        mFileChoose.setEnabled(true);
        mFileDescription.setText("");
        mFileNameShow.setText("upload study material(* only PDF format)");
        mFileTitle.requestFocus();
    }

    private static final int FILE_SELECT_CODE = 0;

    private void browseDocuments() {
        String[] mimeTypes = {"application/pdf"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(
                Intent.createChooser(intent, "Select a File to Product"),
                FILE_SELECT_CODE);
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = 0;
            if (result != null) {
                cut = result.lastIndexOf('/');
            }
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                fileUri = data.getData();
                Log.d(TAG, "File Uri: " + fileUri.toString());
                try {
                    fileName = getFileName(fileUri);
                    mFileNameShow.setText(fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}