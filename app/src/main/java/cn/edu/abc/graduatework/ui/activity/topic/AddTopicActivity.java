package cn.edu.abc.graduatework.ui.activity.topic;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.constant.SpConstant;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.ui.activity.BaseActivity;
import cn.edu.abc.graduatework.util.StringUtil;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class AddTopicActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.img_icon)
    CircleImageView mImgIcon;
    @BindView(R.id.et_title)
    AppCompatEditText mEtTitle;
    @BindView(R.id.et_info)
    AppCompatEditText mEtInfo;
    @BindView(R.id.btn_submit)
    AppCompatButton mBtnSubmit;
    @BindView(R.id.ck_rule)
    CheckBox mCkRule;
    private AppCompatDialog mTipDialog;
    private BottomSheetDialog mBottomSheetDialog;
    private static final int CARMA_ACTION_CODE = 0;
    private static final int PHOTO_ACTION_CODE = 1;
    private File tempFile;
    private ProgressDialog mProgressDialog;
    private String mTopicImage;
    private boolean mUploadSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mStatusBarColor = getResources().getColor(R.color.colorPrimary);
        useStatusBarColor = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @OnClick({R.id.img_icon, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_icon:
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 0);
                if (mBottomSheetDialog == null) {
                    initSelectPhotoDialog();
                }
                mBottomSheetDialog.show();
                break;
            case R.id.btn_submit:
                if (!mUploadSuccess) {
                    Toast.makeText(this, "请上传图片", Toast.LENGTH_SHORT).show();
                    return;
                } else if (StringUtil.isEmpty(mEtTitle.getText())) {
                    Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
                    return;
                } else if (StringUtil.isEmpty(mEtInfo.getText())) {
                    Toast.makeText(this, "请输入话题介绍", Toast.LENGTH_SHORT).show();
                    return;
                }
                Topic topic = new Topic();
                topic.setImgUrl(mTopicImage);
                topic.setTitle(mEtTitle.getText().toString());
                topic.setContent(mEtInfo.getText().toString());
                topic.setUserId(getSharedPreferences(SpConstant.SP_NAME, MODE_PRIVATE).getString(SpConstant.USER_ID, ""));
                RetrofitClient.getApiService().createTopic(topic).enqueue(new Callback<Result<Topic>>() {
                    @Override
                    public void onResponse(Call<Result<Topic>> call, Response<Result<Topic>> response) {
                        if (response.isSuccessful()) {
                            if (mTipDialog == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddTopicActivity.this);
                                builder.setMessage("提交成功，需后台审核");
                                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                mTipDialog = builder.create();
                                mTipDialog.setCancelable(false);
                            }
                            mTipDialog.show();
                        } else {
                            Toast.makeText(AddTopicActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<Topic>> call, Throwable t) {
                        Toast.makeText(AddTopicActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }

    private void initSelectPhotoDialog() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        mBottomSheetDialog.setContentView(R.layout.dialog_select_photo);
        AppCompatTextView tvCamera = mBottomSheetDialog.getDelegate().findViewById(R.id.tv_camera);
        AppCompatTextView tvPhoto = mBottomSheetDialog.getDelegate().findViewById(R.id.tv_photo);
        AppCompatTextView tvCancel = mBottomSheetDialog.getDelegate().findViewById(R.id.tv_cancel);
        if (tvCancel != null) {
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBottomSheetDialog.dismiss();
                }
            });
        }
        if (tvCamera != null) {
            tvCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectCarma();
                    mBottomSheetDialog.dismiss();
                }
            });
        }

        if (tvPhoto != null) {
            tvPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectPhoto();
                    mBottomSheetDialog.dismiss();
                }
            });
        }

    }

    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_ACTION_CODE);
    }

    private void selectCarma() {
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(AddTopicActivity.this, "cn.edu.abc.graduatework", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CARMA_ACTION_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CARMA_ACTION_CODE:   //调用相机后返回
                if (data != null) {
                    compressImage();//压缩图片上传
                }
                break;
            case PHOTO_ACTION_CODE:
                if (data != null) {
                    Uri uri = data.getData();
                    //获取照片路径
                    String[] filePathColumn = {MediaStore.Audio.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        String path = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
                        cursor.close();
                        tempFile = new File(path);
                        compressImage();//压缩图片上传
                    }
                }
                break;
        }
    }

    private void compressImage() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("图片上传中……");
        }
        mProgressDialog.show();

        if (tempFile != null) {
            Luban.with(this)
                    .load(tempFile)
                    .ignoreBy(100)
                    .setTargetDir(Environment.getExternalStorageDirectory().getPath())
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }

                        @Override
                        public void onSuccess(File file) {
                            uploadImage(file);
                        }

                        @Override
                        public void onError(Throwable e) {
                            mProgressDialog.dismiss();
                            Toast.makeText(AddTopicActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                        }
                    }).launch();
        }
    }

    private void uploadImage(File file) {
        final RequestBody requestBody = RequestBody.
                create(MediaType.parse(MultipartBody.FORM.toString()), file);
        final MultipartBody.Part part = MultipartBody.Part.
                createFormData("file", file.getName(), requestBody);
        RetrofitClient.getApiService().upload(part).enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                if (response.isSuccessful()) {
                    Result<String> body = response.body();
                    mTopicImage = body.getValue();
                    GlideApp.with(AddTopicActivity.this).load(mTopicImage)
                            .placeholder(R.drawable.camera_icon)
                            .error(R.drawable.error_img)
                            .fitCenter()
                            .into(mImgIcon);
                    mProgressDialog.dismiss();
                    Toast.makeText(AddTopicActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                    mUploadSuccess = true;
//                    changeButtonStatus();
                } else {
                    mProgressDialog.dismiss();
                    Toast.makeText(AddTopicActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(AddTopicActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
