package cn.ifhu.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;
import cn.ifhu.base.BaseObserver;
import cn.ifhu.bean.BaseEntity;
import cn.ifhu.bean.UserServiceBean;
import cn.ifhu.net.OperationService;
import cn.ifhu.net.RetrofitAPIManager;
import cn.ifhu.net.SchedulerUtils;
import cn.ifhu.utils.StringUtils;
import cn.ifhu.utils.ToastHelper;
import cn.ifhu.utils.UserLogic;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author fuhongliang
 */
public class CategoryManagementActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_sort)
    EditText etSort;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_management);
        ButterKnife.bind(this);
        tvTitle.setText("分类编辑");


    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (checkContent()){
            setLoadingMessageIndicator(true);
            RetrofitAPIManager.create(OperationService.class).addGoodsClass(UserLogic.getUser().getStore_id(),etSort.getText().toString().trim())
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<List<Observer>>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity t) throws Exception {
                    ToastHelper.makeText(t.getMessage()+"", Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                    etSort.setText("");
                }

                @Override
                public void onNext(BaseEntity<List<Observer>> tLinkBaseEntity) {
                    super.onNext(tLinkBaseEntity);
                }
            });
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
