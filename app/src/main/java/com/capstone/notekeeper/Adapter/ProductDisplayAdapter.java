package com.capstone.notekeeper.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Fragments.BuyFragment;
import com.capstone.notekeeper.Models.Product;
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
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Product productCurrent = mProducts.get(position);

    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

//            textViewName = itemView.findViewById(R.id.text_view_name);
//            textViewPrice = itemView.findViewById(R.id.text_view_price);
//            imageView = itemView.findViewById(R.id.image_view_upload);
//
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    BuyFragment buyFragment = new BuyFragment();
//                    Bundle bundle = new Bundle();
//                    int position = getAdapterPosition();
//                    Product current = mProducts.get(position);
//                    String name = current.getName();
//                    bundle.putInt("position", position);
//                    bundle.putString("name", name);
//                    bundle.putString("price", current.getPrice());
//
//                    if (imageView != null)
//                        bundle.putString("imageUrl", current.getImageUrl());
//                    else
//                        bundle.putString("imageUrl", null);
//                    bundle.putString("userName", current.getUserName());
//                    bundle.putString("date", current.getDate());
//                    bundle.putString("desc", current.getDesc());
//                    bundle.putString("email", current.getEmail());
//                    bundle.putString("key", current.getKey());
//                    /*if (((BitmapDrawable) imageView.getDrawable()).getBitmap() != null)
//                        bundle.putParcelable("bitmapImage", ((BitmapDrawable) imageView.getDrawable()).getBitmap());
//                    else
//                        bundle.putParcelable("bitmapImage", null);*/
//                    buyFragment.setArguments(bundle);
//
//
//                    ((FragmentActivity) mContext)
//                            .getSupportFragmentManager()
//                            .beginTransaction().replace(R.id.content_frame, buyFragment)
//                            .addToBackStack(null).commit();
//
//
//                }
//            });
        }


    }
}
