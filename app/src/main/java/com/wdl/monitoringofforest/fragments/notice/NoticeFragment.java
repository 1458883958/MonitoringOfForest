package com.wdl.monitoringofforest.fragments.notice;


import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.model.db.NoticeDb;
import com.wdl.factory.presenter.notice.NoticeContract;
import com.wdl.factory.presenter.notice.NoticePresenter;
import com.wdl.monitoringofforest.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class NoticeFragment extends PresenterFragment<NoticeContract.Presenter>
        implements NoticeContract.View {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty)
    EmptyView emptyView;

    private RecyclerAdapter<NoticeDb> adapter;

    public NoticeFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mPresenter.start();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new RecyclerAdapter<NoticeDb>() {
            @Override
            protected int getItemViewType(int position, NoticeDb noticeDb) {
                return R.layout.cell_notice_list;
            }

            @Override
            protected ViewHolder<NoticeDb> onCreateViewHolder(View root, int viewType) {
                return new NoticeFragment.ViewHolder(root);
            }
        });
        emptyView.bind(recyclerView);
        setPlaceHolderView(emptyView);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<NoticeDb> {

        @BindView(R.id.im_title)
        TextView nTitle;
        @BindView(R.id.im_content)
        TextView nContent;
        @BindView(R.id.im_time)
        TextView nTime;
        @BindView(R.id.im_attach)
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onBind(NoticeDb noticeDb) {
            nTitle.setText(noticeDb.getSubject());
            nContent.setText(noticeDb.getContent());
            nTime.setText(noticeDb.getTime()+"");
            if (TextUtils.isEmpty(noticeDb.getFilePath())) {
                linearLayout.setVisibility(View.GONE);
            }
            else linearLayout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_notice;
    }

    @NonNull
    public static NoticeFragment newInstance() {
        return new NoticeFragment();
    }

    @Override
    protected NoticeContract.Presenter initPresenter() {
        return new NoticePresenter(this);
    }

    @Override
    public RecyclerAdapter<NoticeDb> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void adapterDataChanged() {
        emptyView.triggerOkOrEmpty(adapter.getItemCount() > 0);
    }
}
