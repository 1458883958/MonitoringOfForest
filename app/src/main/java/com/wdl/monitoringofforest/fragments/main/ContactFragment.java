package com.wdl.monitoringofforest.fragments.main;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.PortraitView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.presenter.contact.ContactContract;
import com.wdl.factory.presenter.contact.ContactPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.MessageActivity;
import com.wdl.monitoringofforest.activities.PersonalActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends PresenterFragment<ContactContract.Presenter>
        implements ContactContract.View {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.empty)
    EmptyView mEmpty;

    private RecyclerAdapter<UserDb> mAdapter;
    public ContactFragment() {
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
        //初始化RecyclerView
        //设置布局方向
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        //设置适配器
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<UserDb>() {
            @Override
            protected int getItemViewType(int position, UserDb user) {
                //返回的是子布局文件
                return R.layout.cell_contact_list;
            }

            @Override
            protected ViewHolder<UserDb> onCreateViewHolder(View root, int viewType) {
                return new ContactFragment.ViewHolder(root);
            }
        });

        //设置监听事件
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<UserDb>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, UserDb user) {
                //跳转到聊天界面
               MessageActivity.show(getContext(),user);
            }
        });

        //绑定recycler
        mEmpty.bind(mRecycler);
        //设置占位布局
        setPlaceHolderView(mEmpty);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<UserDb>{

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.im_name)
        TextView mName;

        @BindView(R.id.im_desc)
        TextView mDesc;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @OnClick(R.id.im_portrait)
        void onPortraitClick(){
            //显示信息
            PersonalActivity.show(getContext(),data.getId());
        }
        @Override
        protected void onBind(UserDb user) {
            //头像的加载
            mPortraitView
                    .setUp(Glide.with(ContactFragment.this),user);
            mName.setText(user.getPhone());
            mDesc.setText(user.getAlias());
        }
    }
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected ContactContract.Presenter initPresenter() {
        return new ContactPresenter(this);
    }

    @Override
    public RecyclerAdapter<UserDb> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void adapterDataChanged() {
        //判断是否使用占位布局
        placeHolderView.triggerOkOrEmpty(mAdapter.getItemCount()>0);

    }
}
