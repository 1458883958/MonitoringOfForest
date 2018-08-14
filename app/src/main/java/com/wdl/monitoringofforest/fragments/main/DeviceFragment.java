package com.wdl.monitoringofforest.fragments.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.presenter.pi.PiContract;
import com.wdl.factory.presenter.pi.PiPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备模块
 */
@SuppressWarnings({"unused", "unchecked"})
public class DeviceFragment extends PresenterFragment<PiContract.Presenter> implements PiContract.View {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty)
    EmptyView emptyView;

    private RecyclerAdapter adapter;

    public DeviceFragment() {
        // Required empty public constructor
    }

    /**
     * 第一次加载
     */
    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        //进行数据库查询以及网络查询
        mPresenter.start();
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        //recyclerView设置
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new RecyclerAdapter<PiDb>() {
            @Override
            protected int getItemViewType(int position, PiDb pi) {
                return R.layout.cell_pi_list;
            }

            @Override
            protected ViewHolder<PiDb> onCreateViewHolder(View root, int viewType) {
                return new DeviceFragment.ViewHolder(root);
            }
        });


        //绑定recycler
        emptyView.bind(recyclerView);
        //设置占位布局
        setPlaceHolderView(emptyView);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_device;
    }

    @Override
    protected PiContract.Presenter initPresenter() {
        return new PiPresenter(this);
    }

    @Override
    public RecyclerAdapter<PiDb> getRecyclerAdapter() {
        return adapter;
    }

    /**
     * 刷新界面
     * 判断是否显示占位布局
     */
    @Override
    public void adapterDataChanged() {
        placeHolderView.triggerOkOrEmpty(adapter.getItemCount() > 0);
    }

    /**
     * 设备状态更新成功
     */
    @Override
    public void changedSucceed() {

    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<PiDb> {

        @BindView(R.id.pName)
        TextView pName;
        @BindView(R.id.pSwitch)
        SwitchCompat pSwitch;
        @BindView(R.id.et_remark)
        EditText pRemark;
        @BindView(R.id.et_threshold)
        EditText pThreshold;
        @BindView(R.id.et_delayed)
        EditText pDelayed;
        @BindView(R.id.boot_state)
        ImageView bootState;
        @BindView(R.id.setting)
        ImageView setting;
        @BindView(R.id.submit)
        ImageView submit;
        @BindView(R.id.et_password)
        EditText pPassword;

        /**
         * 开关机
         */
        @OnClick(R.id.pSwitch)
        void changedPi() {
            mPresenter.changedSwitch(data);
//            int pId = data.getId();
//            if (data.getSwitchState() == 0)
//                mPresenter.changedSwitch(pId,1);
//            else
//                mPresenter.changedSwitch(pId,0);
        }

        @OnClick(R.id.setting)
        void setting() {
            LogUtils.e("setting");
            setState(true);

        }

        @OnClick(R.id.submit)
        void submit() {
            String remark = pRemark.getText().toString().trim();
            String threshold = pThreshold.getText().toString().trim();
            String delayed = pDelayed.getText().toString().trim();
            String password = pPassword.getText().toString().trim();
            mPresenter.update(remark, Integer.valueOf(threshold), Integer.valueOf(delayed), password);
            setState(false);
        }

        private void setState(boolean b) {
            pRemark.setEnabled(b);
            pThreshold.setEnabled(b);
            pDelayed.setEnabled(b);
            pPassword.setEnabled(b);
            submit.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
            setting.setVisibility(b ? View.INVISIBLE : View.VISIBLE);
        }

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(PiDb pi) {
            pName.setText(pi.getName());
            pSwitch.setChecked(pi.getSwitchState() == 1);
            pRemark.setText(pi.getRemark());
            pPassword.setText(pi.getPassword());
            pThreshold.setText(String.valueOf(pi.getThreshold()));
            pDelayed.setText(String.valueOf(pi.getDelayed()));
            bootState.setImageResource(pi.getBootState() == 1 ?
                    R.drawable.ic_circle : R.drawable.ic_circle_off);
        }
    }


}
