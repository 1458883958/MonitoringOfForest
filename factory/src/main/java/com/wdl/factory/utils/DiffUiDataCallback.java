package com.wdl.factory.utils;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.utils
 * 创建者：   wdl
 * 创建时间： 2018/8/7 10:29
 * 描述：    实体类的差异比较
 */
@SuppressWarnings("unused")
public class DiffUiDataCallback<T extends DiffUiDataCallback.UiDataDiff<T>> extends DiffUtil.Callback {

    private List<T> oldList;
    private List<T> newList;

    public DiffUiDataCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    /**
     * @param oldItemPosition 旧数据下标
     * @param newItemPosition 新数据下标
     * @return 是否是同一个实体类
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        T oldBean = oldList.get(oldItemPosition);
        T newBean = newList.get(newItemPosition);
        return newBean.isSame(oldBean);
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldBean = oldList.get(oldItemPosition);
        T newBean = newList.get(newItemPosition);
        return newBean.isUiContentSame(oldBean);
    }

    /**
     * @param <T> 泛型,能进行比较的数据类型
     */
    public interface UiDataDiff<T> {
        /**
         * 是否是同一个数据
         *
         * @param old 旧的数据
         * @return true:相等,具有相同的主键id
         */
        boolean isSame(T old);

        /**
         * 在相同主键的基础上判断其余的字段是否一致
         *
         * @param old 旧的数据
         * @return true:相等,内容也一致
         */
        boolean isUiContentSame(T old);
    }
}
