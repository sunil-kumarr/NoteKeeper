package com.capstone.notekeeper.BuyAndSellModule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductDisplayAdapter extends RecyclerView.Adapter<ProductDisplayAdapter.ImageViewHolder> {
    private Context mContext;
    private List<ProductModel> mProductModels;

    public ProductDisplayAdapter(Context context, List<ProductModel> pProductModels) {
        mContext = context;
        mProductModels = pProductModels;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.product_list_item_tab, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.update(position);
    }

    @Override
    public int getItemCount() {
        return mProductModels.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private TextView pName, pPrice, pDesc;
        private ImageView pImage;
        private ConstraintLayout mContainer;

        ImageViewHolder(View itemView) {
            super(itemView);
            pName = itemView.findViewById(R.id.show_product_name);
            pDesc = itemView.findViewById(R.id.show_product_desc);
            pPrice = itemView.findViewById(R.id.show_product_price);
            pImage = itemView.findViewById(R.id.show_product_image);
            mContainer = itemView.findViewById(R.id.product_container);
        }

        void update(int position) {
            ProductModel productModel = mProductModels.get(position);
            pName.setText(productModel.getProductName());
            pPrice.setText("Rs." + productModel.getProductPrice());
            pDesc.setText(productModel.getProductDesc());
            Picasso.get()
                    .load(productModel.getProductImageUrl())
                    .fit()
                    .placeholder(R.color.grey_100)
                    .into(pImage);
            mContainer.setOnClickListener(view -> {
                Intent productIntent = new Intent(mContext, ProductDetailsActivity.class);
                productIntent.putExtra("product_id", productModel.getProductID());
                mContext.startActivity(productIntent);
            });
        }
    }
}
