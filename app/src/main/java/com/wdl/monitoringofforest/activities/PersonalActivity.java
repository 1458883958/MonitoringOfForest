package com.wdl.monitoringofforest.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wdl.common.app.PresenterToolbarActivity;
import com.wdl.common.widget.PortraitView;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.UserDb;
import com.wdl.factory.presenter.user.PersonalContract;
import com.wdl.factory.presenter.user.PersonalPresenter;
import com.wdl.monitoringofforest.R;

import net.qiujuer.genius.ui.widget.Button;

import butterknife.BindView;

/**
 * 用户信息界面
 */
@SuppressWarnings("unused")
public class PersonalActivity extends PresenterToolbarActivity<PersonalContract.Presenter>
        implements PersonalContract.View {
    public static final String USER_ID = "USER_ID";
    private int userId;
    private MenuItem mFllow;
    //是否关注此人
    private boolean isFollowed = false;

    @BindView(R.id.im_header)
    ImageView mHeadler;
    @BindView(R.id.im_portrait)
    PortraitView mPortrait;
    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_desc)
    TextView mDesc;
    @BindView(R.id.tv_follows)
    TextView mFollow;
    @BindView(R.id.tv_following)
    TextView mFollowing;
    @BindView(R.id.say_hello)
    Button mSayHello;
    /**
     * 用户显示入口
     *
     * @param context 上下文
     * @param userId  用户id
     */
    public static void show(Context context, int userId) {
        Intent intent = new Intent(context, PersonalActivity.class);
        intent.putExtra(USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        userId = bundle.getInt(USER_ID);
        return userId != -1;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_personal;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.start();
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitle("");
    }

    @Override
    protected PersonalContract.Presenter initPresenter() {
        return new PersonalPresenter(this);
    }

    @Override
    public int getId() {
        return userId;
    }

    @Override
    public void onDone(UserDb user) {
        if (user==null)
            return;
        mPortrait.setUp(Glide.with(this),user);
        mName.setText(user.getPhone());
        mDesc.setText(user.getAlias());
        mFollow.setText(String.format(getString(R.string.label_follows),""+3));
        mFollowing.setText(String.format(getString(R.string.label_following),""+3));
        hideLoading();

    }

    @Override
    public void allowChat(boolean f) {
        mSayHello.setVisibility(f? View.VISIBLE:View.GONE);
    }

    @Override
    public void isFollow(boolean f) {
        isFollowed = f;
        changedItem();
    }

    /**
     * 初始化menu
     * @param menu Menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.personal,menu);
        mFllow = menu.findItem(R.id.action_follow);
        changedItem();
        return true;
    }

    /**
     * 改变menu item的状态
     */
    private void changedItem() {
        if (mFllow==null)return;
        Drawable drawable = isFollowed?getResources().getDrawable(R.drawable.ic_favorite):
                getResources().getDrawable(R.drawable.ic_favorite_border);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable,getResources().getColor(R.color.white));
        mFllow.setIcon(drawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_follow){
            //TODO 进行关注
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(int res) {

    }
}
