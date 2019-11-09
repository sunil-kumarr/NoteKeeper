package com.capstone.notekeeper.Fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Adapter.CourseAdapter;
import com.capstone.notekeeper.Models.CourseType;
import com.capstone.notekeeper.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class UploadNotesFragment extends Fragment {
    private Context mContext;
    private Button mFileChoose,mUploadFile;
    private TextView mFileNameShow;
    private EditText mFileName,mFileDescription;
    private AppCompatSpinner appCompatSpinner;
    private FirebaseStorage firebaseStorage;

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
        Objects.requireNonNull(getActivity()).setTitle("Upload study material");
        mFileChoose = view.findViewById(R.id.choose_file_btn);
        mFileNameShow = view.findViewById(R.id.file_name);
        appCompatSpinner = view.findViewById(R.id.branchNameSpinner);
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
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(mContext,
                android.R.layout.simple_spinner_dropdown_item,
                branchname);
        appCompatSpinner.setAdapter(spinnerArrayAdapter);
        firebaseStorage = FirebaseStorage.getInstance();
        mFileChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });
    }
    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(mContext, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_SELECT_CODE) {
            if (resultCode == RESULT_OK) {
                // Get the Uri of the selected file
                Uri uri = data.getData();
                Log.d(TAG, "File Uri: " + uri.toString());
                // Get the path
                String path = null;
                try {
                     Upload_DownloadUrl(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Upload_DownloadUrl(Uri pFileUri) throws Exception {
        Uri fileUri = null;
        if (pFileUri != null){
            fileUri = FilePathNameExtractor(pFileUri);
            if(fileUri!=null){
                mFileNameShow.setText(fileUri.toString());
            }
        }
        final StorageReference ref = firebaseStorage.getReference().child("NoteKeeper/"+fileUri);
        assert pFileUri != null;
//        ref.putFile(pFileUri)
//                .addOnProgressListener(pTaskSnapshot -> {
////                        mProgressBar.setVisibility(View.VISIBLE);
//                    double progress = (100.0 * pTaskSnapshot.getBytesTransferred()) / pTaskSnapshot.getTotalByteCount();
////                        mProgressBar.setProgress((int) progress);
////                        String s = new DecimalFormat("##").format(progress);
//                })
//                .continueWithTask(task -> {
//                    if (!task.isSuccessful()) {
//                        throw task.getException();
//                    }
//                    return ref.getDownloadUrl();
//                })
//                .addOnSuccessListener(downloadUri -> {
//
////                        currentDeal.setImageUrl(downloadUri.toString());
////                        mProgressBar.setVisibility(View.GONE);
//                    Log.d("Firebase url", "" + downloadUri.toString());
//                })
//                .addOnFailureListener(exception -> {
//                });
    }

    private Uri FilePathNameExtractor(Uri pImageUri) {
        String path = pImageUri.getLastPathSegment();
        assert path != null;
        String filename = path.substring(path.lastIndexOf("/") + 1);
        String file;
//        if (filename.indexOf(".") > 0) {
//            file = filename.substring(0, filename.lastIndexOf("."));
//        } else {
//            file = filename;
//        }
        return Uri.parse(filename);
        //   Log.d("DealsPath", filename);
    }
}