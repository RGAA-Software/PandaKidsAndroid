package com.tc.android.basic;

import android.content.Context;
import android.content.SharedPreferences;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public final class SPUtils {

    private SharedPreferences sp;
    private static final String FILE_SP_NAME = "SpConfig"; //文件名
    private static SPUtils mSPUtils;

    public SPUtils(Context context) {
        sp = context.getSharedPreferences(FILE_SP_NAME, Context.MODE_PRIVATE);
    }
    //    ---------------------  String ----------------------------

    /**
     * SP中写入String
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code ""}
     */
    public String getString(String key) {
        return getString(key, "");
    }

    /**
     * SP中读取String
     *
     * @param key 键
     * @return 存在返回对应值，存在返回默认值{@code defaultValue}
     */
    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }


    //    ---------------------  int ----------------------------

    /**
     * SP中写入int
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }


    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code ""}
     */
    public int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * SP中读取int
     *
     * @param key 键
     * @return 存在返回对应值，存在返回默认值{@code defaultValue}
     */
    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }


    //    ---------------------  long ----------------------------

    /**
     * SP中写入long
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, long value) {
        sp.edit().putLong(key, value).apply();
    }

    /**
     * SP中读取long
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public long getLong(String key) {
        return getLong(key, -1L);
    }

    /**
     * SP中读取long
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }


    //    ---------------------  float ----------------------------

    /**
     * SP中写入float
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, float value) {
        sp.edit().putFloat(key, value).apply();
    }

    /**
     * SP中读取float
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值-1
     */
    public float getFloat(String key) {
        return getFloat(key, -1f);
    }

    /**
     * SP中读取float
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }


    //    ---------------------  boolean ----------------------------

    /**
     * SP中写入long
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }


    /**
     * SP中读取boolean
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code false}
     */
    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * SP中读取boolean
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    //    ---------------------  Set ----------------------------

    /**
     * SP中写入Set
     *
     * @param key   键
     * @param values 值
     */
    public void put(String key, Set<String> values) {
        sp.edit().putStringSet(key, values).apply();
    }


    /**
     * SP中读取StringSet
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值{@code Collections.<String>emptySet()}
     */
    public Set<String> getStringSet(String key) {
        return getStringSet(key, Collections.<String>emptySet());
    }

    /**
     * SP中读取StringSet
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return 存在返回对应值，不存在返回默认值{@code defaultValue}
     */
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return sp.getStringSet(key, defaultValue);
    }



    /**
     * SP中写入list
     *
     * @param key   键
     * @param values 值
     */
//    public void put(String key, List<String> values) {
//        Gson gson = new Gson();
//        //转换成json数据，再保存
//        String strJson = gson.toJson(values);
//        sp.edit().putString(key,strJson)
//            .apply();
//    }

    /**
     * SP中读取list
     *
     * @param key 键
     * @return 存在返回对应值，不存在返回默认值
     */
//    public List<String> getStringList(String key){
//        List<String> mList = new ArrayList<>();
//        String strJson = getString(key);
//        if (null == strJson) {
//            return mList;
//        }
//        Gson gson = new Gson();
//        mList = gson.fromJson(strJson, new TypeToken<List<String>>() {
//        }.getType());
//        return mList;
//    }

    /**
     * SP中是否存在该key
     *
     * @param key
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public boolean contains(String key) {
        return sp.contains(key);
    }

    /**
     * SP中移除该key
     *
     * @param key 键
     */
    public void remove(String key) {
        sp.edit().remove(key).apply();
    }


    /**
     * SP中清除所有数据
     */
    public void clear() {
        sp.edit().clear().apply();
    }
}
