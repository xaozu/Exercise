package xz.exercise.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;

import xz.exercise.utils.LogUtils;

/**
 * Author：zhulunjun
 * Time：2017/3/13
 * description：用贝塞尔曲线画圆
 * 可拉伸效果
 * 回弹效果
 */

public class CircleView extends View{
    private  float r=100;//圆的半径
    private static final float MAGIC= 0.55228475f;//控制点与半径的定点比值
    private Paint mPaint;//画笔
    private Path mPath=new Path();//路径
    private float mPrevX=0;//按下的x坐标
    private float mPrevY=0;//按下的y坐标
    private float mDegrees=0;//角度
    private boolean mIsDown;//是否按下

    public CircleView(Context context) {
        super(context);
        initPaint();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    //初始化画笔
    private void initPaint(){
        mPaint=new Paint();
        mPaint.setColor(Color.BLACK);//颜色是黑色
        mPaint.setStyle(Paint.Style.FILL);//填充模式，STROKE描边,FILL填充
        mPaint.setAntiAlias(true);//消除锯齿
        mPaint.setStrokeWidth(5);//画笔的宽度

        updatePath(0,0,0);

    }

    //用贝塞尔曲线画圆的路径
    float longRadius=100f,shotRadius=100f,distance=0;
    private void updatePath(float x,float y,float distanceI){
        if(distanceI==0) {
            distance = distance(mPrevX, mPrevY, x, y);//计算距离
        }else{
            distance=distanceI;
        }
        longRadius=r+distance;
        shotRadius=r - distance * 0.2f;
        if(mIsDown)
            mDegrees=points2Degrees(mPrevX,mPrevY,x,y);

        LogUtils.d("distance "+distance+" mDegrees "+mDegrees+" "+longRadius+" "+shotRadius);
        mPath.reset();
        //右上角扇形
        mPath.lineTo(0,-r);
        mPath.cubicTo(r*MAGIC,-r //控制点
                     ,longRadius,-r*MAGIC //控制点
                     ,longRadius,0); //结束点
        mPath.lineTo(0,0); //回归原点

        //右下角扇形
        mPath.lineTo(0,r);
        mPath.cubicTo(r*MAGIC,r
                     ,longRadius,r*MAGIC
                     ,longRadius,0);
        mPath.lineTo(0,0);

        //左上角扇形
        mPath.lineTo(0,-r);
        mPath.cubicTo(-r*MAGIC,-r
                     ,-shotRadius,-r*MAGIC
                     ,-shotRadius,0);
        mPath.lineTo(0,0);

        //左下角扇形
        mPath.lineTo(0,r);
        mPath.cubicTo(-r*MAGIC,r
                     ,-shotRadius,r*MAGIC
                     ,-shotRadius,0);
        mPath.lineTo(0,0);

        invalidate(); //重绘

    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        int action=event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mIsDown=true;
                mPrevX=event.getX();
                mPrevY=event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                if(!mIsDown)break;
                updatePath(event.getX(),event.getY(),0);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(!mIsDown)break;
                mIsDown=false;
                //这里加入弹跳插值器动画
                Animation animation=new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        float offset=(1-interpolatedTime)*distance(mPrevX,mPrevY,event.getX(),event.getY());
                        updatePath(mPrevX,mPrevY,offset);
                    }
                };
                animation.setDuration(800);
                animation.setInterpolator(new BounceInterpolator());
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        updatePath(mPrevX,mPrevY,0);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                startAnimation(animation);
                break;
        }
        return true;//拦截

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX=getMeasuredWidth()>>1;
        int centerY=getMeasuredHeight()>>1;

//        canvas.drawColor(0xFFDDDDDD);
        canvas.save();
        canvas.translate(centerX,centerY);
        canvas.rotate(mDegrees);
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
    }

    //计算两点之间的距离
    public float distance(float x1,float y1, float x2, float y2){
        return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    //计算两点之间的角度
    private float points2Degrees(float x1, float y1, float x2, float y2){
        double angle = Math.atan2(y2-y1,x2-x1);
        return (float) Math.toDegrees(angle);
    }
}
