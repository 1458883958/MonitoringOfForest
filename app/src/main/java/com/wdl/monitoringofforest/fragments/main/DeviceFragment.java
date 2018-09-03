package com.wdl.monitoringofforest.fragments.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.model.card.DictationResult;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.presenter.pi.PiContract;
import com.wdl.factory.presenter.pi.PiPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.DeviceDataActivity;
import com.wdl.monitoringofforest.activities.MainActivity;
import com.wdl.utils.LogUtils;

import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设备模块
 */
@SuppressWarnings({"unused", "unchecked"})
public class DeviceFragment extends PresenterFragment<PiContract.Presenter>
        implements PiContract.View {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @BindView(R.id.empty)
    EmptyView emptyView;

    private RecyclerAdapter adapter;
    private FloatActionButton fab;
    private StringBuffer stringBuffer;

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


    private void tts() {
        //创建语音识别对话框
        RecognizerDialog rd = new RecognizerDialog(Objects.requireNonNull(getContext()), null);
        //设置参数accent,language等参数
        rd.setParameter(SpeechConstant.LANGUAGE, "zh_cn");//中文
        rd.setParameter(SpeechConstant.ACCENT, "mandarin");//普通话
        stringBuffer = new StringBuffer();
        //设置回调接口
        rd.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult arg0, boolean arg1) {
                // TODO Auto-generated method stub
                String result = arg0.getResultString();
                LogUtils.e(result);
                String voice = parseJson(result);
                stringBuffer.append(voice);
                if (arg1) {//回话结束
                    LogUtils.e("voice:" + stringBuffer.toString());
                    String ints = stringBuffer.toString();
                    //解析语音识别后返回的json格式的结果
                    if (ints.contains("一号拍照")) {
                        mPresenter.changedSwitch((PiDb) adapter.getItems().get(0));

                        //adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onError(SpeechError arg0) {
                // TODO Auto-generated method stub
            }
        });
        //显示对话框
        rd.show();
    }

    private String parseJson(String result) {
        StringBuffer buff = new StringBuffer();
        Gson gson = new Gson();
        DictationResult dictationResult = gson.fromJson(result,
                DictationResult.class);
        ArrayList<DictationResult.Words> ws = (ArrayList<DictationResult.Words>)
                dictationResult.getWs();
        for (DictationResult.Words w : ws) {
            String info = w.getCw().get(0).getW();
            LogUtils.e("info:" + info);
            buff.append(info);
        }
        return buff.toString();
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        fab = Objects.requireNonNull(getActivity()).findViewById(R.id.fab_action);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tts();
            }
        });
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

        adapter.setListener(new RecyclerAdapter.AdapterListenerImpl<PiDb>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, PiDb piDb) {
                super.onItemClick(holder, piDb);
                int pId = piDb.getId();
                DeviceDataActivity.show(getContext(), pId);
            }
        });

        /*
         * 长按删除 设备
         * */
        adapter.setListener(new RecyclerAdapter.AdapterListenerImpl<PiDb>() {
            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, PiDb piDb) {
                super.onItemLongClick(holder, piDb);
                showPopMenu(holder,piDb);
            }
        });
        //绑定recycler
        emptyView.bind(recyclerView);
        //设置占位布局
        setPlaceHolderView(emptyView);
    }

    /**
     * @param holder
     * @param piDb
     */
    public void showPopMenu(RecyclerAdapter.ViewHolder holder, final PiDb piDb){
        PopupMenu popupMenu = new PopupMenu(Objects.requireNonNull(getContext()),holder.itemView);
        popupMenu.getMenuInflater().inflate(R.menu.menu_delete,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId()==R.id.removeItem) {
                    //删除网络存储
                    int pId = piDb.getId();
                    int uId = piDb.getUserId();
                    mPresenter.deleteDevice(pId,uId);
                }
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
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
        }

        @OnClick(R.id.setting)
        void setting() {
            setState(true);

        }

        @OnClick(R.id.submit)
        void submit() {
            String remark = pRemark.getText().toString().trim();
            String threshold = pThreshold.getText().toString().trim();
            String delayed = pDelayed.getText().toString().trim();
            String password = pPassword.getText().toString().trim();
            mPresenter.update(data, data.getId(), remark, Integer.valueOf(threshold), Integer.valueOf(delayed), password);
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
