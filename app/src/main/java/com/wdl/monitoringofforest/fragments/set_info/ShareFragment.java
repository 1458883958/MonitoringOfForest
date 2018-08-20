package com.wdl.monitoringofforest.fragments.set_info;



import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.wdl.common.app.Fragment;
import com.wdl.common.common.Common;
import com.wdl.monitoringofforest.R;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {

    @BindView(R.id.iv_share_code)
    ImageView ivCode;

    public ShareFragment() {
        // Required empty public constructor
    }

    public static ShareFragment newInstance() {
        return new ShareFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_share;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        Bitmap bitmap;
        BitMatrix matrix;
        MultiFormatWriter writer = new MultiFormatWriter();
        String words = Common.Constance.URL+"/myfile/app-debug.apk";
        try {
            matrix = writer.encode(words, BarcodeFormat.QR_CODE, 500, 500);
            BarcodeEncoder encoder = new BarcodeEncoder();
            bitmap = encoder.createBitmap(matrix);
            ivCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
