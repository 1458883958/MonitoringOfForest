package com.wdl.monitoringofforest.fragments.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.TextView;

import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.presenter.pi.PiContract;
import com.wdl.factory.presenter.pi.PiPresenter;
import com.wdl.monitoringofforest.R;

import butterknife.BindView;

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

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mPresenter.start();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter = new RecyclerAdapter<Pi>() {
            @Override
            protected int getItemViewType(int position, Pi pi) {
                return R.layout.cell_pi_list;
            }

            @Override
            protected ViewHolder<Pi> onCreateViewHolder(View root, int viewType) {
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
    public RecyclerAdapter<Pi> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void adapterDataChanged() {
        placeHolderView.triggerOkOrEmpty(adapter.getItemCount()>0);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<Pi>{

        @BindView(R.id.pName)
        TextView pName;
        @BindView(R.id.pSwitch)
        SwitchCompat pSwitch;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Pi pi) {
            pName.setText(pi.getpName());
            pSwitch.setChecked(pi.getpSwitchstate() == 0);
        }
    }

}
