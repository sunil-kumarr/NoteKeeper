package com.capstone.notekeeper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.notekeeper.Adapter.QuestionAdapter;
import com.capstone.notekeeper.Fragments.AddInputDialogFragment;
import com.capstone.notekeeper.Models.Question;
import com.capstone.notekeeper.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BloqueryActivity extends AppCompatActivity
        implements
        ChildEventListener,
        QuestionAdapter.QuestionAdapterDelegate,
        AddInputDialogFragment.AddInputDialogListener{

    /* Constants */
    public static final String TAG = "BloqueryActivity";
    public static final String EXTRA_QUESTION_ID_KEY = "question_id_key";
    public static final String EXTRA_QUESTION_STRING = "question_string";
    public static final String EXTRA_CURRENT_USER = "current_user_email";

    private QuestionAdapter mQuestionAdapter;
    private RecyclerView mQueryRecyclerView;
    // Firebase stuff
    private DatabaseReference mQuestionsReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloquery);


        // Initialise Firebase objects
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Initialise the adapter
        mQuestionAdapter = new QuestionAdapter();
        // Set BloqueryActivity(this) as QuestionAdapter's delegate
        mQuestionAdapter.setQuestionAdapterDelegate(this);

        // Initialise Views in the layout
        mQueryRecyclerView = (RecyclerView) findViewById(R.id.recycler_bloquery);

        // Set the layout, animator, and adapter for RecyclerView
        mQueryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mQueryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mQueryRecyclerView.setAdapter(mQuestionAdapter);

        // Initialise database;
        mQuestionsReference = FirebaseDatabase.getInstance().getReference("questions");

        // Setup event listener
        mQuestionsReference.addChildEventListener(this);

        // Initialise ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }



    @Override
    protected void onStop() {
        super.onStop();
        mQuestionsReference.removeEventListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bloquery, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*
     * When Actionbar is selected, respond to NavigationDrawer or Overflow menu
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_question_bloquery) {
            showEditDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Firebase: Required methods of ChildEventListener
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Question question = dataSnapshot.getValue(Question.class);
        mQuestionAdapter.addQuestion(question);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    /*
     * Method from implementing QuestionAdapter.QuestionAdapterDelegate
     */
    @Override
    public void onItemClicked(int position, List<Question> questions) {

        // The current Question item
        Question questionItem = questions.get(position);
        String questionId = questionItem.getQuestionId();

        Intent intent = new Intent(this, SingleQuestionActivity.class);
        intent.putExtra(EXTRA_QUESTION_ID_KEY, questionId);
        intent.putExtra(EXTRA_QUESTION_STRING, questionItem.getQuestionString());

        startActivity(intent);
    }

    @Override
    public void onFinishAddInput(String inputText) {
        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter a question...", Toast.LENGTH_SHORT).show();
        } else {
            String key = mQuestionsReference.push().getKey();
            String userId = mCurrentUser.getUid();

            Question question = new Question(key, inputText, (long) System.currentTimeMillis(), 0, userId);
            mQuestionsReference.child(key).setValue(question);

            Toast.makeText(this, "Question added!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddInputDialogFragment addInputDialogFragment = AddInputDialogFragment.newInstance("Ask a question");
        addInputDialogFragment.show(fm, TAG);
    }
}
