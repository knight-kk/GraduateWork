package cn.edu.abc.graduatework.ui.activity.user;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.constant.SpConstant;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.User;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.ui.activity.BaseActivity;
import cn.edu.abc.graduatework.ui.activity.login.LoginActivity;
import cn.edu.abc.graduatework.viewmodel.UserViewModel;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.img_avatar)
    CircleImageView mImgAvatar;
    @BindView(R.id.ll_avatar)
    LinearLayout mLlAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.ll_name)
    LinearLayout mLlName;
    @BindView(R.id.tv_email)
    TextView mTvEmail;
    @BindView(R.id.ll_email)
    LinearLayout mLlEmail;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.tv_job)
    TextView mTvJob;
    @BindView(R.id.ll_job)
    LinearLayout mLlJob;
    @BindView(R.id.tv_school)
    TextView mTvSchool;
    @BindView(R.id.ll_school)
    LinearLayout mLlSchool;
    @BindView(R.id.btn_logout)
    Button mBtnLogout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private BottomSheetDialog mBottomSheetDialog;
    private static final int CARMA_ACTION_CODE = 0;
    private static final int PHOTO_ACTION_CODE = 1;
    private File tempFile;
    private ProgressDialog mProgressDialog;
    private UserViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initEvent();

        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.getLiveData().observe(this, new Observer<Result<User>>() {
            @Override
            public void onChanged(@Nullable Result<User> userResult) {
                updateUI(userResult);
            }
        });
        mViewModel.getUserInfo(getSharedPreferences(SpConstant.SP_NAME, MODE_PRIVATE).getString(SpConstant.USER_ID, ""));
    }

    private void initEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateUI(Result<User> userResult) {
        if (userResult != null) {
            if (userResult.getCode() == ResultEnum.SUCCESS.getCode()) {
                User user = userResult.getValue();
                GlideApp.with(this).load(user.getImg())
                        .placeholder(R.drawable.profile)
                        .error(R.drawable.error_img)
                        .fitCenter()
                        .into(mImgAvatar);
                String name = user.getName() != null ? user.getName() : "";
                String email = user.getEmail() != null ? user.getEmail() : "";
                String sex = (user.getSex() != null ? user.getSex() : 0) == 0 ? "男" : "女";
                String job = user.getJob() != null ? user.getJob() : "";
                String school = user.getSchool() != null ? user.getSchool() : "";
                mTvName.setText(name);
                mTvEmail.setText(email);
                mTvSex.setText(sex);
                mTvJob.setText(job);
                mTvSchool.setText(school);
            } else {
                Toast.makeText(this, "数据获取失败", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @OnClick({R.id.ll_avatar, R.id.ll_name, R.id.ll_email, R.id.ll_sex, R.id.ll_job, R.id.ll_school, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_avatar:
                if (mBottomSheetDialog == null) {
                    initSelectPhotoDialog();
                }
                mBottomSheetDialog.show();
                break;
            case R.id.ll_name:
                break;
            case R.id.ll_email:
                break;
            case R.id.ll_sex:
                break;
            case R.id.ll_job:
                break;
            case R.id.ll_school:
                break;
            case R.id.btn_logout:
                SharedPreferences.Editor editor = getSharedPreferences(SpConstant.SP_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(SpConstant.IS_LOGIN, false);
                editor.putString(SpConstant.USER_PASSWORD, "");
                editor.apply();
                startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
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
            Uri contentUri = FileProvider.getUriForFile(UserInfoActivity.this, "cn.edu.abc.graduatework", tempFile);
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
                            Toast.makeText(UserInfoActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                        }
                    }).launch();
        }
    }

    /** 图片上传
     * @param file 上传的图片文件
     */
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
                    GlideApp.with(UserInfoActivity.this).load(body.getValue())
                            .placeholder(R.drawable.camera_icon)
                            .error(R.drawable.error_img)
                            .fitCenter()
                            .into(mImgAvatar);
                    mProgressDialog.dismiss();
                    Toast.makeText(UserInfoActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();
                    User user = new User();
                    user.setImg(body.getValue());
                    saveInfo(user);
                } else {
                    mProgressDialog.dismiss();
                    Toast.makeText(UserInfoActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(UserInfoActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void saveInfo(User user) {
        user.setId(getSharedPreferences(SpConstant.SP_NAME, MODE_PRIVATE).getString(SpConstant.USER_ID, ""));
        RetrofitClient.getApiService().perfectInfo(user).enqueue(new Callback<Result<String>>() {
            @Override
            public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                if (response.isSuccessful()) {
                    Result<String> result = response.body();
                    if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                        Toast.makeText(UserInfoActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                Toast.makeText(UserInfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                Toast.makeText(UserInfoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
