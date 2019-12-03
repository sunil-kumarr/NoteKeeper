package com.capstone.notekeeper.BuyAndSellModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ProductDisplayAdapter extends RecyclerView.Adapter<ProductDisplayAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Product> mProducts;

    public ProductDisplayAdapter(Context context, List<Product> products) {
        mContext = context;
        mProducts = products;
    }

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
        return mProducts.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private TextView pName,pPrice,pDesc;
        private ImageView pImage;
        ImageViewHolder(View itemView) {
            super(itemView);
            pName = itemView.findViewById(R.id.show_product_name);
            pDesc = itemView.findViewById(R.id.show_product_desc);
            pPrice = itemView.findViewById(R.id.show_product_price);
            pImage = itemView.findViewById(R.id.show_product_image);
        }
        void update(int position) {
            Product product = mProducts.get(position);
             pName.setText(product.getProductName());
             pPrice.setText("Rs."+product.getProductPrice());
             pDesc.setText(product.getProductDesc());
            Picasso.get()
                    .load(product.getProductImageUrl())
                    .fit()
                    .into(pImage);
        }
    }
}
