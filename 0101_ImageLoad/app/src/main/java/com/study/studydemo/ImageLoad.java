package com.study.studydemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 功能：实现图片加载，并且要将图片缓存起来
 * <p>
 * Created by danke on 2017/7/6.
 */

public class ImageLoad {

    private ImageCache mImageCache = new ImageCache();
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // 线程池,线程数量为CPU的数量

    public void displayImage(final String url, final ImageView iv, final Activity activity) {
        iv.setTag(url);
        Bitmap bitmap = mImageCache.get(url);
        if (bitmap != null && iv.getTag().equals(url)) {
            iv.setImageBitmap(bitmap);
            return;
        }
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = downloadImage(url);
                if (bitmap == null) {
                    return;
                }
                if (iv.getTag().equals(url)) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.setImageBitmap(bitmap);
                        }
                    });
                }

                mImageCache.put(url, bitmap);
            }
        });
    }

    /**
     * 从网络流中获取Bitmap 图片资源
     *
     * @param imageUrl
     * @return
     */
    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
