package com.wdl.monitoringofforest.fragments.media;


import android.app.Dialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wdl.common.widget.GalleryView;
import com.wdl.common.widget.TransStateBottomSheetDialog;
import com.wdl.monitoringofforest.R;

import java.util.Objects;

/**
 * y
 * 图片选择的gallery
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class GalleryFragment extends BottomSheetDialogFragment
        implements GalleryView.SelectedChangedListener {

    private GalleryView mGalleryView;
    private OnSelectImageListener listener;

    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * 初始化控件
     */
    @Override
    public void onStart() {
        super.onStart();
        mGalleryView.setUp(getLoaderManager(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mGalleryView = root.findViewById(R.id.galleryView);
        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TransStateBottomSheetDialog(Objects.requireNonNull(getContext()));
    }

    /**
     * 改变的回调
     *
     * @param size 大小
     */
    @Override
    public void notifyChanged(int size) {
        //如果选中
        if (size>0){
            //界面隐藏
            dismiss();
            //将路径传给监听者
            if (listener!=null){
                String[] paths = mGalleryView.getImagePaths();
                listener.onSelect(paths[0]);
                //销毁引用
                listener = null;
            }
        }
    }

    /**
     * 设置监听
     *
     * @param listener OnSelectImageListener
     * @return GalleryFragment
     */
    public GalleryFragment setListener(OnSelectImageListener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * 选中图片的回调
     */
    public interface OnSelectImageListener {
        /**
         * 选中图片的回调
         *
         * @param path 选中图片的路径
         */
        void onSelect(String path);
    }
}
