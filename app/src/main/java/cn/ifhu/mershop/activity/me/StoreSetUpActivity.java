package cn.ifhu.mershop.activity.me;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baba.GlideImageView;
import com.bumptech.glide.Glide;
import com.yalantis.ucrop.UCrop;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.activity.operation.AddProductActivity;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.FileModel;
import cn.ifhu.mershop.bean.UserServiceBean;
import cn.ifhu.mershop.dialog.DialogWheelFragment;
import cn.ifhu.mershop.net.MeService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.net.UploadFileService;
import cn.ifhu.mershop.utils.Constants;
import cn.ifhu.mershop.utils.ImageChooseUtil;
import cn.ifhu.mershop.utils.IrReference;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author fuhongliang
 */
public class StoreSetUpActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_store_state)
    LinearLayout llStoreState;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.rl_notice)
    RelativeLayout rlNotice;
    @BindView(R.id.tv_store_phone)
    TextView tvStorePhone;
    @BindView(R.id.ll_store_phone)
    LinearLayout llStorePhone;
    @BindView(R.id.tv_store_add)
    TextView tvStoreAdd;
    @BindView(R.id.tv_store_time)
    TextView tvStoreTime;
    @BindView(R.id.rl_store_license)
    RelativeLayout rlStoreLicense;
    @BindView(R.id.tv_store_state)
    TextView tvStoreState;
    @BindView(R.id.tv_store_notice)
    TextView tvStoreNotice;
    UserServiceBean.LoginResponse loginResponse;
    @BindView(R.id.iv_logo)
    GlideImageView ivLogo;
    @BindView(R.id.swh_auto_access)
    Switch swhAutoAccess;
    @BindView(R.id.swh_auto_print)
    Switch swhAutoPrint;
    @BindView(R.id.ll_store_time)
    LinearLayout llStoreTime;
    String cardPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_setup);
        ButterKnife.bind(this);
        tvTitle.setText("门店设置");
        initData();
    }


    public void initData() {
        loginResponse = UserLogic.getUser();
        if (loginResponse != null) {
            tvStoreState.setText(loginResponse.getStore_state() == 0 ? "已停止营业" : "正常营业中");
            tvStoreNotice.setText(loginResponse.getStore_description());
            ivLogo.loadCircle(Constants.IMGPATH+loginResponse.getStore_avatar());
            tvStorePhone.setText(loginResponse.getStore_phone());
            tvStoreAdd.setText(loginResponse.getStore_address());
            tvStoreTime.setText(loginResponse.getWork_start_time() + "~" + loginResponse.getWork_end_time());
        }
        boolean autoAccess = IrReference.getInstance().getBoolean(Constants.AUTOACCESS, false);
        swhAutoAccess.setChecked(autoAccess);

        boolean autoPrint = IrReference.getInstance().getBoolean(Constants.AUTOPRINT, false);
        swhAutoPrint.setChecked(autoPrint);

        swhAutoAccess.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                IrReference.getInstance().setBoolean(Constants.AUTOACCESS, true);
            } else {
                IrReference.getInstance().setBoolean(Constants.AUTOACCESS, false);
            }
        });

        swhAutoPrint.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                IrReference.getInstance().setBoolean(Constants.AUTOPRINT, true);
            } else {
                IrReference.getInstance().setBoolean(Constants.AUTOPRINT, false);
            }
        });
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.ll_store_state)
    public void onLlStoreStateClicked() {
        startActivity(new Intent(StoreSetUpActivity.this, StoreStateActivity.class));
    }

    @OnClick(R.id.rl_notice)
    public void onRlNoticeClicked() {
        startActivity(new Intent(StoreSetUpActivity.this, StoreNoticeActivity.class));
    }

    @OnClick(R.id.ll_store_phone)
    public void onLlStorePhoneClicked() {
        startActivity(new Intent(StoreSetUpActivity.this, ChangeStorePhoneActivity.class));
    }

    @OnClick(R.id.ll_store_time)
    public void onTvStoreTimeClicked() {
        if (loginResponse.getWork_start_time() != null && loginResponse.getWork_end_time() != null) {
            Bundle bundle = new Bundle();
            bundle.putString("startTime", loginResponse.getWork_start_time());
            bundle.putString("endTime", loginResponse.getWork_end_time());
            DialogWheelFragment.showOperateDialog(getSupportFragmentManager(), bundle, (beginTime, endTime) -> setStoreTime(beginTime, endTime));
        }
    }

    @OnClick(R.id.rl_store_license)
    public void onRlStoreLicenseClicked() {
        startActivity(new Intent(StoreSetUpActivity.this, BusinessQualificationActivity.class));
    }


    public void setStoreTime(String beginTime, String endTime) {
        tvStoreTime.setText(beginTime + "~" + endTime);
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(MeService.class).storeSetWorktime(UserLogic.getUser().getStore_id(), beginTime, endTime)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Object>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity t) throws Exception {
                ToastHelper.makeText(t.getMessage() + "", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
                UserServiceBean.LoginResponse loginResponse = UserLogic.getUser();
                loginResponse.setWork_start_time(beginTime);
                loginResponse.setWork_end_time(endTime);
                UserLogic.saveUser(loginResponse);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @OnClick(R.id.rl_logo)
    public void onRlLogoClicked() {
        showSelectPicPage();
    }

    public void showSelectPicPage() {
        ImageChooseUtil.startChooseImage(StoreSetUpActivity.this, ImageChooseUtil.REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case ImageChooseUtil.REQUEST_CODE:
                    List<Uri> stringList = Matisse.obtainResult(data);
                    startCrop(stringList.get(0));
                    break;
                case UCrop.REQUEST_CROP:
                    handleCropResult(data);
                    break;
                default:
                    break;
            }
        }
    }

    public void startCrop(@NonNull Uri uri) {
        String destinationFileName = System.currentTimeMillis() + ".jpg";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);
        uCrop.start(this);
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(90);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);
        return uCrop.withOptions(options);
    }

    private UCrop basisConfig(@NonNull UCrop uCrop) {
        uCrop = uCrop.useSourceImageAspectRatio();
        return uCrop;
    }

    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            cardPath = resultUri.getPath();
            ivLogo.load(cardPath);
            upLoadImage();
        } else {
            Toast.makeText(this, "剪切失败，请重新选择", Toast.LENGTH_SHORT).show();
        }
    }

    public void upLoadImage(){
        setLoadingMessageIndicator(true);
        File file = new File(cardPath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RetrofitAPIManager.createUpload(UploadFileService.class).imageUpload(body)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<String>(true) {
            @Override
            protected void onApiComplete() {
            }

            @Override
            protected void onSuccees(BaseEntity<String> t) throws Exception {
                updateLogo(t.getData());

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                setLoadingMessageIndicator(false);
            }
        });
    }

    public void updateLogo(String url){
        setLoadingMessageIndicator(true);
        RetrofitAPIManager.create(MeService.class).changeAvator(UserLogic.getUser().getStore_id()+"",url)
                .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<UserServiceBean.LoginResponse>(true) {
            @Override
            protected void onApiComplete() {
                setLoadingMessageIndicator(false);
            }

            @Override
            protected void onSuccees(BaseEntity<UserServiceBean.LoginResponse> t) throws Exception {
                UserLogic.saveUser(t.getData());
                loginResponse = UserLogic.getUser();
                ivLogo.loadCircle(Constants.IMGPATH+loginResponse.getStore_avatar());
            }

        });

    }
}
