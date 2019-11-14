package com.capstone.notekeeper.Fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstone.notekeeper.Models.Product;
import com.capstone.notekeeper.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class SellFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_CAMERA_REQUEST = 0;

    private Context mContext;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mEditTextFileName;
    private EditText mEditTextFilePrice;
    private ImageView mImageView;
    private TextView mDescription;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    private String fileLink,productName,productDesc,productPrice;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser mCurrentUser;
    private StorageTask mUploadTask;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sell, container, false);
        getActivity().setTitle("Sell Products");
        mButtonChooseImage = v.findViewById(R.id.choose_file_btn);
        mButtonUpload = v.findViewById(R.id.post_button);
        mEditTextFileName = v.findViewById(R.id.edt_product_name);
        mEditTextFilePrice = v.findViewById(R.id.edt_product_price);
        mImageView = v.findViewById(R.id.product_image);
        mDescription = v.findViewById(R.id.edt_product_description);
        mProgressBar = v.findViewById(R.id.upload_progress);
        mStorageRef = FirebaseStorage.getInstance().getReference("products_list");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("products_list");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Posting product ...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Posting product online...", Toast.LENGTH_SHORT).show();
                    productName = mEditTextFileName.getText().toString();
                    productDesc = mDescription.getText().toString();
                    productPrice = mEditTextFilePrice.getText().toString();
                    if(TextUtils.isEmpty(productName)|| TextUtils.isEmpty(productDesc)||TextUtils.isEmpty(productPrice)){
                        Toast.makeText(mContext, "Invalid Inputs!!", Toast.LENGTH_SHORT).show();
                    }else {
                        uploadImage();
                    }
                }
            }
        });
        return v;
    }

    private void addDataToFirebase(String fileLink) {
            String productId =  mDatabaseRef.push().getKey();
            if(mCurrentUser!=null && fileLink!=null){
                String userId = mCurrentUser.getUid();
                String userName = mCurrentUser.getDisplayName();
                String userEmail = mCurrentUser.getEmail();
                String phoneNumber = mCurrentUser.getPhoneNumber();
                String userImageUrl = Objects.requireNonNull(mCurrentUser.getPhotoUrl()).toString();
                Product uploadProduct = new Product(userEmail,userName,phoneNumber,userImageUrl,userId
                 ,productName,productDesc,productPrice,fileLink,productId,System.currentTimeMillis());
                mDatabaseRef.child(productId).setValue(uploadProduct)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(mContext, "Product posted online!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mContext, "Failed !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                Toast.makeText(mContext, "Error while posting", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onStart() {
        super.onStart();
//        NetworkConnection networkConnection = new NetworkConnection();
//        if (networkConnection.isConnectedToInternet(getActivity())
//                || networkConnection.isConnectedToMobileNetwork(getActivity())
//                || networkConnection.isConnectedToWifi(getActivity())) {
//
//        } else {
//            networkConnection.showNoInternetAvailableErrorDialog(getActivity());
//            return;
//        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadImage() {
        if (mImageUri != null) {
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mProgressBar.setProgress(0);
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(mContext, "Posting...", Toast.LENGTH_SHORT).show();
                                    fileLink =  uri.toString();
                                    addDataToFirebase(fileLink);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(mContext, "Failed to post!", Toast.LENGTH_SHORT).show();
                                }
                            });
                         }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}
