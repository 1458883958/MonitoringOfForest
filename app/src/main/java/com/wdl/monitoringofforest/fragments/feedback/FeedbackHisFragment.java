package com.wdl.monitoringofforest.fragments.feedback;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdl.common.app.Fragment;
import com.wdl.monitoringofforest.R;

import java.util.Objects;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackHisFragment extends Fragment {

    private FeedbackTrigger trigger;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.trigger = (FeedbackTrigger) context;
    }

    @OnClick(R.id.go_feedback)
    void goFeed(){
        trigger.triggerView();
    }

    public FeedbackHisFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_feedback_his;
    }

    public static FeedbackHisFragment newInstance(){
        return new FeedbackHisFragment();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Objects.requireNonNull(getActivity()).setTitle(R.string.title_history_feedback);
    }
}
