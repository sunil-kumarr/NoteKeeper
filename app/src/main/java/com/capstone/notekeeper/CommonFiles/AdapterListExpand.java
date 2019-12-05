package com.capstone.notekeeper.CommonFiles;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.capstone.notekeeper.R;

import java.util.List;

public class AdapterListExpand extends BaseExpandableListAdapter {

    private Context _context;
    private List<FAQModelClass> mQuestions;

    public AdapterListExpand(Context context, List<FAQModelClass> mQuestions) {
        this._context = context;
        this.mQuestions=mQuestions;
    }
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return mQuestions.get(groupPosition).getAnswer();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.faq_answer_layout, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.faq_answer);

        txtListChild.setText(childText);
        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mQuestions.get(groupPosition).getQuestion();
    }

    @Override
    public int getGroupCount() {
        return mQuestions.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.faq_question_layout, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.faq_question);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(mQuestions.get(groupPosition).getQuestion());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}