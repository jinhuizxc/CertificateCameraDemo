package com.example.certificatecamerademo.id_card;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.certificatecamerademo.R;
import com.example.certificatecamerademo.id_card.utils.GlideUtils;
import com.example.certificatecamerademo.ocr.contract.OcrContract;
import com.example.certificatecamerademo.ocr.presenter.OCRPresenter;
import com.example.ocr_ui.camera.IDCardCameraActivity;
import com.example.ocr_ui.util.FileUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 做身份证识别的拍照界面, 拍照页面, 正面，反面;
 * <p>
 * 拍照取景，图库取景，打开闪光灯;
 * <p>
 * TODO 待优化;
 * 1. 图片裁剪后的页面大小调整;
 * 2. 阿里身份证识别;
 */
public class IDCardActivity extends AppCompatActivity implements OcrContract.View {

    @BindView(R.id.iv_image_front)
    ImageView ivImageFront;
    @BindView(R.id.rl_card_front)
    RelativeLayout rlCardFront;
    @BindView(R.id.iv_image_reverse)
    ImageView ivImageReverse;
    @BindView(R.id.rl_card_reverse)
    RelativeLayout rlCardReverse;
    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.tv_front)
    TextView tvFront;
    @BindView(R.id.tv_back)
    TextView tvBack;


    private static final int REQUEST_CODE_CAMERA = 102; //照相机扫描的请求码
    // 网络请求测试阿里身份证识别;
    private OCRPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);
        ButterKnife.bind(this);

        presenter = new OCRPresenter(this);

    }


    @OnClick({R.id.rl_card_front, R.id.rl_card_reverse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_card_front:
                scanFront();
                break;
            case R.id.rl_card_reverse:
                scanBack();
                break;
        }
    }

    // 调用拍摄身份证正面 activity
    private void scanFront() {
        Intent intent = new Intent(this, IDCardCameraActivity.class);
        // 设置临时存储
        intent.putExtra(IDCardCameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtils.getFile(getApplicationContext(), IDCardCameraActivity.CONTENT_TYPE_ID_CARD_FRONT)
                        .getAbsolutePath());
        //设置扫描的身份证的类型（正面front还是反面back）
        intent.putExtra(IDCardCameraActivity.KEY_CONTENT_TYPE, IDCardCameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    //调用拍摄身份证反面activity
    private void scanBack() {
        Intent intent = new Intent(IDCardActivity.this, IDCardCameraActivity.class);
        intent.putExtra(IDCardCameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtils.getFile(getApplicationContext(), IDCardCameraActivity.CONTENT_TYPE_ID_CARD_BACK)
                        .getAbsolutePath());
        intent.putExtra(IDCardCameraActivity.KEY_CONTENT_TYPE, IDCardCameraActivity.CONTENT_TYPE_ID_CARD_BACK);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    /**
     * 处理返回的图片信息,
     * 1. 先显示图片设置到imageview上;
     * 2. 进行识别;
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //如果拍摄类型是身份证
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(IDCardCameraActivity.KEY_CONTENT_TYPE);
                if (!TextUtils.isEmpty(contentType)) {
                    //判断是身份证正面还是反面
                    if (IDCardCameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        String filePath = FileUtils
                                .getFile(getApplicationContext(), IDCardCameraActivity.CONTENT_TYPE_ID_CARD_FRONT)
                                .getPath();
                        Log.d("返回的图片信息 front = ", filePath);
                        GlideUtils.setImageNoCache(this, filePath, ivImageFront);
//                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);

                        // 请求识别;
                        requestAuth(true, filePath);
                    } else if (IDCardCameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        String filePath = FileUtils
                                .getFile(getApplicationContext(), IDCardCameraActivity.CONTENT_TYPE_ID_CARD_BACK)
                                .getPath();
                        Log.d("返回的图片信息 back = ", filePath);
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        ivImageReverse.setImageBitmap(bitmap);
                        GlideUtils.setImageNoCache(this, filePath, ivImageReverse);
//                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                        // 请求识别;
                        requestAuth(false, filePath);
                    }
                }
            }
        }
    }

    private void requestAuth(boolean mIsFace, String picPath) {
        presenter.request(mIsFace, picPath);
    }


    @Override
    public Activity getActivity() {
        return this;
    }
}
