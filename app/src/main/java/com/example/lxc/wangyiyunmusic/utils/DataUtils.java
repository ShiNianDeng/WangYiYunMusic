package com.example.lxc.wangyiyunmusic.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataUtils {
    /**
     * 从assets资源文件中读取资源数据
     * 利用StringBuilder接受文件中的数据，使用AssetsManager资源管理器的open()方法打开资源文件。
     * open()方法返回的是一个InputStream流，使用InputStreamReader(字节和字符桥接器)封装InputStream流。
     * BufferedReader()封装InputStreamReader，BufferedReader.line()方法读取资源文件中每一行的数据返回
     */

    public static String getDataFromAssets(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
