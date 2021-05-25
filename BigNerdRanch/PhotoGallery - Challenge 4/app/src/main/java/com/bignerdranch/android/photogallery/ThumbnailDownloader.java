package com.bignerdranch.android.photogallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.util.Pair;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ThumbnailDownloader<T> extends HandlerThread {
    public static final int ONE_SIDED_CACHE_SIZE = 25;
    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    private static final int MESSAGE_DOWNLOAD_SET = 1;
    public ThumbnailDownloadListener<T> mThumbnailDownloadListener;
    private boolean mHasQuit = false;
    private Handler mRequestHandler;
    private Handler mResponseHandler;
    private ConcurrentMap<T, String> mRequestMap = new ConcurrentHashMap<>();
    private ConcurrentMap<String, Bitmap> mThumbnailCache = new ConcurrentHashMap<>();

    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler = responseHandler;
    }

    public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener) {
        mThumbnailDownloadListener = listener;
    }

    @Override
    public boolean quit() {
        mHasQuit = true;
        return super.quit();
    }

    public void queueThumbnail(T target, List<GalleryItem> items) {
        String url = items.get(ONE_SIDED_CACHE_SIZE).getUrl();
        Log.i(TAG, " Adres URL: " + url);
        for (int pos = 0; pos < 11; pos++) {
            String item_url = items.get(pos).getUrl();
            if (item_url != null && mThumbnailCache.get(item_url) == null) {
                mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD_SET,item_url).sendToTarget();
            }
        }
        mThumbnailDownloadListener.onThumbnailDownloaded(target, mThumbnailCache.get(url));

    }

    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T target = (T) msg.obj;
                    Log.i(TAG, "Żądanie dla adresu URL: " + mRequestMap.get(target));
                    handleRequest(target);
                    Log.i(TAG, "Żądanie zostało ukończone dla adresu URL: " + mRequestMap.get(target));
                } else if (msg.what == MESSAGE_DOWNLOAD_SET) {
                    String url = (String) msg.obj;
                    Log.i(TAG, "Żądanie MESSAGE_DOWNLOAD_SET dla adresu URL: " + url);
                    handleDownloadSetRequest(url);
                    Log.i(TAG, "Żądanie MESSAGE_DOWNLOAD_SET zostało ukończone dla adresu URL: " +url);
                }
            }
        };
    }



    private void handleDownloadSetRequest(String url){
        try {
            byte[] bitmapBytes = new FlickrFetcher().getUrlBytes(url);
            mThumbnailCache.put(url,BitmapFactory.decodeByteArray(bitmapBytes,0,bitmapBytes.length));
        }catch (IOException ioe){
            Log.i(TAG, "Żądanie dla adresu URL: " + url + "zakończyło sie nie powodzeniem");
        }
    }

    private void handleRequest(final T target) {
        try {
            final String url = mRequestMap.get(target);
            if (url == null) {
                return;
            }

            byte[] bitmapBytes = new FlickrFetcher().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            Log.i(TAG, "Bitmapa została utworzona");

            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mRequestMap.get(target) != url || mHasQuit) {
                        return;
                    }
                    mRequestMap.remove(target);
                    Log.i(TAG, "Response hand");
                    mThumbnailDownloadListener.onThumbnailDownloaded(target, bitmap);
                }
            });

        } catch (IOException ioe) {
            Log.e(TAG, "Błąd podczas pobierania zdjęcia", ioe);
        }

    }

    public void clearQueue() {
        mRequestHandler.removeMessages(MESSAGE_DOWNLOAD);
        mRequestMap.clear();
    }


    public interface ThumbnailDownloadListener<T> {
        void onThumbnailDownloaded(T target, Bitmap thumbnail);
    }
}
