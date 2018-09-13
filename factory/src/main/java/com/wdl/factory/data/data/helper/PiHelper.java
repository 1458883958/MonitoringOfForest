package com.wdl.factory.data.data.helper;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wdl.common.app.Application;
import com.wdl.factory.Factory;
import com.wdl.factory.R;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.model.api.CallbackImpl;
import com.wdl.factory.model.api.account.RspModel;
import com.wdl.factory.model.api.pi.Model;
import com.wdl.factory.model.api.pi.PiModel;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.card.Sensor;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.model.db.PiDb_Table;
import com.wdl.factory.model.db.SensorDb;
import com.wdl.factory.net.Network;
import com.wdl.factory.net.RemoteService;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.data.SensorPresenter;
import com.wdl.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/6 16:49
 * 描述：    设备操作工具类
 */
@SuppressWarnings("unused")
public class PiHelper {

    /**
     * 查看已绑定设备
     */
    public static void select(User user) {
        RemoteService service = Network.remoteService();
        Call<RspModel<List<Pi>>> call = service.selectAllPi(user);
        call.enqueue(new CallbackImpl<List<Pi>>() {
            @Override
            protected void failed(String msg) {
            }

            @Override
            protected void succeed(List<Pi> data) {
                if (data == null || data.size() == 0) return;
                //唤起进行数据存储
                Factory.getPiCenter().dispatch(data.toArray(new Pi[0]));
            }

            @Override
            protected void succeedMsg(String msg) {
                super.succeedMsg(msg);
                return;
            }
        });
    }

    /**
     * 绑定pi
     *
     * @param model PiModel
     */
    public static void bind(PiModel model, final DataSource.Callback<Pi> callback) {
        RemoteService service = Network.remoteService();
        final Call<RspModel<Pi>> call = service.addPi(model);
        call.enqueue(new CallbackImpl<Pi>() {
            @Override
            protected void failed(String msg) {
                LogUtils.e("msg:" + msg);
                if (callback != null) {
                    Factory.decodeRspCode(msg, callback);
                }
            }

            @Override
            protected void succeed(Pi data) {
                LogUtils.e("xx:" + data.toString());
                //通知并保存
                Factory.getPiCenter().dispatch(data);
                if (callback != null)
                    callback.onLoaded(data);
            }
        });
    }

    /**
     * 拍照开关
     *
     * @param model PiModel
     */
    public static void change(PiModel model, final PiDb db) {
        RemoteService service = Network.remoteService();
        Call<RspModel<Pi>> call = service.changedState(model);
        call.enqueue(new CallbackImpl<Pi>() {
            @Override
            protected void failed(String msg) {

            }

            @Override
            protected void succeed(Pi data) {
                db.setSwitchState(data.getpSwitchstate());
                Factory.getPiCenter().dispatch(db);
            }

        });
    }

    /**
     * 更新设备信息
     *
     * @param pi
     * @param model PiModel
     */
    public static void update(final PiDb pi, final Model model) {
        RemoteService service = Network.remoteService();
        Call<RspModel<Pi>> call = service.update(model);
        call.enqueue(new CallbackImpl<Pi>() {
            @Override
            protected void failed(String msg) {

            }

            @Override
            protected void succeed(Pi data) {
                pi.setDelayed(data.getpDelayed());
                pi.setSwitchState(data.getpSwitchstate());
                pi.setPassword(data.getpPassword());
                pi.setRemark(data.getpRemark());
                pi.update();
            }
        });
    }

    /**
     * 删除
     *
     * @param model PiModel
     */
    public static void delete(PiModel model) {
        RemoteService service = Network.remoteService();
        Call<RspModel<Pi>> call = service.deletePi(model);
        call.enqueue(new CallbackImpl<Pi>() {
            @Override
            protected void failed(String msg) {
            }

            @Override
            protected void succeed(Pi data) {
                DbHelper.delete(PiDb.class, data.build());
            }

        });
    }


    public static void query(int userId, final DataSource.Callback<List<PiDb>> callback) {

        RemoteService service = Network.remoteService();
        User user = new User();
        user.setuId(userId);
        Call<RspModel<List<Pi>>> call = service.selectAllPi(user);
        call.enqueue(new CallbackImpl<List<Pi>>() {
            @Override
            protected void failed(String msg) {
            }

            @Override
            protected void succeed(List<Pi> data) {
                if (data == null || data.size() == 0) {
                    if (callback != null) callback.onNotAvailable(R.string.data_pi_un_response);
                    return;
                }
                List<PiDb> piDbs = new ArrayList<>();
                for (Pi datum : data) {
                    piDbs.add(datum.build());
                }
                if (callback != null) callback.onLoaded(piDbs);
            }

        });

    }

    /**
     * 查询限制条数的传感器数据
     *
     * @param model    Model
     * @param callback Callback
     */
    public static void querySensor(Model model, final DataSource.Callback<List<SensorDb>> callback) {
        RemoteService service = Network.remoteService3();
        Call<RspModel<List<Sensor>>> call = service.getSensor(model);
        LogUtils.e("model:" + model.toString());
        call.enqueue(new CallbackImpl<List<Sensor>>() {
            @Override
            protected void failed(String msg) {

            }

            @Override
            protected void succeed(List<Sensor> data) {
                //存库
                Factory.getSensorCenter().dispatch(data.toArray(new Sensor[0]));
                List<SensorDb> dbs = new ArrayList<>();
                for (Sensor datum : data) {
                    dbs.add(datum.build());
                }
                if (callback != null)
                    callback.onLoaded(dbs);
            }
        });
    }
}
