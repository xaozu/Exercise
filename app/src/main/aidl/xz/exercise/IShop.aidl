// IShop.aidl
package xz.exercise;

import xz.exercise.Product;

// Declare any non-default types here with import statements

interface IShop {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     * 主要是告诉你AIDL支持哪些数据类型传输
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);
    //自己定义方法，供之后调用
    void sell();

    Product buy();

    void setProduct(in Product product);
}
