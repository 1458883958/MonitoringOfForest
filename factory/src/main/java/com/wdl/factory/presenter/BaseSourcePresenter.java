package com.wdl.factory.presenter;

import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.DbDataSource;
import com.wdl.factory.model.card.Pi;

import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter
 * 创建者：   wdl
 * 创建时间： 2018/8/3 14:47
 * 描述：    基础仓库源presenter封装
 * @param <Data>      数据类型
 * @param <ViewModel> recycler需要的数据模型
 * @param <Source>    数据库仓库源
 * @param <View>      view
 */
@SuppressWarnings("unused")
public abstract class BaseSourcePresenter<Data
        ,ViewModel
        ,Source extends DbDataSource<Data>
        ,View extends BaseContract.RecyclerView>
            extends BaseRecyclerPresenter<ViewModel,View>
            implements DataSource.SucceedCallback<List<Data>>{

    protected Source source;

    public BaseSourcePresenter(View view, Source source) {
        super(view);
        this.source = source;
    }

    @Override
    public void start() {
        super.start();
        if (source!=null)
            source.load(this);
    }

    @Override
    public void destroy() {
        super.destroy();
        source.disposed();
        source = null;
    }

}
