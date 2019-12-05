package com.capstone.notekeeper.BuyAndSellModule;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.capstone.notekeeper.CommonFiles.Utils;
import com.capstone.notekeeper.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {

    private static final int CALL_REQUEST = 254;
    private Toolbar mToolbar;
    private ImageView mProductImage;
    private TextView mProductPrice, mProductDesc, mProcutPlace, mProductDate, mProductTitle, mProductPlace;
    private Button mCallOwner, mEmailOwner;

    private FirebaseDatabase mFirebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        mToolbar = findViewById(R.id.product_toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_clear_white);
        }
        mProductImage = findViewById(R.id.product_image);
        mProductTitle = findViewById(R.id.product_title);
        mProductDesc = findViewById(R.id.product_description);
        mProductDate = findViewById(R.id.product_posting_date);
        mProductPlace = findViewById(R.id.product_posting_Address);
        mProductPrice = findViewById(R.id.product_price);
        mCallOwner = findViewById(R.id.button_call_owner);
        mEmailOwner = findViewById(R.id.button_email_owner);

        Intent productIntent = getIntent();
        String productId = productIntent.getStringExtra("product_id");
        if (productId != null) {
            getProductDataAndDisplay(productId);
        }


    }

    private void setEmailOwner(String pEmailOwner){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse(pEmailOwner));
        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }
    private void setCallOwner(String pUserPhone) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    CALL_REQUEST);
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8116227044"));
            startActivity(intent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CALL_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "8116227044"));
                    if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    Activity#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
    private void getProductDataAndDisplay(String pProductId) {
        mFirebaseDatabase.getReference("products_list").child(pProductId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot pDataSnapshot) {
                        ProductModel productModel = pDataSnapshot.getValue(ProductModel.class);
                        if(productModel!=null){
                            mProductPrice.setText("Rs."+productModel.getProductPrice());
                            mProductTitle.setText(productModel.getProductName());
                            mProductDate.setText(Utils.getFormatedDate(productModel.getPostTimeStamp()));
                            mProductPlace.setText(productModel.getProductLocation());
                            mProductDesc.setText(productModel.getProductDesc());
                            Picasso.get()
                                    .load(productModel.getProductImageUrl())
                                    .placeholder(R.color.grey_100)
                                    .into(mProductImage);
                            mCallOwner.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View pView) {
                                    setCallOwner(productModel.getUserPhone());
                                }
                            });
                            mEmailOwner.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View pView) {
                                    setEmailOwner(productModel.getUserEmail());
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError pDatabaseError) {

                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
