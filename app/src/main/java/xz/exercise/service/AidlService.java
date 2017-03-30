package xz.exercise.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import xz.exercise.IShop;
import xz.exercise.Product;
import xz.exercise.utils.LogUtils;

/**
 * Author：zhulunjun
 * Time：2017/3/29
 * description：aidl的服务端
 */

public class AidlService extends Service {
    private Product mProduct;
    //供通讯的binder
    private Binder mBinder=new IShop.Stub(){

        @Override
        public void sell() throws RemoteException {
            LogUtils.d("客官,您需要点什么?");
        }

        @Override
        public Product buy() throws RemoteException {
            return mProduct;
        }

        @Override
        public void setProduct(Product product) throws RemoteException {
            mProduct=product;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
