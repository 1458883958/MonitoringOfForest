package com.wdl.monitoringofforest.fragments.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.PortraitView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.model.card.User;
import com.wdl.factory.presenter.contact.FollowContract;
import com.wdl.factory.presenter.contact.FollowPresenter;
import com.wdl.factory.presenter.user_search.SearchContract;
import com.wdl.factory.presenter.user_search.SearchPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.SearchActivity;
import com.wdl.utils.LogUtils;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.drawable.LoadingCircleDrawable;
import net.qiujuer.genius.ui.drawable.LoadingDrawable;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class SearchUserFragment extends PresenterFragment<SearchContract.Presenter>
        implements SearchActivity.SearchListener ,SearchContract.UserView{

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.empty)
    EmptyView mEmpty;
    private RecyclerAdapter<User> mAdapter;

    public SearchUserFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<User>() {
            @Override
            protected int getItemViewType(int position, User user) {
                return R.layout.cell_search_list;
            }

            @Override
            protected ViewHolder<User> onCreateViewHolder(View root, int viewType) {
                return new SearchUserFragment.ViewHolder(root);
            }
        });

        mEmpty.bind(mRecycler);
        setPlaceHolderView(mEmpty);

    }

    @Override
    protected void initData() {
        super.initData();
        //首次进入页面
        search("");
    }

    @Override
    public void search(String content) {
        mPresenter.search(content);
    }

    @Override
    protected SearchContract.Presenter initPresenter() {
        return new SearchPresenter(this);
    }

    @Override
    public void onSearchDone(List<User> users) {
        mAdapter.replace(users);
        placeHolderView.triggerOkOrEmpty(mAdapter.getItemCount()>0);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<User> implements FollowContract.View{

        @BindView(R.id.im_portrait)
        PortraitView mPortraitView;

        @BindView(R.id.im_name)
        TextView mName;

        @BindView(R.id.im_follow)
        ImageView mFollow;

        private FollowContract.Presenter mPresenter;
        @OnClick(R.id.im_follow)
        void follow(){
            mPresenter.follow(data);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            new FollowPresenter(this);
        }

        @Override
        protected void onBind(User user) {
            mPortraitView.setUp(Glide.with(SearchUserFragment.this), user.getuImagepath());
            mName.setText(user.getuTelephone());
            //mFollow.setEnabled();
        }


        @Override
        public void onFollowSucceed() {
            //如果当前drawable为设置进去的圆形drawable，停止
            if (mFollow.getDrawable() instanceof LoadingCircleDrawable){
                ((LoadingCircleDrawable)mFollow.getDrawable()).stop();
                //设置回默认的drawable
                mFollow.setImageResource(R.drawable.sel_opt_done_add);
            }
            //发起更新
            //updateData(user);
        }

        @Override
        public void showLoading() {
            int minSize = (int) Ui.dipToPx(getResources(),22);
            int maxSize = (int) Ui.dipToPx(getResources(),30);
            //初始化圆形drawable
            LoadingDrawable drawable = new LoadingCircleDrawable(minSize,maxSize);
            drawable.setAlpha(0);
            int[] color = new int[]{UiCompat.getColor(getResources(),R.color.white_alpha_208)};
            drawable.setForegroundColor(color);
            mFollow.setImageDrawable(drawable);
            //启动动画
            drawable.start();
        }

        @Override
        public void showToast(int res) {

        }

        @Override
        public void showError(int res) {
            //如果当前drawable为设置进去的圆形drawable，停止
            if (mFollow.getDrawable() instanceof LoadingCircleDrawable){
                LoadingDrawable drawable = (LoadingCircleDrawable)mFollow.getDrawable();
                drawable.setProgress(1);
                drawable.stop();
            }
        }

        @Override
        public void setPresenter(FollowContract.Presenter presenter) {
            mPresenter = presenter;
        }
    }
}
