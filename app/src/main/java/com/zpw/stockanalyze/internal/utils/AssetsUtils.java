package com.zpw.stockanalyze.internal.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AssetsUtils {
    public static String ANCHOR_VALUE = "";
    public static String ANCHOR_GROW = "";
    public static String ANCHOR = "";
    /**
     * 读取assets本地配置文件
     * @param context
     * @return
     */
    public static void getData(Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("anchor_value_stocks.txt")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(",");
            }
            ANCHOR_VALUE = stringBuilder.toString();

            stringBuilder = new StringBuilder();
            bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("anchor_grow_stocks.txt")));
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(",");
            }
            ANCHOR_GROW = stringBuilder.toString();

            stringBuilder = new StringBuilder();
            bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("anchor_stocks.txt")));
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(",");
            }
            ANCHOR = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String TOKEN = "";

    /**
     * 获取token
     * @param fileName
     * @param context
     * @return
     */
    public static void getToken(String fileName, Context context) {
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                TOKEN = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static float GDP = 0f;

    /**
     * 获取gdp
     * @param fileName
     * @param context
     * @return
     */
    public static void getGdp(String fileName, Context context) {
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                GDP = Float.parseFloat(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final Map<String, String> API_ARGS = new HashMap<>();

    /**
     * 获取API返回字段的属性
     * @param fileName
     * @param context
     * @return
     */
    public static void getAPIArgs(String fileName, Context context) {
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                String[] ss = line.split("=");
                API_ARGS.put(ss[0], ss[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
