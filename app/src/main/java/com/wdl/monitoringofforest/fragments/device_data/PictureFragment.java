package com.wdl.monitoringofforest.fragments.device_data;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wdl.common.app.Fragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.model.card.Image;
import com.wdl.factory.model.db.ImageDb;
import com.wdl.monitoringofforest.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class PictureFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.empty)
    EmptyView mEmpty;

    private RecyclerAdapter<ImageDb> adapter;

    public PictureFragment() {
        // Required empty public constructor
    }

    public static PictureFragment newInstance() {
        return new PictureFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_picture;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(adapter = new RecyclerAdapter<ImageDb>() {
            @Override
            protected int getItemViewType(int position, ImageDb image) {
                return 0;
            }

            @Override
            protected ViewHolder<ImageDb> onCreateViewHolder(View root, int viewType) {
                return new PictureFragment.ViewHolder(root);
            }
        });

        mEmpty.bind(mRecycler);
        setPlaceHolderView(mEmpty);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<ImageDb>{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(ImageDb imageDb) {

        }
    }
}
