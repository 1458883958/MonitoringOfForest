package com.wdl.monitoringofforest.fragments.device_data;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
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
import com.wdl.monitoringofforest.R;
import com.wdl.utils.LogUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends Fragment {

    @BindView(R.id.charts)
    LineChart mLineChart;
    private Calendar cal;
    private int year,month,day;

    public SensorFragment() {
        // Required empty public constructor
    }

    @OnClick(R.id.btn_date)
    void select(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LogUtils.e("date: "+year+"-"+month+"-"+dayOfMonth);
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getContext()),AlertDialog.THEME_DEVICE_DEFAULT_DARK
            ,listener,year,month,day);
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
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);

        //显示边界
        mLineChart.setDrawBorders(true);
        //设置数据
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            entries.add(new Entry(i, (float) (Math.random()) * 80));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "温度");
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);
    }


}
