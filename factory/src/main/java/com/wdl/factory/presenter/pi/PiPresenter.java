package com.wdl.factory.presenter.pi;

import android.support.v7.util.DiffUtil;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.R;
import com.wdl.factory.data.data.helper.PiHelper;
import com.wdl.factory.data.data.pi.PiDataSource;
import com.wdl.factory.data.data.pi.PiRepository;
import com.wdl.factory.model.api.pi.PiModel;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BaseSourcePresenter;
import com.wdl.factory.utils.DiffUiDataCallback;
import com.wdl.utils.LogUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/6 11:31
 * 描述：    PI P层
 */
@SuppressWarnings("unused")
public class PiPresenter extends BaseSourcePresenter<PiDb, PiDb, PiDataSource, PiContract.View>
        implements PiContract.Presenter{
    public PiPresenter(PiContract.View view) {
        super(view, new PiRepository());
    }

    @Override
    public void start() {
        super.start();
        final PiContract.View view = getView();
        LogUtils.e(Account.getUserId() + "");
        if (Account.getUserId() == -1) {
            view.showError(R.string.data_account_error_un_login);
            return;
        }
        User user = new User();
        user.setuId(Account.getUserId());
        //网络查询
        PiHelper.select(user);
    }


    /**
     * 最终的数据都在此处
     *
     * @param data T
     */
    @Override
    public void onLoaded(List<PiDb> data) {
        final PiContract.View view = getView();
        if (view == null) return;
        RecyclerAdapter<PiDb> adapter = view.getRecyclerAdapter();
        //获取老数据
        List<PiDb> oldList = adapter.getItems();
        //进行差异化比较
        DiffUtil.Callback callback = new DiffUiDataCallback<>(oldList, data);
        //差异结果集
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        //调用基类刷新
        refreshData(result, data);

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    /**
     * 改变拍照状态
     *
     * @param state      状态
     * */
    @Override
    public void changedSwitch(int pId,int state) {
        PiModel model = new PiModel();
        model.setpId(pId);
        model.setpSwitchstate(state);
        //PiHelper.change(model);
    }

    /**
     * 改变拍照状态
     *
     * @param db      PiDb
     * */
    @Override
    public void changedSwitch(PiDb db) {
        PiModel model = new PiModel();
        model.setpId(db.getId());
        int state = db.getSwitchState()==0?1:0;
        model.setpSwitchstate(state);
        PiHelper.change(model,db);
    }


    /**
     * @param remark    备注
     * @param threshold 阈值
     * @param delayed   延时
     */
    @Override
    public void update(String remark, Integer threshold, Integer delayed,String password) {
        final PiContract.View view = getView();
        //LogUtils.e(Account.getUserId() + "");
        int userId = Account.getUserId();
        if (userId==-1) {
            view.showError(R.string.data_account_error_un_login);
            return;
        }
        PiModel model = new PiModel();
        model.setuId(userId);
        model.setpDelayed(delayed);
        model.setpRemark(remark);
        model.setpThreshold(threshold);
        model.setpPassword(password);
        PiHelper.update(model);
    }

}
