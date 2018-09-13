package com.wdl.monitoringofforest.fragments.device_data;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.wdl.common.app.Application;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.common.widget.EmptyView;
import com.wdl.common.widget.Progress;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.Factory;
import com.wdl.factory.model.db.ImageDb;
import com.wdl.factory.presenter.data.DataContract;
import com.wdl.factory.presenter.data.DataPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.utils.BitmapUtil;
import com.wdl.utils.DateUtil;
import com.wdl.utils.LogUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wdl.monitoringofforest.activities.DeviceDataActivity.P_ID;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
public class PictureFragment extends PresenterFragment<DataContract.Presenter>
        implements DataContract.ImageView {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    @BindView(R.id.empty)
    EmptyView mEmpty;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

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
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.start();
                        refreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
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
        return new DataPresenter(this, pId);
    }

    @Override
    public RecyclerAdapter<ImageDb> getRecyclerAdapter() {
        return adapter;
    }

    @Override
    public void adapterDataChanged() {
        mEmpty.triggerOkOrEmpty(adapter.getItemCount() > 0);
    }

    class ViewHolder extends RecyclerAdapter.ViewHolder<ImageDb> {

        @BindView(R.id.result)
        TextView density;
        @BindView(R.id.deal_result)
        TextView dealDensity;
        @BindView(R.id.his_pic_or)
        ImageView imOr;
        @BindView(R.id.his_pic_de)
        ImageView imDe;
        @BindView(R.id.time)
        TextView time;
        Progress dialog = null;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        private Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == 1) {
                    int current = msg.arg1;
                    int total = msg.arg2;
                    double result = (double) current / total;
                    int x = (int) (result * 100 + 0.5);
                    dialog.setProgress(x);
                } else if (msg.what == 2) {
                    double x = (double) msg.obj;
                    double value = new BigDecimal(x)
                            .setScale(2, BigDecimal.ROUND_HALF_UP)
                            .doubleValue();
                    dealDensity.setText(String.format(
                            getString(R.string.label_density_result),
                            "" + value));
                    imOr.setClickable(false);
                    Application.showToast(R.string.label_deal_ok);
                    dialog.dismiss();
                }
                return true;
            }
        });

        private class DealPicHandler implements Runnable {
            @Override
            public void run() {
                FutureTarget<File> future = Glide.with(getContext()).load(data.getOriginalPath())
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                try {
                    File file = future.get();
                    LogUtils.e("filePath:"+file.getPath());
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    Bitmap bitmapBinary = BitmapUtil.convertToBMW(bitmap, 100);
                    double x = BitmapUtil.result(bitmapBinary, new BitmapUtil.Progress() {
                        @Override
                        public void progress(int current, int total) {

                            if (current < total) {
                                Message message = Message.obtain();
                                message.what = 1;
                                message.arg1 = current;
                                message.arg2 = total;
                                handler.sendMessage(message);
                            }
                        }
                    });
                    Message message = new Message();
                    message.what = 2;
                    message.obj = x;
                    handler.sendMessage(message);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }

        @OnClick(R.id.his_pic_or)
        void deal() {
            dialog = new Progress(getContext());
            Factory.runOnAsy(new DealPicHandler());
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onBind(ImageDb imageDb) {
            density.setText(String.format(
                    getString(R.string.label_density_result),
                    "" + imageDb.getDensity()));
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
            time.setText(DateUtil.format(imageDb.getTime()));
        }
    }
}
