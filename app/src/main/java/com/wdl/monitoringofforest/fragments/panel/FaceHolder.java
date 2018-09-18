package com.wdl.monitoringofforest.fragments.panel;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.face.Face;
import com.wdl.monitoringofforest.R;

import butterknife.BindView;

/**
 * author：   wdl
 * time： 2018/9/18 17:31
 * des：    表情holder
 */
public class FaceHolder extends RecyclerAdapter.ViewHolder<Face.Bean> {
    @BindView(R.id.im_face)
    ImageView mFace;

    public FaceHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void onBind(Face.Bean bean) {
        if (bean != null && (
                //drawable 资源id                  //face zip 包资源路径
                (bean.preview instanceof Integer) || (bean.preview instanceof String)
        )) {
            Glide.with(mFace.getContext())
                    .load(bean.preview)
                    .asBitmap()
                    //设置解码格式,保证清晰度
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .placeholder(R.drawable.default_face)
                    .into(mFace);
        }
    }
}
