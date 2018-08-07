package com.wdl.factory.data.data.helper;

import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.wdl.factory.model.db.AppDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data.helper
 * 创建者：   wdl
 * 创建时间： 2018/8/6 15:33
 * 描述：    数据库封装的工具类,用来增删改 以及增加移除对某个表的数据改动监听
 * 单例模式
 */
@SuppressWarnings({"unused", "unchecked"})
public class DbHelper {
    private static final DbHelper instance;
    /**
     * 某个表有多个观察者
     * Set:观察者集合
     */
    private final Map<Class<?>, Set<ChangedListener>> listeners = new HashMap<>();

    static {
        instance = new DbHelper();
    }

    //私有构造
    private DbHelper() {
    }


    /**
     * 添加对某个表的监听
     *
     * @param tlx      某个表的信息
     * @param listener 表的监听
     * @param <Model>  泛型
     */
    public static <Model extends BaseModel> void addChangedListener(Class<Model> tlx,
                                                                    ChangedListener<Model> listener) {
        Set<ChangedListener> changedListeners = instance.getSetListener(tlx);
        if (changedListeners == null) {
            //初始化某表的监听集合
            changedListeners = new HashSet<>();
            //添加到总的map
            instance.listeners.put(tlx, changedListeners);
        }
        //将监听加入某个表监听的set
        changedListeners.add(listener);
    }

    /**
     * 某个表的监听的删除
     *
     * @param tlx      某个表的信息
     * @param listener 表的监听
     * @param <Model>  泛型
     */
    public static <Model extends BaseModel> void removeChangedListener(Class<Model> tlx,
                                                                       ChangedListener<Model> listener) {
        Set<ChangedListener> changedListeners = instance.getSetListener(tlx);
        if (changedListeners == null) {
            return;
        }
        //将监听从某个表监听的set中删除
        changedListeners.remove(listener);
    }

    /**
     * 获取某个表的监听集合
     *
     * @param tlx     某个表的信息
     * @param <Model> 泛型
     * @return Set<ChangedListener>  某个表的监听集合
     */
    @Nullable
    private <Model extends BaseModel> Set<ChangedListener> getSetListener(Class<Model> tlx) {
        if (listeners.containsKey(tlx)) {
            return listeners.get(tlx);
        }
        return null;
    }


    /**
     * 数据库更新及保存的统一封装
     *
     * @param tlx     Class<Model>
     * @param models  数据源
     * @param <Model> 限定条件
     */
    public static <Model extends BaseModel> void save(final Class<Model> tlx, final Model... models) {
        if (models == null || models.length == 0) return;
        //当前数据库管理者
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        //提交一个事物
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(tlx);
                //保存
                adapter.saveAll(Arrays.asList(models));
                //唤起通知,告知界面数据库有数据更改
                instance.notifySaveOrUpdate(tlx, models);
            }
        }).build().execute();
    }

    /**
     * 数据库删除的统一封装
     *
     * @param tlx     Class<Model>
     * @param models  数据源
     * @param <Model> 限定条件
     */
    public static <Model extends BaseModel> void delete(final Class<Model> tlx, final Model... models) {
        if (models == null || models.length == 0) return;
        //当前数据库管理者
        DatabaseDefinition databaseDefinition = FlowManager.getDatabase(AppDatabase.class);
        //提交一个事物
        databaseDefinition.beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                ModelAdapter<Model> adapter = FlowManager.getModelAdapter(tlx);
                //删除
                adapter.deleteAll(Arrays.asList(models));
                //唤起通知,告知界面数据库有数据更改
                instance.notifyDelete(tlx, models);
            }
        }).build().execute();
    }

    /**
     * 通知保存 更新
     *
     * @param tlx     Class<Model>
     * @param models  数据源
     * @param <Model> 限定条件
     */
    private final <Model extends BaseModel> void notifySaveOrUpdate(Class<Model> tlx, Model... models) {
        //拿到所有监听者
        final Set<ChangedListener> listenerSet = getSetListener(tlx);
        //循环遍历通知
        if (listenerSet!=null&&listenerSet.size()>0) {
            for (ChangedListener<Model> changedListener : listenerSet) {
                changedListener.onDataSave(models);
            }
        }
    }

    /**
     * 通知删除
     *
     * @param tlx     Class<Model>
     * @param models  数据源
     * @param <Model> 限定条件
     */
    private final <Model extends BaseModel> void notifyDelete(Class<Model> tlx, Model... models) {
        //拿到所有监听者
        final Set<ChangedListener> listenerSet = getSetListener(tlx);
        //循环遍历通知,通用的通知
        if (listenerSet!=null&&listenerSet.size()>0) {
            for (ChangedListener<Model> changedListener : listenerSet) {
                changedListener.onDataDelete(models);
            }
        }
        //列外通知
        //TODO 消息表信息变更,通知消息列表更新
    }

    /**
     * 数据变更监听
     *
     * @param <Data> 泛型
     */
    public interface ChangedListener<Data> {
        //保存
        void onDataSave(Data... data);

        //删除
        void onDataDelete(Data... data);

    }

}
