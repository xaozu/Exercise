package xz.exercise.imagepage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    private ParallaxViewPager parallaxViewPager;
    private LinearLayout ll_o;
    private ImageView iv_triangle_1,iv_triangle_2;
    private RelativeLayout rl_page2;
    private LinearLayout ll_page2_content;
    private LinearLayout ll_page3;
    private List<View> views=new ArrayList<>();
    private int curPage=0;
    private int x,y;
    private boolean isTouchHorizontal=false;
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
        rl_page2= (RelativeLayout) view2.findViewById(R.id.rl_page2);
        ll_page2_content= (LinearLayout) view2.findViewById(R.id.ll_page2_content);
        ll_page3= (LinearLayout) findViewById(R.id.ll_page3);
        views.add(view);
        views.add(view2);
        parallaxViewPager
                .setOverlapPercentage(0.6f)
                .setAdapter(new MyPageAdapter(views));
        parallaxViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("滑动偏移","position "+position+"positionOffset "+positionOffset+"positionOffsetPixels "+positionOffsetPixels);

                page1Anim(positionOffset*4, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                curPage=position;
                setImageO(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initO();
        parallaxViewPager.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(curPage>0) {
                    int curx = (int) event.getRawX();
                    int cury = (int) event.getRawY();

                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN://按下
                            x= (int) event.getRawX();
                            y= (int) event.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE://正在滑动
                            int moveX=curx-x;
                            int moveY=cury-y;
                            if(Math.abs(moveX) > Math.abs(moveY)){//横向滑动
                                isTouchHorizontal=true;
                                //这里做虚化动画
                            }else {
                                isTouchHorizontal=false;
                            }
                            if(isTouchHorizontal){

                            if(curPage==1 && moveX>0)
                                return false;
                            else
                                page2Anim(moveX);

                            }
                            Log.i("滑动监听","curPage "+curPage+" x "+curx+" y "+cury+" moveX "+moveX);
                            break;
                        case MotionEvent.ACTION_UP: //滑动取消
                        case MotionEvent.ACTION_CANCEL:
                            int lastX=curx-x;
                            if(curPage==1 && lastX>0) {
                                ll_page3.setAlpha(0);
                                ll_o.setAlpha(1);
                                ll_page2_content.setVisibility(View.VISIBLE);
                                return false;
                            }
                            if(isTouchHorizontal && lastX<-120){//确定是横向翻页
                                curPage=2;
                                ll_page3.setAlpha(1);
                                ll_o.setAlpha(0);
                                //虚化背景
                                ll_page3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            }else {
                                curPage=1;
                                ll_page3.setAlpha(0);
                                ll_o.setAlpha(1);
                                ll_page2_content.setVisibility(View.VISIBLE);
                            }
                            break;
                    }

                    return true;
                }else{
                    return false;
                }
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void page2Anim(float moveX){

        float alpha=0.0f;
        if(moveX<0) {
            alpha=(-moveX)/200;
            ll_page2_content.setVisibility(View.GONE);
            ll_page3.setAlpha(alpha);
            ll_o.setAlpha(1 - (alpha));
        }else {
            alpha=(moveX/200);
            ll_page3.setAlpha(1-alpha);
            ll_o.setAlpha(alpha);
        }
        Log.i("滑动监听","alpha "+alpha);
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
