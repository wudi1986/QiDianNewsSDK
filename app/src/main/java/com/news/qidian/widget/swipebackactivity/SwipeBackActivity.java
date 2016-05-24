
package com.news.qidian.widget.swipebackactivity;

import android.os.Bundle;
import android.view.View;

import com.news.qidian.common.BaseActivity;

public  class SwipeBackActivity extends BaseActivity implements SwipeBackActivityBase {


    @Override
    protected void setContentView() {
    }

    @Override
    protected void initializeViews() {
    }

    @Override
    protected void loadData() {
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
