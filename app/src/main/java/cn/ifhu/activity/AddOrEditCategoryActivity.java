package cn.ifhu.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.ProductManageBean;
import cn.ifhu.bean.UserServiceBean;
import cn.ifhu.net.OperationService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.utils.ProductLogic;
import cn.ifhu.utils.StringUtils;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author fuhongliang
 */
public class AddOrEditCategoryActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_sort)
    EditText etSort;
    @BindView(R.id.btn_save)
    Button btnSave;
    int mClassId = 0;
    String mClassName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_management);
        ButterKnife.bind(this);
        tvTitle.setText("分类编辑");
        mClassId = getIntent().getIntExtra("ClassId",0);
        mClassName = getIntent().getStringExtra("ClassName");
        etSort.setText(mClassName);
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (checkContent()){
            setLoadingMessageIndicator(true);
            if (mClassId == 0){
                RetrofitAPIManager.create(OperationService.class).addGoodsClass(UserLogic.getUser().getStore_id(),etSort.getText().toString().trim())
                        .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<ProductManageBean.ClassListBean>>(true) {
                    @Override
                    protected void onApiComplete() {
                        setLoadingMessageIndicator(false);
                    }

                    @Override
                    protected void onSuccees(BaseEntity<List<ProductManageBean.ClassListBean>> t) throws Exception {
                        ToastHelper.makeText(t.getMessage()+"", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                        etSort.setText("");
                        if (t.getData().size()>0){
                            ProductLogic.saveClass(t.getData());
                        }
                    }
                });
            }else {
                RetrofitAPIManager.create(OperationService.class).updateGoodsClass(UserLogic.getUser().getStore_id(),mClassId,etSort.getText().toString().trim())
                        .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<ProductManageBean.ClassListBean>>(true) {
                    @Override
                    protected void onApiComplete() {
                        setLoadingMessageIndicator(false);
                    }

                    @Override
                    protected void onSuccees(BaseEntity<List<ProductManageBean.ClassListBean>> t) throws Exception {
                        ToastHelper.makeText(t.getMessage()+"", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                        if (t.getData().size()>0){
                            ProductLogic.saveClass(t.getData());
                        }
                        finish();
                    }
                });
            }

        }
    }

    public boolean checkContent() {
        if (StringUtils.isEmpty(etSort.getText().toString().trim())) {
            ToastHelper.makeText("标题不能为空", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
            return false;
        }
        return true;
    }
}
