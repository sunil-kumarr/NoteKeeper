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

public class AddAnswerBottomSheet extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;
    private Context mContext;
    private Button addAnswerButton;
    private EditText answerEdt;
    private FirebaseUser currentUser;
    private SendAnswerToActivity sendAnswerToActivity;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        sendAnswerToActivity = (SendAnswerToActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_answer_bottom_sheet,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addAnswerButton = view.findViewById(R.id.add_answer_button);
        answerEdt = view.findViewById(R.id.edt_Add_answer);
        addAnswerButton.setOnClickListener(v->{
            String answer = answerEdt.getText().toString();
            sendAnswerToActivity.onAnswerComplete(answer);
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

    public  interface SendAnswerToActivity{
        void onAnswerComplete(String answer);
    }
}
