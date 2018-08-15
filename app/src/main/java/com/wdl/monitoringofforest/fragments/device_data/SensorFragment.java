package com.wdl.monitoringofforest.fragments.device_data;

import com.wdl.common.app.Fragment;
import com.wdl.monitoringofforest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SensorFragment extends Fragment {


    public SensorFragment() {
        // Required empty public constructor
    }

    public static SensorFragment newInstance() {
        return new SensorFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_sensor;
    }
}
