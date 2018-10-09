package com.wdl.monitoringofforest.fragments.main;



import android.net.Uri;
import android.view.View;

import com.wdl.common.app.Fragment;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.media.IRenderView;
import com.wdl.monitoringofforest.media.IjkVideoView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DealFragment extends Fragment {

    @BindView(R.id.video_view)
    IjkVideoView videoView;

    private String url5 = "http://stream1.grtn.cn/tvs2/sd/live.m3u8?_ts&time=1518428696629";
    public DealFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_deal;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        /** 普通播放 start **/
        videoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        videoView.setVideoURI(Uri.parse(url5));
        videoView.start();
        /** 普通播放 end **/

    }
}
