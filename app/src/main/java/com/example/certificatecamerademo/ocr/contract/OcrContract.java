package com.example.certificatecamerademo.ocr.contract;

import android.app.Activity;

import com.example.certificatecamerademo.ocr.bean.RepOutput;

import io.reactivex.Observable;


/**
 * 作者：xin on 2018/7/12 0012 16:55
 * <p>
 * 邮箱：ittfxin@126.com
 * <p>
 * * <p>
 * https://github.com/wzx54321/XinFrameworkLib
 */

public interface OcrContract {

    interface View {

        Activity getActivity();
    }

    interface Presenter {

        void request(boolean isFace, String imgPath);
    }


    interface Model {

        Observable<RepOutput> AuthCard(boolean isFace, String imgPath);
    }
}
