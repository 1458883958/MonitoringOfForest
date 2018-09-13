package com.wdl.factory.presenter.data;

import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.PiHelper;
import com.wdl.factory.model.api.pi.Model;
import com.wdl.factory.model.db.SensorDb;
import com.wdl.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.data
 * 创建者：   wdl
 * 创建时间： 2018/9/12 20:30
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public class SensorPresenter extends BasePresenter<DataContract.SensorView> implements
        DataContract.Presenter,DataSource.Callback<List<SensorDb>>{
    public SensorPresenter(DataContract.SensorView view) {
        super(view);
    }

    @Override
    public void onLoaded(final List<SensorDb> data) {
        final DataContract.SensorView view = getView();
        if (view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.succeed(data);
                }
            });
        }
    }

    @Override
    public void select(int pId, int limitNum) {
        Model model = new Model();
        model.setpId(pId);
        model.setLimitNum(limitNum);
        PiHelper.querySensor(model,this);
    }

    @Override
    public void selectOf(int pId, int limitNum) {

    }

    @Override
    public void onNotAvailable(final int res) {
        final DataContract.SensorView view = getView();
        if (view!=null){
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(res);
                }
            });
        }
    }
}
