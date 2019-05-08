package com.example.certificatecamerademo.ocr.model;



import com.example.certificatecamerademo.api.Constant;
import com.example.certificatecamerademo.http.HttpHelper;
import com.example.certificatecamerademo.api.MyApiManager;
import com.example.certificatecamerademo.ocr.bean.RepOutput;
import com.example.certificatecamerademo.ocr.bean.ReqInput;
import com.example.certificatecamerademo.ocr.contract.OcrContract;
import com.example.certificatecamerademo.utils.OcrUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 作者：xin on 2018/7/9 0009 15:03
 * <p>
 * 邮箱：ittfxin@126.com
 * <p>
 * https://github.com/wzx54321/XinFrameworkLib
 */

public class OCRModel implements OcrContract.Model{


    public Observable<RepOutput> AuthCard(final boolean isFace, final String imgPath) {

        return Observable.create(new ObservableOnSubscribe<ReqInput>() {
            @Override
            public void subscribe(ObservableEmitter<ReqInput> e) throws Exception {
                JSONObject configObj = new JSONObject();
                if (isFace) {
                    configObj.put("side", "face");
                } else {
                    configObj.put("side", "back");
                }
                ReqInput input = new ReqInput();
                input.setImage(OcrUtils.getImageStr(imgPath));
                input.setConfigure(configObj.toString());
                e.onNext(input);

            }
        }).flatMap(new Function<ReqInput, Observable<RepOutput>>() {
            @Override
            public Observable<RepOutput> apply(ReqInput reqInput) throws Exception {
                MyApiManager apiManager = HttpHelper.getInstance()
                        .getRetrofit(Constant.host).create(MyApiManager.class);
                Gson gson = new Gson();
                String reqStr = gson.toJson(reqInput);
                RequestBody body = RequestBody
                        .create(MediaType.parse("application/json; charset=utf-8"), reqStr);
                return apiManager.authCard(body);
            }
        });


    }

}
