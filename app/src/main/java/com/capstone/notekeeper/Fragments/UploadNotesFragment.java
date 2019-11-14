package com.capstone.notekeeper.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import com.capstone.notekeeper.Models.NotesDetails;
import com.capstone.notekeeper.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    private Button mFileChoose,mUploadFile;
    private TextView mFileNameShow,uploadPer;
    private EditText mFileTitle,mFileAuthor,mFileDescription;
    private AppCompatSpinner branchList,notesType;
    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase firebaseDatabase;
    private ProgressBar mProgressBar;
    private NotesDetails mNoteBook;
    private Uri fileUri;
    private String filelink,fileName,author,title,description,typ;
    private ArrayList<String> types,branchname;

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
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        Objects.requireNonNull(getActivity()).setTitle("Upload study material");
        mFileChoose = view.findViewById(R.id.choose_file_btn);
        mUploadFile = view.findViewById(R.id.upload_file_btn);
        mFileNameShow = view.findViewById(R.id.file_name);
        uploadPer = view.findViewById(R.id.upload_percentage);
        notesType = view.findViewById(R.id.NotesTypeSpinner);
        mProgressBar = view.findViewById(R.id.file_upload_bar);
        mFileTitle = view.findViewById(R.id.edt_course_name);
        mFileAuthor = view.findViewById(R.id.edt_author_name);
        mFileDescription = view.findViewById(R.id.edt_course_description);
        branchList = view.findViewById(R.id.BranchSpinner);
        branchname = new ArrayList<>();
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
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_dropdown_item,
                branchname);
        branchList.setAdapter(spinnerArrayAdapter);
        types = new ArrayList<>();
        types.add("Handwritten");
        types.add("EBook");
        types.add("Computerized");
        types.add("Others");
        ArrayAdapter<String> noteTypeAdapter =
                new ArrayAdapter<>(mContext,android.R.layout.simple_spinner_dropdown_item,types);
        notesType.setAdapter(noteTypeAdapter);

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
        title = mFileTitle.getText().toString();
        author = mFileAuthor.getText().toString();
        description = mFileDescription.getText().toString();
        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(author)||TextUtils.isEmpty(description)){
            Toast.makeText(mContext, "Invalid /Incomplete input", Toast.LENGTH_SHORT).show();
            return;
        }
            if(fileUri!=null) {
                 UploadFiles(fileUri);
                 typ = types.get(notesType.getSelectedItemPosition());

            }

    }
    private void addDataToFirestore(String fileUrl){
        mNoteBook = new NotesDetails(author,typ,description,title,fileUrl);
        int branch = branchList.getSelectedItemPosition();
        String branchPath = branchname.get(branch);
       DatabaseReference databaseReference = firebaseDatabase.getReference("notes").child(branchPath);
       String keyId = databaseReference.push().getKey();
       databaseReference.child(keyId).setValue(mNoteBook).addOnSuccessListener(aVoid -> {
           Toast.makeText(mContext, "Successfully uploaded", Toast.LENGTH_SHORT).show();
           mFileTitle.setText("");
           mFileDescription.setText("");
           mFileAuthor.setText("");
           notesType.setSelection(0);
           mFileNameShow.setText("");
           uploadPer.setText("");
           mFileTitle.requestFocus();
       });
    }
    private static final int FILE_SELECT_CODE = 0;
    private void browseDocuments(){
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
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(
                Intent.createChooser(intent, "Select a File to Upload"),
                FILE_SELECT_CODE);
    }
    private String getFileName(Uri uri) {
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

    private void UploadFiles(Uri pFileUri) {
        if (pFileUri != null) {
            mProgressBar.setVisibility(View.VISIBLE);
            uploadPer.setVisibility(View.VISIBLE);
            StorageReference storageReference = firebaseStorage.getReference();
            StorageReference ref = storageReference.child("files/"+fileName);
            Toast.makeText(mContext, "Uploading files", Toast.LENGTH_SHORT).show();
            ref.putFile(pFileUri)
                    .addOnSuccessListener(downloadUri -> {
                         mProgressBar.setVisibility(View.GONE);
                         uploadPer.setText("Uploaded Successfully.");
                          //Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                          ref.getDownloadUrl().addOnSuccessListener(uri -> {
                             filelink = uri.toString();
                              addDataToFirestore(filelink);
                          });

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