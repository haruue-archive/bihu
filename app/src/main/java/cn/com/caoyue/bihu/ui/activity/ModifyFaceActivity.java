package cn.com.caoyue.bihu.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jude.utils.JActivityManager;
import com.jude.utils.JUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.com.caoyue.bihu.BuildConfig;
import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.network.ApiKeys;
import cn.com.caoyue.bihu.data.network.RestApi;
import cn.com.caoyue.bihu.data.storage.CurrentUser;
import cn.com.caoyue.bihu.data.transfer.InformationTransfer;
import cn.com.caoyue.bihu.data.transfer.UploadInformationTransfer;
import cn.com.caoyue.bihu.ui.util.GetFace;
import cn.com.caoyue.bihu.ui.widget.CircleImageView;
import cn.com.caoyue.bihu.util.CurrentState;
import cn.com.caoyue.bihu.util.GetAlbumPicture;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyFaceActivity extends AppCompatActivity {

    CircleImageView imageView;
    Bitmap selectImageBitmap;
    boolean isChangeFace = false;
    public final static int REQUEST_CODE = 142;
    Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_face);
        // Initialize JUtils & JActivityManager
        JUtils.initialize(getApplication());
        JUtils.setDebug(BuildConfig.DEBUG, "inMain");
        JActivityManager.getInstance().pushActivity(ModifyFaceActivity.this);
        // Initialize User Interface
        init();
    }

    private void init() {
        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_inModifyFace);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // Set default face
        imageView = (CircleImageView) findViewById(R.id.face);
        refreshImageView();
        // Button Listener
        findViewById(R.id.btn_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new GetAlbumPicture(ModifyFaceActivity.this, GetAlbumPicture.SELECT_PIC_FOR_FACE).startSelect();
                } catch (Exception e) {
                    JUtils.Toast(getResources().getString(R.string.cannot_get_bitmap));
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.btn_give_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageBitmap = null;
                onBackPressed();
            }
        });
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFaceImage();
            }
        });
    }

    private void uploadFaceImage() {
        // 存储当前图片
        File file = new File(getCacheDir().getAbsolutePath() + "/upload.jpg");
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            imageView.setDrawingCacheEnabled(true);
            ((BitmapDrawable) imageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, bos);
            imageView.setDrawingCacheEnabled(false);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 上传图片
        RequestBody apikey = RequestBody.create(MediaType.parse("text/plain"), ApiKeys.HARUUE_STORAGE_SERVER_2_APIKEY);
        RequestBody use = RequestBody.create(MediaType.parse("text/plain"), "face");
        RequestBody imgFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        Map<String, RequestBody> partMap = new HashMap<>();
        partMap.put("apikey", apikey);
        partMap.put("use", use);
        partMap.put("image\"; filename=\"upload.jpg\"", imgFile);
        Call<UploadInformationTransfer> uploadImageCall = RestApi.getHaruueStorageApiService().uploadImage(partMap);
        uploadImageCall.enqueue(new Callback<UploadInformationTransfer>() {
            @Override
            public void onResponse(Response<UploadInformationTransfer> response) {
                if (response.code() == 200 || null != response.body()) {
                    JUtils.Log("inModifyFace_upload_success", "server: " + response.body().server);
                    JUtils.Log("inModifyFace_upload_success", "file: " + response.body().file);
                    JUtils.Log("inModifyFace_upload_success", "url: " + response.body().url);
                    saveFaceUrl(response.body().url);
                } else {
                    JUtils.Toast(getResources().getString(R.string.upload_fail));
                    try {
                        JUtils.Log("inModifyFace_upload_failed1", "HTTP CODE " + response.code() + " Body: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                JUtils.Toast(getResources().getString(R.string.network_error));
                JUtils.Log("inModifyFace_upload_failure");
                t.printStackTrace();
            }
        });
    }

    private void saveFaceUrl(final String url) {
        Call<InformationTransfer> modifyFaceCall = RestApi.getHaruueKnowWebApiService().modifyFace(ApiKeys.HARUUE_KNOW_WEB_APIKEY, CurrentUser.getInstance().token, url);
        modifyFaceCall.enqueue(new Callback<InformationTransfer>() {
            @Override
            public void onResponse(Response<InformationTransfer> response) {
                if (response.code() == 200 && null != response.body()) {
                    // 更新本地缓存
                    CurrentUser.getInstance().face = url;
                    JUtils.Toast(getResources().getString(R.string.modify_face_success));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    }, 500);
                } else {
                    try {
                        JUtils.Log("inModifyFace_upload_failed1", "HTTP CODE " + response.code() + " Body: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JUtils.Toast(getResources().getString(R.string.upload_fail));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                JUtils.Toast(getResources().getString(R.string.network_error));
            }
        });
    }

    private void refreshImageView() {
        if (null != selectImageBitmap) {
            imageView.setImageBitmap(selectImageBitmap);
        } else {
            new GetFace(imageView, CurrentUser.getInstance().face).load();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        CurrentState.save(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CurrentState.restore(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GetAlbumPicture.SELECT_PIC_FOR_FACE:
                if (resultCode == RESULT_OK) {
                    if (null != data.getExtras() && null != data.getExtras().get("data")) {
                        selectImageBitmap = (Bitmap) data.getExtras().get("data");
                        refreshImageView();
                        isChangeFace = true;
                    } else {
                        JUtils.Toast(getResources().getString(R.string.cannot_get_bitmap));
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageView = (CircleImageView) findViewById(R.id.face);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JActivityManager.getInstance().popActivity(ModifyFaceActivity.this);
        selectImageBitmap = null;
    }

    @Override
    public void onBackPressed() {
        if (isChangeFace) {
            Intent result = new Intent();
            result.putExtra("UserFaceUrl", CurrentUser.getInstance().face);
            setResult(RESULT_OK, result);
        } else {
            setResult(RESULT_CANCELED);
        }
        super.onBackPressed();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ModifyFaceActivity.class);
        ((AppCompatActivity) context).overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);
        ((AppCompatActivity) context).startActivityForResult(intent, REQUEST_CODE);
    }
}
