package com.capstone.notekeeper.AskQuestionModule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.CommonFiles.Utils;
import com.capstone.notekeeper.R;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionAdapterViewHolder> {

    public static interface QuestionAdapterDelegate {
        public void onItemClicked(int position, List<Question> queries);
    }

    /* private fields */
    private List<Question> mQuestions;

    // References to delegate objects
    private WeakReference<QuestionAdapterDelegate> mDelegate;

    public QuestionAdapter() {
        mQuestions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        mQuestions.add(question);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuestionAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_show_layout, parent, false);
        return new QuestionAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(QuestionAdapterViewHolder holder, int position) {
        holder.update(position, mQuestions.get(position));
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }


    public QuestionAdapterDelegate getQuestionAdapterDelegate() {
        if (mDelegate == null) {
            return null;
        }
        return mDelegate.get();
    }

    public void setQuestionAdapterDelegate(QuestionAdapterDelegate delegate) {
        mDelegate = new WeakReference<QuestionAdapterDelegate>(delegate);
    }

    class QuestionAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView questionString;
        TextView timeStamp;
        TextView numAnswers,userName;
        CircleImageView userImage;
        int position;
        public QuestionAdapterViewHolder(View itemView) {
            super(itemView);
            questionString = itemView.findViewById(R.id.text_question_string);
            timeStamp = itemView.findViewById(R.id.text_question_time_stamp);
            numAnswers =  itemView.findViewById(R.id.answer_count);
            userImage =  itemView.findViewById(R.id.question_owner_image);
            userName = itemView.findViewById(R.id.question_owner_name);
            itemView.setOnClickListener(this);
        }

        void update(int position, Question question) {
            this.position = position;
            questionString.setText(question.getQuestionString());
            userName.setText(question.getmUserName());
            Picasso.get()
                    .load(question.getmUserImageUrl())
                    .fit()
                    .into(userImage);
            numAnswers.setText(String.format("%d Answers", question.getmNumberOfAnswers()));
            timeStamp.setText(String.format("Question Added at: %s",Utils.getFormatedDate(question.getmTimeStamp()) ));
            //numAnswers.setText("Number of answers: " + question.getNumberOfAnswers());
            //userImage.setImageResource(question.getUserImageResId());
        }
        // Return a formatted date string (i.e. 1 Jan, 2000 ) from a Date object.

        // Only method of View.OnClickListener
        @Override
        public void onClick(View view) {
            if (getQuestionAdapterDelegate() != null) {
                getQuestionAdapterDelegate().onItemClicked(position, mQuestions);
            }
        }
    }
}
