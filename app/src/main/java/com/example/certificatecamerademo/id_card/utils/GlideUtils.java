package com.example.certificatecamerademo.id_card.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.certificatecamerademo.R;

public class GlideUtils {

    public static void setImage(Context context, String url, ImageView myImageView){
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)  //设置磁盘缓存
                //.placeholder(R.drawable.icon_portrait)
                .error(R.mipmap.ic_launcher)
                .dontAnimate()
                .centerCrop()
                .into(myImageView);
    }

    public static void setImageNoCache(Context context, String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .skipMemoryCache(true) // 不使用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE) // 不使用磁盘缓存
                .dontAnimate()
                .centerCrop()
                .into(imageView);
    }

    //内存缓存清理（主线程）
    public static void clearMemoryCache(final Context context){
        Glide.get(context).clearMemory();
    }

    //磁盘缓存清理（子线程）
    public static void clearFileCache(final Context context){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

}
