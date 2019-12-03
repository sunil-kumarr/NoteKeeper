package com.capstone.notekeeper.StudyLibararyModule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.CommonFiles.Utils;
import com.capstone.notekeeper.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.RatingHolder> {
    private Context mContext;
    private ArrayList<RatiingBookModel> mRatiingBookModels;

    public RatingAdapter(Context pContext) {
        mContext = pContext;
        mRatiingBookModels = new ArrayList<>();
    }

    @NonNull
    @Override
    public RatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.study_rating_tab, parent, false);
        return new RatingHolder(layout);
    }

    public void addRatingModel(RatiingBookModel pRatiingBookModel) {
        mRatiingBookModels.add(pRatiingBookModel);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RatingHolder holder, int position) {
         holder.update(position);
    }

    @Override
    public int getItemCount() {
//        Toast.makeText(mContext, "here", Toast.LENGTH_SHORT).show();
        return mRatiingBookModels.size();
    }

    class RatingHolder extends RecyclerView.ViewHolder {
        private ImageView imageRater;
        private TextView raterName, mRatingTime, mRating, mComment;

        public RatingHolder(@NonNull View itemView) {
            super(itemView);
            imageRater = itemView.findViewById(R.id.reviewer_image);
            raterName = itemView.findViewById(R.id.reviewer_name);
            mRatingTime = itemView.findViewById(R.id.review_date);
            mRating = itemView.findViewById(R.id.review_rating);
            mComment = itemView.findViewById(R.id.review_comment);
        }

        public void update(int pPosition) {
            RatiingBookModel model = mRatiingBookModels.get(pPosition);
            if(model!=null){
               raterName.setText(model.getUserName());
               mRatingTime.setText(Utils.getFormatedDate(model.getTimestamp()));
               mRating.setText(String.valueOf(model.getGivenRating()));
               if(model.getReviewComment()!=null){
                   mComment.setText(model.getReviewComment());
               }else{
                   mComment.setVisibility(View.GONE);
               }
               if(model.getUserImage()!=null){
                   Picasso.get()
                           .load(model.getUserImage())
                           .placeholder(R.drawable.ic_person_black_24dp)
                           .into(imageRater);
               }
            }
        }
    }
}
