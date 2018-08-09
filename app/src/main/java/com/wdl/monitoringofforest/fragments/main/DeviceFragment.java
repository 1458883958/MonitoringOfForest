package com.wdl.monitoringofforest.fragments.main;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.wdl.common.app.Application;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.presenter.pi.PiContract;
import com.wdl.factory.presenter.pi.PiPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.ScanActivity;
import com.wdl.utils.LogUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备模块
 */
@SuppressWarnings({"unused","unchecked"})
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
        placeHolderView.triggerOkOrEmpty(adapter.getItemCount()>0);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<PiDb>{

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

        /**
         * 开关机
         */
        @OnClick(R.id.pSwitch)
        void changedPi(){
            data.setSwitchState(pSwitch.isChecked()?1:0);
            pSwitch.setChecked(data.getSwitchState() == 1);
        }

        public ViewHolder(View itemView) {
            super(itemView);
        }
        @Override
        protected void onBind(PiDb pi) {
            pName.setText(pi.getName());
            pSwitch.setChecked(pi.getSwitchState() == 0);
            pRemark.setText(pi.getRemark());
            pThreshold.setText(String.valueOf(pi.getThreshold()));
            pDelayed.setText(String.valueOf(pi.getDelayed()));
            bootState.setImageResource(pi.getBootState()==0?
                   R.drawable.ic_circle:R.drawable.ic_circle_off);
        }
    }

}
