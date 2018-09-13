package com.wdl.monitoringofforest.fragments.device_data;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.EventLogTags;
import android.widget.DatePicker;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.wdl.common.app.Fragment;
import com.wdl.common.app.PresenterFragment;
import com.wdl.factory.data.data.devicedata.SensorCenter;
import com.wdl.factory.model.db.SensorDb;
import com.wdl.factory.presenter.data.DataContract;
import com.wdl.factory.presenter.data.DataPresenter;
import com.wdl.factory.presenter.data.SensorPresenter;
import com.wdl.monitoringofforest.R;
import com.wdl.utils.LogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.wdl.monitoringofforest.activities.DeviceDataActivity.P_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends PresenterFragment<DataContract.Presenter>
        implements DataContract.SensorView {

    private int pId;
    @BindView(R.id.charts)
    LineChart mLineChart;
    private Calendar cal;
    private int year, month, day;

    public SensorFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initArgs(Bundle bundle) {
        super.initArgs(bundle);
        pId = bundle.getInt(P_ID);

    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();
        mPresenter.select(pId, 20);
    }

    @OnClick(R.id.btn_date)
    void select() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LogUtils.e("date: " + year + "-" + month + "-" + dayOfMonth);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getContext()), AlertDialog.THEME_DEVICE_DEFAULT_DARK
                , listener, year, month, day);
        dialog.show();
    }

    public static SensorFragment newInstance() {
        return new SensorFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_sensor;
    }

    @Override
    protected void initData() {
        super.initData();
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        month = cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day = cal.get(Calendar.DAY_OF_MONTH);


    }


    @Override
    protected DataContract.Presenter initPresenter() {
        return new SensorPresenter(this);
    }

    @Override
    public void succeed(List<SensorDb> dbList) {
        for (int i = 0; i < dbList.size(); i++)
            LogUtils.e("sensor:" + i + " " + dbList.get(i).toString());
        //显示边界
        mLineChart.setDrawBorders(true);
        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i, Float.parseFloat(dbList.get(i).getTemperature()+"")));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "温度");
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
    }
}
