package com.wdl.monitoringofforest.fragments.device_data;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.Factory;
import com.wdl.factory.model.db.ImageDb;
import com.wdl.factory.presenter.data.DataContract;
import com.wdl.factory.presenter.data.DataPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.helper.Cache;
import com.wdl.utils.BitmapUtil;
import com.wdl.utils.LogUtils;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;

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

        private Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                imOr.setImageBitmap((Bitmap) msg.obj);
                return true;
            }
        });
        @BindView(R.id.result)
        TextView density;
        @BindView(R.id.his_pic_or)
        ImageView imOr;
        @BindView(R.id.his_pic_de)
        ImageView imDe;
        @BindView(R.id.time)
        TextView time;
        Bitmap myBitmap;
        @OnClick(R.id.his_pic_or)
        void deal(){
            Factory.runOnAsy(new Runnable() {
                @Override
                public void run() {
                    FutureTarget<File> future = Glide.with(getContext()).load(data.getOriginalPath())
                            .downloadOnly(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL);
                    try {
                        File file = future.get();
                        LogUtils.e("xxx:"+file.getAbsolutePath());
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                        Bitmap bitmapBinary = BitmapUtil.convertToBMW(bitmap,100);
                        Message message = Message.obtain();
                        message.obj = bitmapBinary;
                        handler.sendMessage(message);
                        double x = BitmapUtil.result(bitmapBinary, new BitmapUtil.Progress() {
                            @Override
                            public void progress(int current, int total) {

                            }
                        });
                        LogUtils.e("result:"+x);
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });

        }


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
                    .placeholder(R.drawable.ic_placeholder)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imOr);
            Glide.with(getContext())
                    .load(imageDb.getTargetPath())
                    .fitCenter()
                    .placeholder(R.drawable.ic_placeholder)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imDe);
            time.setText("" + imageDb.getTime());
        }
    }
}
