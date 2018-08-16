package com.wdl.monitoringofforest.fragments.device_data;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.model.db.ImageDb;
import com.wdl.factory.presenter.data.DataContract;
import com.wdl.factory.presenter.data.DataPresenter;
import com.wdl.monitoringofforest.R;

import butterknife.BindView;

import static com.wdl.monitoringofforest.activities.DeviceDataActivity.P_ID;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class PictureFragment extends PresenterFragment<DataContract.Presenter>
        implements DataContract.View {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.empty)
    EmptyView mEmpty;

    private int pId;
    private RecyclerAdapter<ImageDb> adapter;

    public PictureFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mPresenter.start();
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        pId = bundle.getInt(P_ID);

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
                return R.layout.cell_pic_list;
            }

            @Override
            protected ViewHolder<ImageDb> onCreateViewHolder(View root, int viewType) {
                return new PictureFragment.ViewHolder(root);
            }
        });

        mEmpty.bind(mRecycler);
        setPlaceHolderView(mEmpty);
    }

    @Override
    protected DataContract.Presenter initPresenter() {
        return new DataPresenter(this,pId);
    }

    @Override
    public RecyclerAdapter<ImageDb> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void adapterDataChanged() {
        mEmpty.triggerOkOrEmpty(adapter.getItemCount() > 0);
    }

    @Override
    public int getPiId() {
        return pId;
    }


    class ViewHolder extends RecyclerAdapter.ViewHolder<ImageDb> {

        @BindView(R.id.result)
        TextView density;
        @BindView(R.id.his_pic_or)
        ImageView imOr;
        @BindView(R.id.his_pic_de)
        ImageView imDe;
        @BindView(R.id.time)
        TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onBind(ImageDb imageDb) {
            density.setText(String.format(
                    getString(R.string.label_density_result),
                    ""+imageDb.getDensity()));
            Glide.with(getContext())
                    .load(imageDb.getOriginalPath())
                    .fitCenter()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imOr);
            Glide.with(getContext())
                    .load(imageDb.getTargetPath())
                    .fitCenter()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imDe);
            time.setText("" + imageDb.getTime());
        }
    }
}
