package xz.exercise;

import xz.exercise.base.BaseActivity;

/**
 * Author：zhulunjun
 * Time：2017/3/13
 * description：测试，实验，小球拉伸回弹效果
 */

public class TestActivity extends BaseActivity {
    @Override
    protected int getLayoutView() {
        return R.layout.activity_test;
    }

    @Override
    protected BaseActivity getActivity() {
        return this;
    }
}
