package com.wdl.monitoringofforest.fragments.set_info;



import com.wdl.common.app.Fragment;
import com.wdl.monitoringofforest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedbackFragment extends Fragment {


    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_feedback;
    }

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }
}
