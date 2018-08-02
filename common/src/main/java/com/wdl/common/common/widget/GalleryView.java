package com.wdl.common.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wdl.common.R;
import com.wdl.common.common.app.Application;
import com.wdl.common.common.widget.recycler.RecyclerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import utils.LogUtils;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.common.common.widget
 * 创建者：   wdl
 * 创建时间： 2018/8/2 9:35
 * 描述：    图片选择控件的自定义封装
 */
@SuppressWarnings("unused")
public class GalleryView extends RecyclerView {

    //最多选择三张
    private static final int MAX_SELECTED = 3;
    //最小的长度
    private static final int MIN_LENGTH = 10 * 1024;
    private static final int LOADER_ID = 0x0100;
    //已选图片集合
    private List<Image> selectedImages = new LinkedList<>();
    private Adapter adapter = new Adapter();
    private LoaderCallback loaderCallback = new LoaderCallback();
    private SelectedChangedListener listener;

    public GalleryView(Context context) {
        super(context);
        init();
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GalleryView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * 初始化的方法
     *
     * @param manager  LoaderManager
     * @param listener 改变的监听回调
     * @return LOADER_ID
     */
    public int setUp(LoaderManager manager, SelectedChangedListener listener) {
        this.listener = listener;
        manager.initLoader(LOADER_ID, null, loaderCallback);
        return LOADER_ID;
    }

    /**
     * 初始化布局
     */
    @SuppressWarnings("unchecked")
    private void init() {
        //设置布局管理器
        setLayoutManager(new GridLayoutManager(getContext(), 4));
        //设置适配器
        setAdapter(adapter);
        //设置监听
        adapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Image>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Image image) {
                //如果cell允许点击,更新界面
                //不允许不作处理
                if (onItemAllowClick(image)) {
                    holder.updateData(image);
                }

            }
        });
    }

    /**
     * 获取已选图片的路径
     *
     * @return 已选图片的路径
     */
    public String[] getImagePaths() {
        String[] paths = new String[selectedImages.size()];
        int index = 0;
        for (Image selectedImage : selectedImages) {
            paths[index++] = selectedImage.path;
        }
        return paths;
    }

    /**
     * 清空已选图片
     * 1.初始化选中状态
     * 2.清空list
     * 3.通知更新
     */
    public void clear() {
        for (Image selectedImage : selectedImages) {
            selectedImage.isSelected = false;
        }
        selectedImages.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * 判断是否允许点击，cell点击的具体逻辑
     *
     * @param image 点击的项
     * @return true:允许
     */
    @SuppressLint("StringFormatMatches")
    private boolean onItemAllowClick(Image image) {
        boolean notifyRefresh;
        //如果当前点击的项存在于已选中的集合中,移除并更新界面
        if (selectedImages.contains(image)) {
            selectedImages.remove(image);
            //改变checkbox状态
            image.isSelected = false;
            //状态改变需更新
            notifyRefresh = true;
        } else {
            //已选数量大于最大选中的数量
            if (selectedImages.size() >= MAX_SELECTED) {
                //toast提示
                String toast = getResources().getString(R.string.label_gallery_select_max_size);
                toast = String.format(toast, MAX_SELECTED);
                Application.showToast(toast);
                notifyRefresh = false;
            } else {
                selectedImages.add(image);
                image.isSelected = true;
                notifyRefresh = true;
            }
        }
        //改变
        if (notifyRefresh) {
            notifySelectedChanged();
        }
        return true;

    }

    /**
     * 通知改变
     */
    private void notifySelectedChanged() {
        if (listener != null) {
            listener.notifyChanged(selectedImages.size());
        }
    }

    /**
     * 改变监听的回调
     */
    public interface SelectedChangedListener {
        /**
         * 通知改变
         *
         * @param size 大小
         */
        void notifyChanged(int size);
    }

    /**
     * 用于实际数据加载的LoaderCallback
     */
    private class LoaderCallback implements LoaderManager.LoaderCallbacks<Cursor> {

        private final String[] IMAGE_PROJECTION = new String[]{
                MediaStore.Images.Media._ID,//id
                MediaStore.Images.Media.DATA,//图片路径
                MediaStore.Images.Media.DATE_ADDED//图片创建时间
        };

        @NonNull
        @Override
        public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
            //如果用户传入的id与LOADER_ID一致，进行初始化
            if (id == LOADER_ID) {
                return new CursorLoader(
                        getContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        IMAGE_PROJECTION,
                        null,
                        null,
                        //根据时间倒序排列，空格不能漏
                        IMAGE_PROJECTION[2] + " DESC"
                );
            }
            return null;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
            List<Image> images = new ArrayList<>();
            if (data != null) {
                int count = data.getCount();
                LogUtils.e(""+count);
                if (count > 0) {
                    //游标移动到开始
                    data.moveToFirst();
                    //得到相应的index
                    int indexId = data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]);
                    int indexPath = data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]);
                    int indexDate = data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]);
                    //循环读取数据
                    do {
                        //获取相应的值
                        int id = data.getInt(indexId);
                        String path = data.getString(indexPath);
                        long date = data.getLong(indexDate);
                        File file = new File(path);
                        //如果图片不存在或者长度小于10KB,跳过
                        if (!file.exists() || file.length() < MIN_LENGTH)
                            continue;
                        Image image = new Image();
                        image.id = id;
                        image.path = path;
                        image.date = date;
                        images.add(image);
                    } while (data.moveToNext());
                }
            }
            LogUtils.e(""+images.size());
            //通知界面更新
            updateSource(images);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            //销毁或重置时
            updateSource(null);
        }
    }

    /**
     * 通知更新
     *
     * @param images 更新的集合
     */
    private void updateSource(List<Image> images) {
        adapter.replace(images);
    }

    /**
     * 内部适配器
     */
    private class Adapter extends RecyclerAdapter<Image> {

        @Override
        protected int getItemViewType(int position, Image image) {
            return R.layout.cell_gallery;
        }

        @Override
        protected ViewHolder<Image> onCreateViewHolder(View root, int viewType) {
            return new GalleryView.ViewHolder(root);
        }
    }

    /**
     * 数据缓存的viewHolder
     */
    private class ViewHolder extends RecyclerAdapter.ViewHolder<Image> {
        ImageView imageView;
        View shadow;
        CheckBox isSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            shadow = itemView.findViewById(R.id.iv_shadow);
            isSelected = itemView.findViewById(R.id.cb_selected);
        }

        @Override
        protected void onBind(Image image) {
            Glide.with(getContext())
                    .load(image.path)
                    //不使用缓存
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .placeholder(R.color.grey_200)
                    .into(imageView);
            //设置阴影
            shadow.setVisibility(image.isSelected ? VISIBLE : INVISIBLE);
            //设置checkbox
            isSelected.setChecked(image.isSelected);
            isSelected.setVisibility(VISIBLE);

        }
    }

    /**
     * 实体类
     */
    private static class Image {
        int id;      //数据Id
        String path;//路径
        long date; //日期
        boolean isSelected;//是否选中

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Image image = (Image) o;
            return Objects.equals(path, image.path);
        }

        @Override
        public int hashCode() {

            return Objects.hash(path);
        }
    }
}
