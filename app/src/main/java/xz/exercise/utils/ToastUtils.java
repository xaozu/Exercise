package xz.exercise.utils;


import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import xz.exercise.App;
import xz.exercise.R;


/**
 * Created by storm on 14-4-19.
 */
public class ToastUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Object synObj = new Object();
    private static Toast toast=null;
    private ToastUtils() {
    }

    public static final int SUCCESS=1;//成功
    public static final int WARNING=2;//警告
    public static final int FAILURE=3;//失败

    /**
     *
     * @param duration
     * @param message
     * @param resId
     * @param type 1 成功 2 警告 3 失败
     */
    public static void show(final int duration, String message , int resId, int type){
        final View layout = LayoutInflater.from(App.getContext()).inflate(R.layout.toast_layout,null);
        TextView tv_toast = (TextView) layout.findViewById(R.id.tv_toast);
//        ImageView iv_toast= (ImageView) layout.findViewById(R.id.iv_toast);
//        switch (type){
//            case SUCCESS:
//                iv_toast.setImageResource(R.mipmap.toast_success);
//                break;
//            case WARNING:
//                iv_toast.setImageResource(R.mipmap.toast_warning);
//                break;
//            case FAILURE:
//                iv_toast.setImageResource(R.mipmap.toast_failure);
//                break;
//        }
        if(TextUtils.isEmpty(message)){
            tv_toast.setText(resId);
        }else {
            tv_toast.setText(message);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                synchronized (synObj) { //加上同步是为了每个toast只要有机会显示出来
                    if (toast == null) {
                        toast = new Toast(App.getContext());
                    }
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(duration);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });
//        Toast toast = new Toast(App.getContext());
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(duration);
//        toast.setView(layout);
//        toast.show();
    }
    public static void showShort(int resId,int type) {
        show(Toast.LENGTH_SHORT,null,resId,type);
    }

    public static void showShort(String message, int type) {
        show(Toast.LENGTH_SHORT,message,0,type);
    }

    public static void showLong(int resId,int type) {
        show(Toast.LENGTH_LONG,null,resId,type);
    }

    public static void showLong(String message, int type) {
        show(Toast.LENGTH_LONG,message,0,type);
    }

    public static void showDefault(String message) {
        show(Toast.LENGTH_SHORT,message,0,1);
    }


}
