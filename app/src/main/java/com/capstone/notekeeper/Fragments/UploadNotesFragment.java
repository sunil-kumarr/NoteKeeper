package com.capstone.notekeeper.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import com.capstone.notekeeper.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class UploadNotesFragment extends Fragment  {
    private Context mContext;
    private Button mFileChoose,mUploadFile;
    private TextView mFileNameShow,uploadPer;
    private EditText mFileTitle,mFileAuthor,mFileDescription;
    private AppCompatSpinner branchList,notesType;
    private FirebaseStorage firebaseStorage;
    private ProgressBar mProgressBar;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseStorage = FirebaseStorage.getInstance();
        Objects.requireNonNull(getActivity()).setTitle("Upload study material");
        mFileChoose = view.findViewById(R.id.choose_file_btn);
        mUploadFile = view.findViewById(R.id.upload_file_btn);
        mFileNameShow = view.findViewById(R.id.file_name);
        uploadPer = view.findViewById(R.id.upload_percentage);
        branchList = view.findViewById(R.id.branchNameSpinner);
        notesType = view.findViewById(R.id.NotesTypeSpinner);
        mProgressBar = view.findViewById(R.id.file_upload_bar);
        mFileTitle = view.findViewById(R.id.edt_course_name);
        mFileAuthor = view.findViewById(R.id.edt_author_name);
        mFileDescription = view.findViewById(R.id.edt_course_description);
        ArrayList<String> branchname = new ArrayList<>();
        branchname.add("Computer Science Engineering");
        branchname.add("Information Technology Engineering");
        branchname.add("Civil Engineering");
        branchname.add("Mechanical Engineering");
        branchname.add("Electrical Engineering");
        branchname.add("Chemical Engineering");
        branchname.add("Quantitative Aptitude");
        branchname.add("Verbal Ability");
        branchname.add("Logical Reasoning");
        branchname.add("Verbal Reasoning");
        ArrayAdapter<String> spinnerArrayAdapter =
                new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, branchname);
        ArrayList<String> types = new ArrayList<>();
        types.add("Handwritten");
        types.add("EBook");
        types.add("Computerized");
        types.add("Others");
        ArrayAdapter<String> noteTypeAdapter =
                new ArrayAdapter<>(mContext,android.R.layout.simple_spinner_dropdown_item,types);
        notesType.setAdapter(noteTypeAdapter);
        branchList.setAdapter(spinnerArrayAdapter);
        firebaseStorage = FirebaseStorage.getInstance();
        mFileChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                browseDocuments();
            }
        });
        mUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadDataToFirebase();
            }
        });
    }

    private void uploadDataToFirebase() {
        String title = String.valueOf(mFileTitle.getText());
        String author = String.valueOf(mFileAuthor.getText());
        String description = String.valueOf(mFileDescription.getText());
        if(title!=null && author!=null && description!=null){

        }else{
            Toast.makeText(mContext, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
    }
    private static final int FILE_SELECT_CODE = 0;
    private void browseDocuments(){
        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};

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
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"),
                FILE_SELECT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                Uri uri = data.getData();
                Log.d(TAG, "File Uri: " + uri.toString());
                try {UploadFiles(uri);
                    String fileName = uri.getPath();
                    mFileNameShow.setText(fileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void UploadFiles(Uri pFileUri) {
        if (pFileUri != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            uploadPer.setVisibility(View.VISIBLE);
            StorageReference storageReference = firebaseStorage.getReference();
            StorageReference ref = storageReference.child("files/"+pFileUri.getLastPathSegment());
            Toast.makeText(mContext, "Uploading files", Toast.LENGTH_SHORT).show();
            ref.putFile(pFileUri)
                    .addOnSuccessListener(downloadUri -> {
                         mProgressBar.setVisibility(View.GONE);
                         uploadPer.setText("Uploaded Successfully.");
                          Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                          Log.d("Firebase url", "" + downloadUri.toString());
                       })
                    .addOnProgressListener(pTaskSnapshot -> {
                        double progress = (100.0 * pTaskSnapshot.getBytesTransferred()) / pTaskSnapshot.getTotalByteCount();
                        mProgressBar.setProgress((int) progress);
                        uploadPer.setText(String.format("Uploading File (%.2s/100)", String.valueOf(progress)));
                    })
                    .continueWithTask(task -> {
                        if (!task.isSuccessful()) {
                            throw Objects.requireNonNull(task.getException());
                        }
                        return ref.getDownloadUrl();
                    })
                    .addOnFailureListener(exception -> {
                        uploadPer.setText("Failed to Upload!");
                    });
        }
    }
}