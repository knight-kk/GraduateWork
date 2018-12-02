package cn.edu.abc.graduatework.ui.activity.login;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.constant.SpConstant;
import cn.edu.abc.graduatework.entity.MessageEvent;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.School;
import cn.edu.abc.graduatework.entity.User;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.ui.activity.BaseActivity;
import cn.edu.abc.graduatework.ui.activity.MainActivity;
import cn.edu.abc.graduatework.ui.activity.SelectSchoolActivity;
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

public class PerfectInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_skip)
    AppCompatTextView mTvSkip;
    @BindView(R.id.img_icon)
    CircleImageView mImgIcon;
    @BindView(R.id.tv_sex)
    AppCompatTextView mTvSex;
    @BindView(R.id.et_work)
    EditText mEtWork;
    @BindView(R.id.tv_school)
    AppCompatTextView mTvSchool;
    @BindView(R.id.btn_save)
    AppCompatButton mBtnSave;
    private BottomSheetDialog mBottomSheetDialog;
    private static final int CARMA_ACTION_CODE = 0;
    private static final int PHOTO_ACTION_CODE = 1;
    private File tempFile;
    private static final String TAG = "PerfectInfoActivity";
    private ProgressDialog mProgressDialog;
    private String[] mSexs = {"男", "女"};
    private ListPopupWindow mSexPopupWindow;
    private boolean mUploadSuccess;
    private int mSex;
    private School mSchool;
    private String mUserImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_info);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        mEtWork.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    changeButtonStatus();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent<School> event) {
        mSchool = event.getValue();
        mTvSchool.setText(mSchool.getName());
        changeButtonStatus();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.tv_skip, R.id.img_icon, R.id.tv_sex, R.id.tv_school, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_skip:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
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
            case R.id.tv_sex:
                if (mSexPopupWindow == null) {
                    mSexPopupWindow = new ListPopupWindow(this);
                    mSexPopupWindow.setAdapter(new ArrayAdapter<String>(this, R.layout.item_textview, mSexs));
                    mSexPopupWindow.setAnchorView(mTvSex);
                    mSexPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            mTvSex.setText(mSexs[position]);
                            mSex = position;
                            mSexPopupWindow.dismiss();
                        }
                    });
                }
                mSexPopupWindow.show();

                break;
            case R.id.tv_school:
                startActivity(new Intent(this, SelectSchoolActivity.class));
                break;
            case R.id.btn_save:
                User user = new User();
                user.setId(getSharedPreferences(SpConstant.SP_NAME, MODE_PRIVATE).getString(SpConstant.USER_ID, ""));
                user.setSex(mSex);
                user.setImg(mUserImage);
                user.setJob(mEtWork.getText().toString());
                user.setSchool(mSchool.getName());
                RetrofitClient.getApiService().perfectInfo(user).enqueue(new Callback<Result<String>>() {
                    @Override
                    public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                        if (response.isSuccessful()) {
                            Result<String> result = response.body();
                            if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                                Toast.makeText(PerfectInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(PerfectInfoActivity.this, MainActivity.class));
                                finish();
                                return;
                            }
                        }
                        Toast.makeText(PerfectInfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Result<String>> call, Throwable t) {
                        Toast.makeText(PerfectInfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
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
            Uri contentUri = FileProvider.getUriForFile(PerfectInfoActivity.this, "cn.edu.abc.graduatework", tempFile);
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
                            Toast.makeText(PerfectInfoActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
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
                    mUserImage = body.getValue();
                    GlideApp.with(PerfectInfoActivity.this).load(mUserImage)
                            .placeholder(R.drawable.camera_icon)
                            .error(R.drawable.error_img)
                            .fitCenter()
                            .into(mImgIcon);
                    mProgressDialog.dismiss();
                    Toast.makeText(PerfectInfoActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                    mUploadSuccess = true;
                    changeButtonStatus();
                } else {
                    mProgressDialog.dismiss();
                    Toast.makeText(PerfectInfoActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(PerfectInfoActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void changeButtonStatus() {
        mBtnSave.setBackgroundResource(R.drawable.bg_btn_gray);
        mBtnSave.setEnabled(false);

        if (!mUploadSuccess) {
            return;
        } else if (StringUtil.isEmpty(mTvSex.getText())) {
            return;
        } else if (StringUtil.isEmpty(mEtWork.getText())) {
            return;
        } else if (StringUtil.isEmpty(mTvSchool.getText())) {
            return;
        }


        mBtnSave.setBackgroundResource(R.drawable.bg_btn_orange);
        mBtnSave.setEnabled(true);

    }
}
