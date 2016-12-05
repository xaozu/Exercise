package xz.exercise.imagepage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import xz.exercise.R;
import xz.exercise.base.BaseActivity;

/**
 * Author：zhulunjun
 * Time：2016/12/2
 * description：
 */

public class ImageActivity extends BaseActivity {
    ParallaxViewPager parallaxViewPager;
    private LinearLayout ll_o;
    private ImageView iv_triangle_1,iv_triangle_2;
    List<View> views=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        parallaxViewPager= (ParallaxViewPager) findViewById(R.id.prallax);
        ll_o= (LinearLayout) findViewById(R.id.ll_o);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.layout_page_item,null);
        View view2=inflater.inflate(R.layout.layout_page_item2,null);
        iv_triangle_1= (ImageView) view.findViewById(R.id.iv_triangle_1);
        iv_triangle_2= (ImageView) view.findViewById(R.id.iv_triangle_2);
        views.add(view);
        views.add(view2);
        parallaxViewPager
                .setOverlapPercentage(0.6f)
                .setAdapter(new MyPageAdapter(views));
        parallaxViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                page1Anim(positionOffset*4, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                setImageO(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initO();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void page1Anim(float positionOffset, int positionOffsetPixels){
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) iv_triangle_1.getLayoutParams();
        layoutParams.setMargins(dip2px(40),0,0,dip2px(55)+(int) (positionOffsetPixels*0.1f));
        iv_triangle_1.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams layoutParams1= (RelativeLayout.LayoutParams) iv_triangle_2.getLayoutParams();
        layoutParams1.setMargins(dip2px(10)-(int) (positionOffsetPixels*0.04f),0,0,dip2px(20)+(int) (positionOffsetPixels*0.8f));
        iv_triangle_2.setLayoutParams(layoutParams1);
        iv_triangle_2.setAlpha(1-positionOffset);

    }

    //初始化小圆点
    private void initO() {
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setPadding(12,8,12,8);
            if (i == 0) {
                imageView.setImageResource(R.drawable.o_black);
            } else {
                imageView.setImageResource(R.drawable.o_gray);
            }
            ll_o.addView(imageView);
        }
    }

    //设置小圆点
    private void setImageO(int index){
        for(int i=0;i<3;i++) {
            if(index>i || index==i)
                ((ImageView) ll_o.getChildAt(i)).setImageResource(R.drawable.o_black);
            else
                ((ImageView) ll_o.getChildAt(i)).setImageResource(R.drawable.o_gray);
        }
    }

    /**
     * dip转化像素
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue){
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);

    }
}
