package com.wdl.monitoringofforest.fragments.set_info;



import com.wdl.common.app.Fragment;
import com.wdl.monitoringofforest.R;
import com.wdl.monitoringofforest.activities.FeedbackActivity;

import java.util.Objects;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("unused")
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

    //检查更新
    @OnClick(R.id.check_update)
    void checkVersion(){

    }

    @OnClick(R.id.feedback)
    void feedback(){
        FeedbackActivity.show(Objects.requireNonNull(getContext()));
    }

}
