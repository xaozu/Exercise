package xz.exercise;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;

import xz.exercise.exception.Cockroach;
import xz.exercise.utils.LogUtils;
import xz.exercise.utils.ToastUtils;

/**
 * Author：zhulunjun
 * Time：2017/2/6
 * description：
 */

public class App extends Application {
    private static App sApp;
    @Override
    public void onCreate() {
        super.onCreate();
        sApp=this;
        addCrash();
        AVOSCloud.initialize(this,"EEEiGNisHcckew7CtSYAxn11-gzGzoHsz", "8QiUSM3fqHKXXAvktbVCF40X");
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(this, true);
    }

    public static App getApplication(){
        return sApp;
    }

    public static Context getContext(){
        return sApp.getApplicationContext();
    }

    //添加异常捕获
    private void addCrash(){
        Cockroach.install(new Cockroach.ExceptionHandler() {

            // handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException

            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            LogUtils.d("Cockroach "+thread + "\n" + throwable.toString());
                            throwable.printStackTrace();
                            ToastUtils.showDefault("Exception Happend\n" + thread + "\n" + throwable.toString());
//                        throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
    }
}
