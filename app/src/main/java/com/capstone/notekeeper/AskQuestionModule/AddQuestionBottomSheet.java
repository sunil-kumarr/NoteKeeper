package com.capstone.notekeeper.AskQuestionModule;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.capstone.notekeeper.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddQuestionBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;
    private Context mContext;
    private Button addQuestionButton;
    private EditText questionEdt;
    private FirebaseUser currentUser;
    private SendQuestionToActivity questionToActivity;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        questionToActivity = (SendQuestionToActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_question_bottom_sheet,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addQuestionButton = view.findViewById(R.id.add_question_button);
        questionEdt = view.findViewById(R.id.edt_Add_question);
        addQuestionButton.setOnClickListener(v->{
            String question = questionEdt.getText().toString();
            questionToActivity.onQuestionComplete(question);
            dismiss();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public void onStop() {
        super.onStop();
        dismiss();
    }

    public  interface SendQuestionToActivity{
        void onQuestionComplete(String question);
    }
}
