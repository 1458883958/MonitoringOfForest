package com.wdl.monitoringofforest.fragments.set_info;



import com.wdl.common.app.Fragment;
import com.wdl.monitoringofforest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_about;
    }
}
