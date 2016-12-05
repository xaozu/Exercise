package xz.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import xz.exercise.base.BaseActivity;
import xz.exercise.imagepage.ImageActivity;

public class MainActivity extends BaseActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ImageActivity.class));
            }
        });
    }
}
