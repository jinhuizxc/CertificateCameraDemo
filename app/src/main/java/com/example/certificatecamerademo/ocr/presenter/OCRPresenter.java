package com.example.certificatecamerademo.ocr.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.certificatecamerademo.ocr.bean.RepOutput;
import com.example.certificatecamerademo.ocr.contract.OcrContract;
import com.example.certificatecamerademo.ocr.model.OCRModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：xin on 2018/7/9 0009 15:03
 * <p>
 * 邮箱：ittfxin@126.com
 * <p>
 * https://github.com/wzx54321/XinFrameworkLib
 */
public class OCRPresenter implements OcrContract.Presenter {

    private OcrContract.Model model;
    private OcrContract.View view;

    public OCRPresenter(OcrContract.View view) {
        this.view = view;
        model = new OCRModel();
    }


    /**
     * 错误信息:
     * 1. 9.0 网络请求
     *  android:usesCleartextTraffic="true"
     *  2. I/HTTPLOG: retrofit2.adapter.rxjava2.HttpException: HTTP 403 Forbidden
     *  原因是app code不对;
     *  3. 463 463 http://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json
     *  不是正确的身份证信息;
     *  4. 正确的身份证信息的话就是弹出正确的信息toast
     * @param isFace
     * @param imgPath
     */
    public void request(boolean isFace, final String imgPath) {
        model.AuthCard(isFace, imgPath).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<RepOutput>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    //  <-- 200 OK http://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json (16438ms）
                    @Override
                    public void onNext(RepOutput repOutput) {
                        if (repOutput != null)
                            Log.i("HTTPLOG", repOutput.toString());
                        // http://dm-51.data.aliyun.com/rest/160601/ocr/ocr_idcard.json
                        Toast.makeText(view.getActivity(), "" + repOutput.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("HTTPLOG", e.toString());
                        Toast.makeText(view.getActivity(), "" + e.toString(), Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

}
