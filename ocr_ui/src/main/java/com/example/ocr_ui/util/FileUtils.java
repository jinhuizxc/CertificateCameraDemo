package com.example.ocr_ui.util;

import android.content.Context;

import com.example.ocr_ui.camera.IDCardCameraActivity;

import java.io.File;

/**
 * 作者： 周旭 on 2017年9月20日 0020.
 * 邮箱：374952705@qq.com
 * 博客：http://www.jianshu.com/u/56db5d78044d
 */

public class FileUtils {

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    /**
     * 返回的图片信息 front = /data/user/0/com.jxaic.wsdj/files/pic.jpg
     *
     * 图片路径1 /data/user/0/com.jxaic.wsdj/files/front/pic.jpg
     *
     * TODO
     * @param context
     * @return
     */
    public static File getSaveFileFront(Context context) {

//        File file = new File(context.getFilesDir(), "idCardFront.jpg");
//        Logger.d("图片路径 " + file.getAbsolutePath());
        // D/zx_android: │ 图片路径 /data/user/0/com.jxaic.wsdj/files/pic.jpg
        // 重新定义file路径
//        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "idCard/front/pic.jpg");
//        Logger.d("图片路径1 " + file.getAbsolutePath());
        // D/zx_android: │ 图片路径1 /storage/emulated/0/idCard/front/pic.jpg

        File file = new File(context.getExternalCacheDir(), "idCardFront.jpg");
        return file;
    }

    public static File getSaveFileBack(Context context) {
        return new File(context.getFilesDir(), "idCard/back/pic.jpg");
    }

    public static File getFile(Context context, String type) {
        switch (type) {
            case IDCardCameraActivity.CONTENT_TYPE_ID_CARD_FRONT:
                return new File(context.getFilesDir(), "idCardFront.jpg");
            case IDCardCameraActivity.CONTENT_TYPE_ID_CARD_BACK:
                return new File(context.getFilesDir(), "idCardBack.jpg");
        }
        return new File(context.getFilesDir(), "picture.jpg");
    }

    /**
     * @return 拍摄图片裁剪文件
     */
    public static File getCropFile(Context context, String type) {
        switch (type) {
            case IDCardCameraActivity.CONTENT_TYPE_ID_CARD_FRONT:
                return new File(context.getFilesDir(), "idCardFrontCrop.jpg");
            case IDCardCameraActivity.CONTENT_TYPE_ID_CARD_BACK:
                return new File(context.getFilesDir(), "idCardBackCrop.jpg");
        }
        return new File(context.getExternalCacheDir(), "pictureCrop.jpg");
    }

}
