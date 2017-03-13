package xz.exercise;

import android.view.View;

import butterknife.OnClick;
import xz.exercise.base.BaseActivity;

/**
 * Author：zhulunjun
 * Time：2017/2/22
 * description：验证异常捕获的activity
 */

public class ExceptionActivity extends BaseActivity{
    @Override
    protected int getLayoutView() {
        return R.layout.activity_exception;
    }

    @Override
    protected BaseActivity getActivity() {
        return this;
    }

    @OnClick({R.id.btn_1})
    public void myClick(View v){
        switch (v.getId()){
            case R.id.btn_1:
                    ex1();
                break;
        }
    }

    private void ex1(){
        throw new RuntimeException("click exception...");
    }
}
