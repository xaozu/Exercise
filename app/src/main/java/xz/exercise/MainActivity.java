package xz.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import butterknife.OnClick;
import xz.exercise.base.BaseActivity;
import xz.exercise.imagepage.ImageActivity;
import xz.exercise.view.WaveView3;

public class MainActivity extends BaseActivity {
    ImageView imageView;

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected BaseActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WaveView3 waveView3= (WaveView3) findViewById(R.id.wave_view);
        imageView= (ImageView) findViewById(R.id.image);

        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2,-2);
        lp.gravity = Gravity.BOTTOM|Gravity.CENTER;
        waveView3.setOnWaveAnimationListener(new WaveView3.OnWaveAnimationListener() {
            @Override
            public void OnWaveAnimation(float y) {
                lp.setMargins(0,0,0,(int)y+2);
                imageView.setLayoutParams(lp);
            }
        });

    }

    @OnClick({R.id.text,R.id.tx_login,R.id.tx_exption,R.id.tx_test,R.id.tx_aidl})
    public void myClick(View view){
        switch (view.getId()){
            case R.id.text:
                startActivity(new Intent(getActivity(),ImageActivity.class));
                break;
            case R.id.tx_login:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
            case R.id.tx_exption:
                startActivity(new Intent(getActivity(),ExceptionActivity.class));
                break;
            case R.id.tx_test:
                startActivity(new Intent(getActivity(),TestActivity.class));
                break;
            case R.id.tx_aidl:
                startActivity(new Intent(getActivity(),AidlActivity.class));
                break;
            default:
                break;
        }
    }
}
