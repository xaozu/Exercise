package xz.exercise;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author：zhulunjun
 * Time：2017/3/29
 * description：aidl传递的对象
 */

public class Product implements Parcelable {
    private String name;
    private String price;

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.price);
    }

    //手动实现这个方法
    public void readFromParcel(Parcel dest) {
        //注意，这里的读取顺序要writeToParcel()方法中的写入顺序一样
        name = dest.readString();
        price = dest.readString();
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.name = in.readString();
        this.price = in.readString();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
