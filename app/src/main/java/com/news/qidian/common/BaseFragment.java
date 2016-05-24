package com.news.qidian.common;

import android.support.v4.app.Fragment;
//
//import com.umeng.analytics.MobclickAgent;

/**
 * Created by fengjigang on 16/3/8.
 */
public class BaseFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(getActivity());
    }
}
