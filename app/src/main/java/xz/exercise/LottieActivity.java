package xz.exercise;

import android.os.Bundle;
import android.support.annotation.Nullable;

import xz.exercise.base.BaseActivity;

/**
 * Author：zhulunjun
 * Time：2017/2/6
 * description：试验，用AE导出的json文件动画
 */

public class LottieActivity extends BaseActivity{
    @Override
    protected int getLayoutView() {
        return R.layout.activity_lottie;
    }

    @Override
    protected BaseActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
