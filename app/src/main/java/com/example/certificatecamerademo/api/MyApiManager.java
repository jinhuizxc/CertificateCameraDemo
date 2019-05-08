package com.example.certificatecamerademo.api;



import com.example.certificatecamerademo.ocr.bean.RepOutput;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * 作者：xin on 2018/7/9 0009 15:03
 * <p>
 * 邮箱：ittfxin@126.com
 * <p>
 * https://github.com/wzx54321/XinFrameworkLib
 */
public interface MyApiManager {


    @POST(Constant.path)
    @Headers({"Authorization: APPCODE " + Constant.APPCODE})
    Observable<RepOutput> authCard(@Body RequestBody body);
}
