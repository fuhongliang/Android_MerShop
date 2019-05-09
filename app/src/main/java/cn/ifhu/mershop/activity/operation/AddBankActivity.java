package cn.ifhu.mershop.activity.operation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.CategoryWheelItem;
import cn.ifhu.mershop.bean.ProductManageBean;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.ProductLogic;
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;
import jsc.kit.wheel.dialog.ColumnWheelDialog;

/**
 * @author fuhongliang
 */
public class AddBankActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_bank_number)
    EditText etBankNumber;
    @BindView(R.id.et_bank_address)
    EditText etBankAddress;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_save)
    TextView tvSave;
    ColumnWheelDialog dialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
        tvTitle.setText("添加银行卡");
    }


    public boolean checkEmpty() {                                       //public为公开的 void是没有返回数据 方法名称(){}
        if (StringUtils.isEmpty(etName.getText().toString())) {         //判断输入框是否为空、StingUtils是封装好了的.isEmpty(控件的命名.get类型().toString装换为字符串)
            ToastHelper.makeText("请输入姓名").show();                    //为空的话提示用户ToastHelper.makeText("提示").显示
            return false;                                                //返回值
        }

        if (StringUtils.isEmpty(etBankNumber.getText().toString())) {
            ToastHelper.makeText("请输入卡号").show();
            return false;
        }
        if (StringUtils.isEmpty(etBankAddress.getText().toString())) {
            ToastHelper.makeText("请输入开户银行").show();
            return false;
        }

        return true;
    }

    public void addBankAccount() {                                        //调用方法
        if (checkEmpty()) {                                               //如果方法
            setLoadingMessageIndicator(true);           //加载(真)
            //                         接口字段的文件.class        接口名称(自定义)       从用户登录时保存用户的店铺id。ID为int型需要转换为string型
            RetrofitAPIManager.create(OperationService.class).addBankAccount(UserLogic.getUser().getStore_id() + "",      //上传数据到后台
                    etName.getText().toString(),                            //控件命名.get文本框().to类型，
                    etBankNumber.getText().toString(),
                    etBankAddress.getText().toString(),
                    tvBankName.getText().toString())
                    .compose(SchedulerUtils.ioMainScheduler())    //这个是固定的。可复制
                    .subscribe(new BaseObserver<Object>(true) {   //固定的、已封装好了<对象>(布尔值为true)
                        @Override
                        protected void onApiComplete() {
                            setLoadingMessageIndicator(false);
                        }

                        @Override //从后台获取数据的方法(成功之后)
                        protected void onSuccees(BaseEntity t) throws Exception {        //protected void  自动生成(command N)
                            ToastHelper.makeText(t.getMessage()).show();                 //提示.makeText(t.getMessage()封装好了).show();
                            startActivity(new Intent(AddBankActivity.this, BindingSuccessActivity.class));
                        }
                    });
        }
    }


    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_save)
    public void onTvSaveClicked() {
        addBankAccount();
    }

    @OnClick(R.id.tv_bank_name)
    public void onTvBankNameClicked() {
        if (dialog == null) {
            dialog = createDialog();
        } else {
            dialog.show();
        }
    }

    private ColumnWheelDialog createDialog() {
        ColumnWheelDialog<CategoryWheelItem, CategoryWheelItem, CategoryWheelItem, CategoryWheelItem, CategoryWheelItem> dialog = new ColumnWheelDialog<>(this);
        dialog.show();
        dialog.setTitle("");
        dialog.setCancelButton("取消", null);
        dialog.setOKButton("确定", (v, item0, item1, item2, item3, item4) -> {
            String result = "";
            if (item0 != null) {
                result += item0.getShowText();
            }
            tvBankName.setText(result);
            return false;
        });
        dialog.setItems(initItems(), null, null, null, null);
        dialog.setSelected(0, 0, 0, 0, 0);
        return dialog;
    }

    private CategoryWheelItem[] initItems() {
        final CategoryWheelItem[] items;
        try {
            List<String> stringList = ProductLogic.getBankList();
            items = new CategoryWheelItem[stringList.size()];
            for (int i = 0; i < stringList.size(); i++) {
                items[i] = new CategoryWheelItem(stringList.get(i), 0);
            }
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CategoryWheelItem[1];
    }
}
