package com.wdl.monitoringofforest.fragments.message;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.PortraitView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.data.data.helper.MessageHelper;
import com.wdl.factory.data.data.helper.UserHelper;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.MessageDb;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.message.MessageContract;
import com.wdl.factory.presenter.message.MessagePresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.MessageActivity;
import com.wdl.monitoringofforest.activities.PersonalActivity;
import com.wdl.monitoringofforest.fragments.panel.PanelFragment;

import net.qiujuer.genius.ui.compat.UiCompat;
import net.qiujuer.genius.ui.widget.Loading;
import net.qiujuer.widget.airpanel.AirPanel;
import net.qiujuer.widget.airpanel.AirPanelFrameLayout;
import net.qiujuer.widget.airpanel.Util;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatUserFragment extends PresenterFragment<MessageContract.Presenter>
        implements AppBarLayout.OnOffsetChangedListener, MessageContract.View ,PanelFragment.PanelCallback{
    private int receiverId;
    private Adapter adapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.appbar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    private MenuItem mPersonalInfoItem;
    @BindView(R.id.mPortrait)
    PortraitView mPortrait;

    @BindView(R.id.et_content)
    EditText mContent;
    @BindView(R.id.btn_submit)
    View mView;

    private PanelFragment panelFragment;
    //控制底部面板与主页面的切换
    private AirPanel.Boss boss;
    private UserDb db;

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        receiverId = bundle.getInt(MessageActivity.KEY_RECEIVER_ID, -1);
        db = UserHelper.findFistOfLocal(receiverId);
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        //初始化面板
        boss = root.findViewById(R.id.lay_content);
        boss.setup(new AirPanel.PanelListener() {
            @Override
            public void requestHideSoftKeyboard() {
                //请求隐藏软键盘
                Util.hideKeyboard(mContent);
            }
        });
        panelFragment = (PanelFragment) getChildFragmentManager()
                .findFragmentById(R.id.frag_panel);
        panelFragment.setUp(this);
        initToolBar();
        initAppBar();
        initContent();
        mPortrait.setUp(Glide.with(this), db.getImage());
        collapsingToolbarLayout.setTitle(db.getAlias());
        Glide.with(this)
                .load(R.drawable.default_banner_chat)
                .centerCrop()
                .into(new ViewTarget<CollapsingToolbarLayout, GlideDrawable>(collapsingToolbarLayout) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setContentScrim(resource.getCurrent());
                    }
                });
        //设置RecyclerView的布局管理器
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter();
        mRecyclerView.setAdapter(adapter);
    }

    private void initContent() {
        //编辑框设置监听 用以改变提交按钮的状态
        mContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String content = editable.toString().trim();
                boolean needSend = !TextUtils.isEmpty(content);
                //设置状态改变icon
                mView.setActivated(needSend);
            }
        });
    }

    /**
     * 初始化AppBar
     */
    private void initAppBar() {
        mAppBarLayout.addOnOffsetChangedListener(this);
    }

    /**
     * 初始化toolbar
     */
    protected void initToolBar() {
        Toolbar toolbar = mToolbar;
        if (toolbar != null) {
            //设置返回按钮
            toolbar.setNavigationIcon(R.drawable.ic_back);
            //监听
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Objects.requireNonNull(getActivity()).finish();
                }
            });
            toolbar.inflateMenu(R.menu.chat_user);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.action_person) {
                        portraitClick();
                    }
                    return false;
                }
            });
            //拿到个人的item
            mPersonalInfoItem = toolbar.getMenu().findItem(R.id.action_person);
        }

    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mPresenter.start();
    }

    @OnClick(R.id.mPortrait)
    void portraitClick() {
        PersonalActivity.show(getContext(), receiverId);
    }

    @OnClick(R.id.btn_face)
    void face() {
        //打开
        boss.openPanel();
        panelFragment.showFace();
    }

    @OnClick(R.id.btn_record)
    void record() {
        //打开
        boss.openPanel();
        panelFragment.showRecord();
    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        if (mView.isActivated()) {
            String content = mContent.getText().toString();
            mContent.setText("");
            mPresenter.pushMessage(1, receiverId, content);
        } else {
            onMoreClick();
        }
    }

    private void onMoreClick() {
        boss.openPanel();
        panelFragment.showGallery();
    }

    public ChatUserFragment() {
        // Required empty public constructor
    }

    @NonNull
    public static ChatUserFragment newInstance() {
        return new ChatUserFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat_user;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        View view = mPortrait;
        MenuItem item = mPersonalInfoItem;
        if (view == null || item == null) return;
        //完全张开的状态
        if (verticalOffset == 0) {
            view.setVisibility(View.VISIBLE);
            view.setScaleX(1);
            view.setScaleY(1);
            view.setAlpha(1);

            item.setVisible(false);
            item.getIcon().setAlpha(0);
        } else {
            //当前缩放值
            verticalOffset = Math.abs(verticalOffset);
            //最大缩放值
            final int totalScrollRange = appBarLayout.getTotalScrollRange();
            //完全收缩
            if (verticalOffset >= totalScrollRange) {
                view.setVisibility(View.INVISIBLE);
                view.setScaleX(0);
                view.setScaleY(0);
                view.setAlpha(0);

                item.setVisible(true);
                item.getIcon().setAlpha(255);
            } else {
                //中间状态
                float progress = 1 - verticalOffset / (float) totalScrollRange;
                view.setVisibility(View.VISIBLE);
                view.setScaleX(progress);
                view.setScaleY(progress);
                view.setAlpha(progress);

                item.setVisible(true);
                item.getIcon().setAlpha(255 - (int) (255 * progress));
            }
        }
    }

    @Override
    protected MessageContract.Presenter initPresenter() {
        return new MessagePresenter(this, receiverId);
    }

    @Override
    public RecyclerAdapter<MessageDb> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void adapterDataChanged() {
    }

    @Override
    public EditText getInputEditText() {
        //返回edit
        return mContent;
    }

    /**
     * 内容适配器
     */
    private class Adapter extends RecyclerAdapter<MessageDb> {

        @Override
        protected int getItemViewType(int position, MessageDb message) {
            //判断发送者是否是自己,是自己显示在右边
            boolean isRight = Objects.equals(message.getSenderId(), Account.getUserId());
            //消息内容是文字
            switch (message.getType()) {
                case MessageDb.MESSAGE_TYPE_NOR:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
                case MessageDb.MESSAGE_TYPE_PIC:
                    return isRight ? R.layout.cell_chat_pic_right : R.layout.cell_chat_pic_left;
                case MessageDb.MESSAGE_TYPE_AUDIO:
                    return isRight ? R.layout.cell_chat_audio_right : R.layout.cell_chat_audio_left;
                default:
                    return isRight ? R.layout.cell_chat_text_right : R.layout.cell_chat_text_left;
            }
        }

        @Override
        protected ViewHolder<MessageDb> onCreateViewHolder(View root, int viewType) {
            switch (viewType) {
                //文字的
                case R.layout.cell_chat_text_right:
                case R.layout.cell_chat_text_left:
                    return new TextHolder(root);
                //文字的
                case R.layout.cell_chat_pic_right:
                case R.layout.cell_chat_pic_left:
                    return new PicHolder(root);
                //文字的
                case R.layout.cell_chat_audio_right:
                case R.layout.cell_chat_audio_left:
                    return new AudioHolder(root);
                default:
                    return new TextHolder(root);
            }
        }
    }

    /**
     * 基础Holder
     */
    class BaseHolder extends RecyclerAdapter.ViewHolder<MessageDb> {
        @BindView(R.id.im_portrait)
        PortraitView mPortrait;
        //可空,左边没有,右边有
        @Nullable
        @BindView(R.id.loading)
        Loading mLoading;

        public BaseHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(MessageDb message) {
            //头像加载
            mPortrait.setUp(Glide.with(ChatUserFragment.this),
                    UserHelper.findFistOfLocal(message.getSenderId()).getImage());

        }

    }

    class TextHolder extends BaseHolder {

        @BindView(R.id.tv_content)
        TextView mContent;

        public TextHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(MessageDb message) {
            super.onBind(message);
            //把内容设置到布局
            mContent.setText(message.getContent());
        }
    }

    /**
     * 语音Holder
     */
    class AudioHolder extends BaseHolder {

        public AudioHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(MessageDb message) {
            super.onBind(message);

        }
    }

    /**
     * 图片Holder
     */
    class PicHolder extends BaseHolder {

        public PicHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(MessageDb message) {
            super.onBind(message);

        }
    }

}
