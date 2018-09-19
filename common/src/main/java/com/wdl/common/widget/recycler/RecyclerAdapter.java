package com.wdl.common.widget.recycler;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdl.common.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.widget.recycler
 * 创建者：   wdl
 * 创建时间： 2018/7/31 14:20
 * 描述：    RecyclerAdapter的封装
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnLongClickListener, View.OnClickListener, AdapterCallback<Data> {

    private final List<Data> dataList;
    private RecyclerAdapter.AdapterListener<Data> listener;


    //构造函数
    public RecyclerAdapter() {
        this(null);
    }

    public RecyclerAdapter(AdapterListener<Data> listener) {
        this(new ArrayList<Data>(), listener);
    }

    public RecyclerAdapter(List<Data> dataList, AdapterListener<Data> listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    /**
     * 创建一个ViewHolder,holder相当于一个item
     *
     * @param parent   RecyclerView
     * @param viewType 界面的类型
     * @return ViewHolder<Data>,根据界面类型返回相应的ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder<Data> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //得到LayoutInflater，将xml转为view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //将id为viewType的xml转为view
        View root = inflater.inflate(viewType, parent, false);
        //通过子类必须实现的方法得到一个ViewHolder
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);
        //设置root的tag为holder,进行双向绑定
        root.setTag(R.id.tag_recycler_holder, holder);
        //设置点击事件
        root.setOnClickListener(this);
        root.setOnLongClickListener(this);
        //界面注解绑定
        holder.unbinder = ButterKnife.bind(holder, root);
        //绑定callback
        holder.callback = this;
        return holder;
    }

    /**
     * 绑定一个数据到holder
     *
     * @param holder   ViewHolder<Data>
     * @param position 下标
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder<Data> holder, int position) {
        //获取数据
        Data data = dataList.get(position);
        //触发绑定
        holder.bind(data);
    }

    /**
     * 复写默认的布局类型返回
     *
     * @param position 下标
     * @return 类型, 复写后默认为xml文件的id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, dataList.get(position));
    }

    /**
     * 得到布局的类型
     *
     * @param position 下标
     * @param data     数据
     * @return layoutId
     */
    @LayoutRes
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 获取所有数据
     *
     * @return List<Data>
     */
    public List<Data> getItems() {
        return dataList;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View v) {
        //获取holder
        ViewHolder holder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.listener != null) {
            //获取点击的坐标
            int position = holder.getAdapterPosition();
            //回调
            this.listener.onItemClick(holder, dataList.get(position));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        //获取holder
        ViewHolder holder = (ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.listener != null) {
            //获取点击的坐标
            int position = holder.getAdapterPosition();
            //回调
            this.listener.onItemLongClick(holder, dataList.get(position));
            return true;
        }
        return false;
    }

    @Override
    public void update(Data data, ViewHolder<Data> holder) {
        //得到坐标
        int position = holder.getAdapterPosition();
        if (position >= 0) {
            //存在先删除后再原位置增加
            dataList.remove(position);
            dataList.add(position, data);
            //通知刷新
            notifyItemInserted(position);
            //刷新
            notifyDataSetChanged();
        }
    }

    /**
     * 得到一个新的viewHolder
     *
     * @param root     根布局
     * @param viewType xml文件的id
     * @return ViewHolder<Data>
     */
    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    /**
     * 自定义点击监听器
     *
     * @param <Data> 泛型
     */
    public interface AdapterListener<Data> {

        /**
         * cell点击时触发
         *
         * @param holder 自定义ViewHolder
         * @param data   Data
         */
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        /**
         * cell长按时触发
         *
         * @param holder 自定义ViewHolder
         * @param data   Data
         */
        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }

    public RecyclerAdapter.AdapterListener<Data> getListener() {
        return listener;
    }

    public void setListener(RecyclerAdapter.AdapterListener<Data> listener) {
        this.listener = listener;
    }

    /**
     * 内部实现的回调
     * 如只需实现点击或长按的某一个,实现此类即可
     *
     * @param <Data> 泛型
     */
    public static abstract class AdapterListenerImpl<Data> implements RecyclerAdapter.AdapterListener<Data> {

        @Override
        public void onItemClick(ViewHolder holder, Data data) {

        }

        @Override
        public void onItemLongClick(ViewHolder holder, Data data) {

        }
    }

    /**
     * 自定义ViewHolder
     *
     * @param <Data> 泛型
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {

        private AdapterCallback<Data> callback;
        private Unbinder unbinder;
        protected Data data;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于数据绑定的触发
         *
         * @param data Data
         */
        void bind(Data data) {
            this.data = data;
            onBind(data);
        }

        /**
         * 真正的绑定方法
         *
         * @param data Data
         */
        protected abstract void onBind(Data data);

        /**
         * holder对应自己更新数据的操作
         *
         * @param data Data
         */
        public void updateData(Data data) {
            if (this.callback != null) {
                this.callback.update(data, this);
            }
        }
    }

    /**
     * 插入一条数据,并通知插入
     *
     * @param data Data
     */
    public void add(Data data) {
        dataList.add(data);
        notifyItemInserted(dataList.size() - 1);
    }

    /**
     * 插入一堆数据,并通知集合更新
     *
     * @param data Data[]
     */
    public void add(Data... data) {
        if (data != null && data.length > 0) {
            int startPos = dataList.size();
            Collections.addAll(dataList, data);
            notifyItemRangeChanged(startPos, data.length);
        }
    }


    /**
     * 插入一堆并通知更新
     *
     * @param data 集合
     */
    public void add(Collection<Data> data) {
        if (data != null && data.size() > 0) {
            int startPos = dataList.size();
            dataList.addAll(data);
            notifyItemRangeInserted(startPos, data.size());
        }
    }

    /**
     * 删除全部并通知更新
     */
    public void clear() {
        dataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 替换数据,包含清空(通知刷新)
     *
     * @param data 替换的集合
     */
    public void replace(Collection<Data> data) {
        dataList.clear();
        if (data == null || data.size() == 0) return;
        dataList.addAll(data);
        notifyDataSetChanged();
    }
}
