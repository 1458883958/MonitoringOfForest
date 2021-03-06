package com.wdl.monitoringofforest.fragments.feedback;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.wdl.common.app.Application;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.factory.presenter.feedback.AddFeedbackContract;
import com.wdl.factory.presenter.feedback.AddFeedbackPresenter;
import com.wdl.monitoringofforest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unchecked")
public class FeedbackFragment extends PresenterFragment<AddFeedbackContract.Presenter> implements AddFeedbackContract.View,AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinner)
    Spinner spinner;
    private ArrayAdapter<String> adapter;
    private List<String> dataList;
    private String subject;
    @BindView(R.id.feed_content)
    EditText mContent;

    @OnClick(R.id.submit)
    void submit(){
        String content = mContent.getText().toString().trim();
        mPresenter.insertFeedback(subject,content);
    }

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_feedback;
    }

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Objects.requireNonNull(getActivity()).setTitle(R.string.title_feedback);
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(getResources().getStringArray(R.array.spinner)));
        adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                R.layout.spinner_item,dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        subject = adapter.getItem(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    protected AddFeedbackContract.Presenter initPresenter() {
        return new AddFeedbackPresenter(this);
    }

    /**
     * 反馈成功
     */
    @Override
    public void insertSucceed() {
        Application.showToast(R.string.feed_back_succeed);
    }
}
