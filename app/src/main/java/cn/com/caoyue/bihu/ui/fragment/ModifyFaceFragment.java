package cn.com.caoyue.bihu.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.utils.JUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.com.caoyue.bihu.R;
import cn.com.caoyue.bihu.data.network.ApiKeys;
import cn.com.caoyue.bihu.data.network.RestApi;
import cn.com.caoyue.bihu.data.storage.CurrentFragment;
import cn.com.caoyue.bihu.data.storage.CurrentSelectImageBitmap;
import cn.com.caoyue.bihu.data.storage.CurrentUser;
import cn.com.caoyue.bihu.data.transfer.InformationTransfer;
import cn.com.caoyue.bihu.data.transfer.UploadInformationTransfer;
import cn.com.caoyue.bihu.ui.activity.MainActivity;
import cn.com.caoyue.bihu.ui.util.GetFace;
import cn.com.caoyue.bihu.ui.widget.CircleImageView;
import cn.com.caoyue.bihu.util.GetAlbumPicture;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyFaceFragment extends Fragment {

    View view;
    CircleImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CurrentFragment.getInstance().storage(ModifyFaceFragment.this);
        ((MainActivity) getActivity()).setCurrentFragmentName("ModifyFaceFragment");
        view = inflater.inflate(R.layout.fragment_modify_face, container, false);
        imageView = (CircleImageView) view.findViewById(R.id.face);
        if (null == CurrentSelectImageBitmap.bitmap) {
            new GetFace(imageView, CurrentUser.getInstance().face).load();
        } else {
            imageView.setImageBitmap(CurrentSelectImageBitmap.bitmap);
        }
        view.findViewById(R.id.btn_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetAlbumPicture(getActivity(), GetAlbumPicture.SELECT_PIC_FOR_FACE).startSelect();
            }
        });
        view.findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 存储当前图片
                File file = new File(getContext().getCacheDir().getAbsolutePath() + "/upload.jpg");
                try {
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                    imageView.setDrawingCacheEnabled(true);
                    ((BitmapDrawable)imageView.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, bos);
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
                            try {
                                JUtils.Log("inModifyFace_upload_success", "HTTP CODE " + response.code() + " Body: " + response.errorBody().string());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            saveFaceToDatabase(response.body().url);
                        } else {
                            JUtils.Toast(getResources().getString(R.string.upload_fail));
                            try {
                                JUtils.Log("inModifyFace_upload_failed1", "HTTP CODE " + response.code() + " Body: " + response.errorBody().string());
                            } catch (IOException e) {
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
        });
        view.findViewById(R.id.btn_give_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentSelectImageBitmap.clean();
                new GetFace(imageView, CurrentUser.getInstance().face).load();
            }
        });
        return view;
    }

    public void saveFaceToDatabase(final String url) {
        Call<InformationTransfer> modifyFaceCall = RestApi.getHaruueKnowWebApiService().modifyFace(ApiKeys.HARUUE_KNOW_WEB_APIKEY, CurrentUser.getInstance().token, url);
        modifyFaceCall.enqueue(new Callback<InformationTransfer>() {
            @Override
            public void onResponse(Response<InformationTransfer> response) {
                if (response.code() == 200 && null != response.body()) {
                    JUtils.Toast(response.body().info);
                    // 更新本地缓存
                    CurrentUser.getInstance().face = url;
                    JUtils.Toast(getResources().getString(R.string.modify_face_success));
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

    @Override
    public void onResume() {
        super.onResume();
        if (null != CurrentSelectImageBitmap.bitmap) {
            imageView.setImageBitmap(CurrentSelectImageBitmap.bitmap);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CurrentSelectImageBitmap.clean();
        CurrentFragment.clean();
    }
}
