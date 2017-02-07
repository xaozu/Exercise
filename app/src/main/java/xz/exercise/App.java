package xz.exercise;

import android.app.Application;
import android.content.Context;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;

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
}
