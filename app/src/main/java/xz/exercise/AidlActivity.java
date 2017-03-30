package xz.exercise;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import xz.exercise.base.BaseActivity;
import xz.exercise.service.AidlService;
import xz.exercise.utils.LogUtils;
import xz.exercise.utils.ToastUtils;

/**
 * Author：zhulunjun
 * Time：2017/3/29
 * description：
 */

public class AidlActivity extends BaseActivity {
    private AidlConnection mConnection;
    @Override
    protected int getLayoutView() {
        return R.layout.activity_aidl;
    }

    @Override
    protected BaseActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConnection=new AidlConnection();
        bindService(new Intent(this,AidlService.class),mConnection,BIND_AUTO_CREATE);
    }

    //服务连接状态监听
    private class AidlConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //绑定成功的回调
            IShop shop=IShop.Stub.asInterface(iBinder);
            try {
                LogUtils.d("绑定成功");
                shop.sell();

                shop.setProduct(new Product("南瓜","2.00"));
                Product product=shop.buy();

                ToastUtils.showDefault(product.getName()+" "+product.getPrice());
                LogUtils.d("绑定成功1"+product.getName());
            } catch (RemoteException e) {
                e.printStackTrace();
                LogUtils.d("错误");
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.d("绑定错误");
        }
    }
}
