package com.wdl.monitoringofforest.fragments.panel;

import android.view.View;

import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.face.Face;
import com.wdl.monitoringofforest.R;

import java.util.List;

/**
 * author：   wdl
 * time： 2018/9/18 17:29
 * des：    TODO
 */
public class FaceAdapter extends RecyclerAdapter<Face.Bean>{
    public FaceAdapter(List<Face.Bean> beans, AdapterListener<Face.Bean> listener) {
        super(beans, listener);
    }

    @Override
    protected int getItemViewType(int position, Face.Bean bean) {
        return R.layout.cell_face;
    }

    @Override
    protected ViewHolder<Face.Bean> onCreateViewHolder(View root, int viewType) {
        return new FaceHolder(root);
    }
}
