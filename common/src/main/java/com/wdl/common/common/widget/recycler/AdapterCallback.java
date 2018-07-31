package com.wdl.common.common.widget.recycler;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.common.widget.recycler
 * 创建者：   wdl
 * 创建时间： 2018/7/31 14:25
 * 描述：    TODO
 */
@SuppressWarnings("unused")
public interface AdapterCallback<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder<Data> holder);
}
