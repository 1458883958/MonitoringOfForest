package com.wdl.monitoringofforest.fragments.panel;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wdl.common.app.Fragment;
import com.wdl.common.tools.UiTool;
import com.wdl.common.widget.GalleryView;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.face.Face;
import com.wdl.monitoringofforest.R;

import net.qiujuer.genius.ui.Ui;

import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * 底部空气栏
 * A simple {@link Fragment} subclass.
 */
public class PanelFragment extends Fragment {

    private PanelCallback callback;
    private View mFacePanel, mGalleryPanel, mRecordPanel;

    public PanelFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_panel;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        initFace(root);
        initRecord(root);
        initGallery(root);

    }

    private void initGallery(View root) {
        final View galleryPanel = mGalleryPanel = root.findViewById(R.id.lay_gallery_panel);
        final GalleryView galleryView = galleryPanel.findViewById(R.id.view_gallery);
        final TextView selectedSize = galleryPanel.findViewById(R.id.txt_gallery_select_count);
        //设置选中条数
        galleryView.setUp(getLoaderManager(), new GalleryView.SelectedChangedListener() {
            @Override
            public void notifyChanged(int size) {
                selectedSize.setText(String.format(getText(R.string.label_gallery_selected_size).toString(), size));
            }
        });
        //发送按钮
        galleryPanel.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGallerySendCall(galleryView, galleryView.getImagePaths());
            }
        });
    }

    /**
     * 发送按钮的点击事件
     *
     * @param galleryView GalleryView
     * @param imagePaths  已选的图片路径
     */
    private void onGallerySendCall(GalleryView galleryView, String[] imagePaths) {
        //通知到聊天界面
        PanelCallback mCallback = callback;
        if (mCallback == null) return;
            mCallback.onSendGallery(imagePaths);
        //清理状态
        galleryView.clear();
    }

    private void initRecord(View root) {
        //final View recordPanel = mRecordPanel = root.findViewById(R.id.lay_gallery_panel);
    }

    //初始化表情
    private void initFace(View root) {
        final View facePanel = mFacePanel = root.findViewById(R.id.lay_panel_face);
        View backspace = facePanel.findViewById(R.id.im_backspace);
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 删除表情
                PanelCallback mCallback = callback;
                if (mCallback == null) return;
                //模拟一次键盘点击
                KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0
                        , 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);

                mCallback.getInputEditText().dispatchKeyEvent(event);
            }
        });
        TabLayout tabLayout = facePanel.findViewById(R.id.tab);
        ViewPager pager = facePanel.findViewById(R.id.pager);
        tabLayout.setupWithViewPager(pager);

        //每一个表情显示48dp
        final int minFaceSize = (int) Ui.dipToPx(getResources(), 48);
        //屏幕宽度
        final int totalScreen = UiTool.getSreenWidth(Objects.requireNonNull(getActivity()));
        //一行显示的表情数
        final int spanCount = totalScreen / minFaceSize;

        //绑定适配器
        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Face.all(Objects.requireNonNull(getContext())).size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                //添加item
                LayoutInflater inflater = LayoutInflater.from(getContext());
                RecyclerView recyclerView =
                        (RecyclerView) inflater
                                .inflate(R.layout.lay_face_content, container, false);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), spanCount));
                //设置适配器
                List<Face.Bean> faces =
                        Face.all(Objects.requireNonNull(getContext()))
                                .get(position).faces;
                FaceAdapter adapter = new FaceAdapter(faces, new RecyclerAdapter.AdapterListenerImpl<Face.Bean>() {
                    @Override
                    public void onItemClick(RecyclerAdapter.ViewHolder holder, Face.Bean bean) {
                        if (callback == null) return;
                        //获取edit
                        EditText editText = callback.getInputEditText();
                        //输入 表情添加到输入框
                        Face.inputFace(getContext(),
                                editText.getText(),
                                bean,
                                (int) (editText.getTextSize() + (int) Ui.dipToPx(getResources(), 2)));

                    }
                });
                recyclerView.setAdapter(adapter);
                //添加
                container.addView(recyclerView);
                return recyclerView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                //移除item
                container.removeView((View) object);
                super.destroyItem(container, position, object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                //拿到表情盘的描述
                return Face.all(Objects.requireNonNull(getContext())).get(position).name;
            }
        });
    }

    public void showFace() {
        //mRecordPanel.setVisibility(View.GONE);
        mGalleryPanel.setVisibility(View.GONE);
        mFacePanel.setVisibility(View.VISIBLE);
    }

    public void showRecord() {
//mRecordPanel.setVisibility(View.GONE);
        mGalleryPanel.setVisibility(View.GONE);
        mFacePanel.setVisibility(View.GONE);
    }

    public void showGallery() {
        //mRecordPanel.setVisibility(View.GONE);
        mGalleryPanel.setVisibility(View.VISIBLE);
        mFacePanel.setVisibility(View.GONE);
    }

    /**
     * 开始初始化方法
     *
     * @param callback PanelCallback
     */
    public void setUp(PanelCallback callback) {
        this.callback = callback;
    }

    /**
     * 回调聊天界面的call
     */
    public interface PanelCallback {
        //获取Edit 用来键入表情等
        EditText getInputEditText();

        /**
         * @param paths 已选图片的路径
         */
        void onSendGallery(String[] paths);

        /**
         * @param file 录音的路径
         * @param time 录音时长
         */
        void onRecordDone(File file, long time);
    }

}
