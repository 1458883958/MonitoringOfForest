package com.wdl.monitoringofforest.fragments.feedback;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.model.db.FeedbackDb;
import com.wdl.factory.presenter.feedback.HisFeedbackContract;
import com.wdl.factory.presenter.feedback.HisFeedbackPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.utils.DateUtil;
import com.wdl.utils.LogUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class FeedbackHisFragment extends PresenterFragment<HisFeedbackContract.Presenter>
        implements HisFeedbackContract.View {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty)
    EmptyView emptyView;

    MenuItem item;

    private RecyclerAdapter adapter;


    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        //    //进行数据库查询以及网络查询
        mPresenter.start();
    }

    @Override
    protected HisFeedbackContract.Presenter initPresenter() {
        return new HisFeedbackPresenter(this);
    }

//    @OnClick(R.id.go_feedback)
//    void goFeed(){
//        trigger.triggerView();
//    }

    public FeedbackHisFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_feedback_his;
    }

    public static FeedbackHisFragment newInstance() {
        return new FeedbackHisFragment();
    }


    @SuppressLint("ResourceType")
    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Objects.requireNonNull(getActivity()).setTitle(R.string.title_history_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new RecyclerAdapter<FeedbackDb>() {
            @Override
            protected int getItemViewType(int position, FeedbackDb feedbackDb) {
                return R.layout.feedback_item;
            }

            @Override
            protected ViewHolder<FeedbackDb> onCreateViewHolder(View root, int viewType) {
                return new FeedbackHisFragment.ViewHolder(root);
            }
        });
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
    }

    @Override
    public RecyclerAdapter<FeedbackDb> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void adapterDataChanged() {
        placeHolderView.triggerOkOrEmpty(adapter.getItemCount() > 0);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<FeedbackDb> {

        @BindView(R.id.subject)
        TextView mSubject;
        @BindView(R.id.content)
        TextView mContent;
        @BindView(R.id.time)
        TextView mTime;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onBind(FeedbackDb feedbackDb) {
            mSubject.setText(feedbackDb.getSubject());
            mContent.setText("\u3000\u3000" + feedbackDb.getContent());
            mTime.setText(DateUtil.format(feedbackDb.getTime()));
        }
    }
}
