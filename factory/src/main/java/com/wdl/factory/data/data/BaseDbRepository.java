package com.wdl.factory.data.data;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.DbDataSource;
import com.wdl.factory.data.data.helper.DbHelper;
import com.wdl.factory.model.db.BaseDbModel;
import com.wdl.utils.CollectionUtil;

import net.qiujuer.genius.kit.reflect.Reflector;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.data.data
 * 创建者：   wdl
 * 创建时间： 2018/8/7 15:13
 * 描述：    基础Repository的提取,基础的数据库仓库,实现对数据库的基本的监听操作
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class BaseDbRepository<Data extends BaseDbModel<Data>> implements DbDataSource<Data>
        , QueryTransaction.QueryResultListCallback<Data>, DbHelper.ChangedListener<Data> {

    //与presenter层交互的回调
    private SucceedCallback<List<Data>> callback;
    //数据缓存
    private final LinkedList<Data> dataList = new LinkedList<>();
    //当前范型对应的真实的Class信息
    private Class<Data> dataClass;

    public BaseDbRepository() {
        //拿到当前泛型数组信息
        Type[] types = Reflector.getActualTypeArguments(BaseDbRepository.class, this.getClass());
        dataClass = (Class<Data>) types[0];
    }

    /**
     * DbFlow框架查询的回调用
     *
     * @param transaction QueryTransaction
     * @param tResult     查询后的数据
     */
    @Override
    public void onListQueryResult(QueryTransaction transaction, @NonNull List<Data> tResult) {
        if (tResult.size() == 0) {
            dataList.clear();
            //通知刷新
            notifyDataChanged();
            return;
        }
        // 转变为数组
        Data[] users = CollectionUtil.toArray(tResult, dataClass);
        // 回到数据集更新的操作中
        onDataSave(users);
    }


    @Override
    public void load(SucceedCallback<List<Data>> callback) {
        this.callback = callback;
        //添加监听
        DbHelper.addChangedListener(dataClass, this);
    }

    /**
     * 取消监听,销毁数据
     */
    @Override
    public void disposed() {
        this.callback = null;
        DbHelper.removeChangedListener(dataClass, this);
        dataList.clear();
    }

    /**
     * 数据库监听的回掉
     *
     * @param data Data[]
     */
    @Override
    public void onDataSave(Data... data) {
        boolean isChanged = false;
        for (Data datum : data) {
            if (isRequired(datum)) {
                insertOrUpdate(datum);
                isChanged = true;
            }
        }
        //如果数据有改变,通知更新
        if (isChanged)
            notifyDataChanged();
    }

    /**
     * 数据库监听的回掉
     *
     * @param data Data[]
     */
    @Override
    public void onDataDelete(Data... data) {
        boolean isChanged = false;
        for (Data datum : data) {
            if (dataList.remove(datum)) {
                isChanged = true;
            }
        }
        //如果数据有改变,通知更新
        if (isChanged)
            notifyDataChanged();
    }

    /**
     * 数据插入还是更新
     *
     * @param datum Data
     */
    protected void insertOrUpdate(Data datum) {
        int index = indexOf(datum);
        if (index >= 0) {
            update(index, datum);
        } else {
            insert(datum);
        }
    }

    /**
     * 插入的数据
     *
     * @param datum Data
     */
    private void insert(Data datum) {
        dataList.add(datum);
    }

    /**
     * 更新
     *
     * @param index 下标
     * @param datum 数据
     */
    private void update(int index, Data datum) {
        dataList.remove(index);
        dataList.add(index, datum);
    }

    /**
     * 判断新数据在老数据列表中是否存在
     *
     * @param datum Data新数据
     * @return index
     */
    protected int indexOf(Data datum) {
        int index = -1;
        for (Data data : dataList) {
            index++;
            if (data.isSame(datum)) {
                return index;
            }
        }
        return -1;
    }

    /**
     * 通知界面刷新
     */
    private void notifyDataChanged() {
        final SucceedCallback<List<Data>> callback = this.callback;
        if (callback != null)
            callback.onLoaded(dataList);
    }

    /**
     * 判断是否是自己需要的数据,过滤数据
     *
     * @param datum 数据
     * @return boolean
     */
    protected abstract boolean isRequired(Data datum);


}
