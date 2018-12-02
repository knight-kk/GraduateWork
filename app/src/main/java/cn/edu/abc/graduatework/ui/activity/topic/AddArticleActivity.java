package cn.edu.abc.graduatework.ui.activity.topic;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.constant.SpConstant;
import cn.edu.abc.graduatework.entity.Article;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.ui.activity.BaseActivity;
import cn.edu.abc.graduatework.util.StringUtil;
import jp.wasabeef.richeditor.RichEditor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class AddArticleActivity extends BaseActivity {

    @BindView(R.id.tv_image)
    IconTextView mTvImage;
    @BindView(R.id.tv_bold)
    IconTextView mTvBold;
    @BindView(R.id.tv_italic)
    IconTextView mTvItalic;
    @BindView(R.id.editor)
    RichEditor mEditor;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_text_size)
    IconTextView mTvTextSize;
    @BindView(R.id.tv_align_left)
    IconTextView mTvAlignLeft;
    @BindView(R.id.tv_align_center)
    IconTextView mTvAlignCenter;
    @BindView(R.id.tv_align_right)
    IconTextView mTvAlignRight;
    @BindView(R.id.tv_commit)
    TextView mTvCommit;
    @BindView(R.id.et_title)
    AppCompatEditText mEtTitle;
    @BindView(R.id.line)
    View mLine;
    private ListPopupWindow mSelectTextSizePopupWindow;
    private String[] textSizes = {"H1", "H2", "H3", "H4", "H5", "H6"};
    private BottomSheetDialog mBottomSheetDialog;
    private static final int CARMA_ACTION_CODE = 0;
    private static final int PHOTO_ACTION_CODE = 1;
    private File tempFile;
    private ProgressDialog mProgressDialog;
    private String mUploadImage = "";
    private boolean isFirst = true;
    private static final String MY_STYLE_CSS = "file:///android_asset/my_style.css";
    public static final String TOPIC_ID = "topic_id";
    private String topicId;
    //    @BindView(R.id.preview)
//    TextView mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);
        ButterKnife.bind(this);
        topicId = getIntent().getExtras().getString(TOPIC_ID);

        mEditor = (RichEditor) findViewById(R.id.editor);
//        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(16);
        mEditor.setEditorFontColor(Color.parseColor("#333333"));
        mEditor.setPadding(10, 10, 10, 10);
        //mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        mEditor.setPlaceholder("Insert text here...");
        mEditor.loadCSS(MY_STYLE_CSS);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
//                mPreview.setText(text);
            }
        });

    }

    @OnClick({R.id.tv_image, R.id.tv_text_size, R.id.tv_bold,
            R.id.tv_italic, R.id.tv_align_left, R.id.tv_align_center,
            R.id.tv_align_right, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_image:
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
            case R.id.tv_text_size:
                if (mSelectTextSizePopupWindow == null) {
                    mSelectTextSizePopupWindow = new ListPopupWindow(this);
                    mSelectTextSizePopupWindow.setWidth(mTvTextSize.getWidth() * 2);
                    mSelectTextSizePopupWindow.setAdapter(
                            new ArrayAdapter<String>(this,
                                    R.layout.item_textview,
                                    textSizes));
                    mSelectTextSizePopupWindow.setAnchorView(mTvTextSize);
                    mSelectTextSizePopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            mTvTextSize.setText(textSizes[position] + "{md-arrow-drop-down #333333}");
                            mEditor.setHeading(position + 1);
                            mSelectTextSizePopupWindow.dismiss();
                        }
                    });
                }
                mSelectTextSizePopupWindow.show();
                break;
            case R.id.tv_bold:
                mEditor.setBold();
                break;
            case R.id.tv_italic:
                mEditor.setItalic();
                break;
            case R.id.tv_align_left:
                mEditor.setAlignLeft();
                break;
            case R.id.tv_align_center:
                mEditor.setAlignCenter();
                break;
            case R.id.tv_align_right:
                mEditor.setAlignRight();
                break;

            case R.id.tv_commit:
                if (StringUtil.isEmpty(mEtTitle.getText())) {
                    Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                Article article = new Article();
                String title = mEtTitle.getText().toString().trim();
                article.setTitle(title);
                article.setUserId(getSharedPreferences(SpConstant.SP_NAME, MODE_PRIVATE).getString(SpConstant.USER_ID, ""));
                article.setImgUrl(mUploadImage);
                article.setContent(mEditor.getHtml());
                article.setTopicId(topicId);
                RetrofitClient.getApiService().addArticle(article).enqueue(new Callback<Result<Article>>() {
                    @Override
                    public void onResponse(Call<Result<Article>> call, Response<Result<Article>> response) {
                        if (response.isSuccessful()) {
                            Result<Article> result = response.body();
                            if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                                Toast.makeText(AddArticleActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            }
                        }
                        Toast.makeText(AddArticleActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Result<Article>> call, Throwable t) {
                        Toast.makeText(AddArticleActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
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
            Uri contentUri = FileProvider.getUriForFile(AddArticleActivity.this, "cn.edu.abc.graduatework", tempFile);
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
                            Toast.makeText(AddArticleActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
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
                    if (isFirst) {
                        isFirst = false;
                        mUploadImage = body.getValue();
                    }
                    mEditor.insertImage(body.getValue(), "图片加载失败");
//                    GlideApp.with(AddArticleActivity.this).load(mUserImage)
//                            .placeholder(R.drawable.camera_icon)
//                            .error(R.drawable.error_img)
//                            .fitCenter()
//                            .into(mImgIcon);

                    mProgressDialog.dismiss();
                    Toast.makeText(AddArticleActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();

                } else {
                    mProgressDialog.dismiss();
                    Toast.makeText(AddArticleActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result<String>> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(AddArticleActivity.this, "图片上传失败", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
