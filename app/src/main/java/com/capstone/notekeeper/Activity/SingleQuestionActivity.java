package com.capstone.notekeeper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.notekeeper.Adapter.AnswerAdapter;
import com.capstone.notekeeper.Fragments.AddAnswerBottomSheet;
import com.capstone.notekeeper.Fragments.AddQuestionBottomSheet;
import com.capstone.notekeeper.Models.Answer;
import com.capstone.notekeeper.Models.Question;
import com.capstone.notekeeper.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class SingleQuestionActivity extends AppCompatActivity implements AddAnswerBottomSheet.SendAnswerToActivity {

    public static final String TAG = "SingleQuestionActivity";
    private TextView mQuestionString,mQuestionWriter,mQuestionTime;
    private CircleImageView mWriterImage;
    private FloatingActionButton mAddAnswerBtn,mGoBack;
    private int position;
    private AnswerAdapter mAnswerAdapter;
    private DatabaseReference mAnswersReference,questionRef;
    private FirebaseUser mCurrentUser;
    private String mQuestionId;
    private RecyclerView mAnswerRecyclerView;
    private LinearLayout mNoAnswerDisplay;
    private Integer numberOfAnswers=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_question);
        mAnswerRecyclerView = findViewById(R.id.answer_recycler_view);
        mQuestionString = findViewById(R.id.question_text);
        mQuestionWriter = findViewById(R.id.question_owner_name);
        mAddAnswerBtn = findViewById(R.id.add_answer_button);
        mNoAnswerDisplay = findViewById(R.id.no_answer_container);
        mQuestionTime = findViewById(R.id.text_question_time);
        mWriterImage = findViewById(R.id.question_owner_image);
        mGoBack = findViewById(R.id.go_back);
        Intent intent = getIntent();
        if(intent!=null) {
            mQuestionId = intent.getStringExtra("question_id_key");
            addQuestionData(mQuestionId);
            mAnswersReference = FirebaseDatabase.getInstance().getReference("answers").child(mQuestionId);
        }
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        mAnswerAdapter = new AnswerAdapter();
        mAnswerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAnswerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAnswerRecyclerView.setAdapter(mAnswerAdapter);
        mAddAnswerBtn.setOnClickListener(view -> {
            showEditDialog();
        });
        mGoBack.setOnClickListener(view -> {
            finish();
        });
        mAnswersReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Answer answer = dataSnapshot.getValue(Answer.class);
                mNoAnswerDisplay.setVisibility(View.GONE);
                mAnswerRecyclerView.setVisibility(View.VISIBLE);
                mAnswerAdapter.addAnswer(answer);
                numberOfAnswers++;
                Map<String,Object> update = new HashMap<>();
                update.put("numberOfAnswers",numberOfAnswers);
                questionRef.updateChildren(update);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void addQuestionData(String questionId){
        questionRef = FirebaseDatabase.getInstance().getReference("questions").child(questionId);
         questionRef.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 Question question = dataSnapshot.getValue(Question.class);
                 if(question!=null) {
                     mQuestionString.setText(question.getQuestionString());
                     mQuestionWriter.setText(question.getmUserName());
                     mQuestionTime.setText(String.format("Question Added at: %s", formatDate(new Date(question.getTimeStamp()))));
                     Picasso.get()
                             .load(question.getmUserImageUrl())
                             .fit()
                             .into(mWriterImage);
                     if(question.getmNumberOfAnswers()>0){
                         mNoAnswerDisplay.setVisibility(View.GONE);
                         mAnswerRecyclerView.setVisibility(View.VISIBLE);
                     }
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
    }
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        return dateFormat.format(date);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddAnswerBottomSheet addAnswerBottomSheet = new AddAnswerBottomSheet();
        addAnswerBottomSheet.show(fm,addAnswerBottomSheet.getTag());
    }

    @Override
    public void onAnswerComplete(String answer) {
        if (answer.isEmpty()) {
            Toast.makeText(this, "Please enter an answer...", Toast.LENGTH_SHORT).show();
        } else {

            if(mAnswersReference!=null && mCurrentUser!=null &&questionRef!=null) {
                String answerId = mAnswersReference.push().getKey();
                String userName = mCurrentUser.getDisplayName();
                String userId = mCurrentUser.getUid();
                String userImageUrl = mCurrentUser.getPhotoUrl().toString();
                Answer answerTab = new Answer( answerId, answer, (long) System.currentTimeMillis(), userId,userImageUrl,userName);
                mAnswersReference.child(answerId).setValue(answerTab);
                Toast.makeText(this, "Answer added!", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
