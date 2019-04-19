package cn.ifhu.mershop.activity.operation;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ifhu.mershop.R;
import cn.ifhu.mershop.adapter.FullCutRuleAdapter;
import cn.ifhu.mershop.base.BaseActivity;
import cn.ifhu.mershop.base.BaseObserver;
import cn.ifhu.mershop.bean.BaseEntity;
import cn.ifhu.mershop.bean.DiscountGoods;
import cn.ifhu.mershop.bean.DiscountPostBean;
import cn.ifhu.mershop.bean.FullCutBean;
import cn.ifhu.mershop.bean.FullCutPostBean;
import cn.ifhu.mershop.bean.ProductManageBean;
import cn.ifhu.mershop.net.OperationService;
import cn.ifhu.mershop.net.RetrofitAPIManager;
import cn.ifhu.mershop.net.SchedulerUtils;
import cn.ifhu.mershop.utils.DateUtil;
import cn.ifhu.mershop.utils.ProductLogic;
import cn.ifhu.mershop.utils.StringUtils;
import cn.ifhu.mershop.utils.ToastHelper;
import cn.ifhu.mershop.utils.UserLogic;
import cn.ifhu.mershop.view.ExpandListView;
import io.reactivex.Observer;
import jsc.kit.wheel.dialog.DateTimeWheelDialog;

/**
 * @author fuhongliang
 */
public class AddFullReductionActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.et_input_full)
    EditText etInputFull;
    @BindView(R.id.et_input_cut)
    EditText etInputCut;
    @BindView(R.id.ll_add)
    LinearLayout llAdd;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.listView)
    ExpandListView listView;
    @BindView(R.id.et_name)
    EditText etName;
    private List<FullCutPostBean.RuleBean> rule;
    FullCutRuleAdapter fullCutRuleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_full_reduction);
        ButterKnife.bind(this);
        rule = new ArrayList<>();
        fullCutRuleAdapter = new FullCutRuleAdapter(rule, this);
        listView.setAdapter(fullCutRuleAdapter);
        fullCutRuleAdapter.setOnClickItem(new FullCutRuleAdapter.OnClickItem() {
            @Override
            public void deleteItem(int position) {
                rule.remove(position);
                fullCutRuleAdapter.setBeanList(rule);
            }
        });
        tvTitle.setText("添加活动");
    }

    @OnClick(R.id.iv_back)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.tv_start_time)
    public void onTvStartTimeClicked() {
        createDialog(0).show();
    }

    @OnClick(R.id.tv_end_time)
    public void onTvEndTimeClicked() {
        createDialog(1).show();
    }

    private DateTimeWheelDialog createDialog(int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2010);
        calendar.set(Calendar.MONTH, 4);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        Date startDate = calendar.getTime();
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2035);
        Date endDate = calendar.getTime();

        DateTimeWheelDialog dialog = new DateTimeWheelDialog(this);
        dialog.show();
        dialog.setTitle("选择时间");
        int config = DateTimeWheelDialog.SHOW_YEAR_MONTH_DAY_HOUR_MINUTE;
        dialog.configShowUI(config);
        dialog.setCancelButton("取消", null);
        dialog.setOKButton("确定", (v, selectedDate) -> {
            if (type == 0) {
                tvStartTime.setText(DateUtil.getDateToString(selectedDate));
            } else {
                tvEndTime.setText(DateUtil.getDateToString(selectedDate));
            }
            return false;
        });
        dialog.setDateArea(startDate, endDate, true);
        dialog.updateSelectedDate(new Date());
        return dialog;
    }


    @OnClick(R.id.ll_add)
    public void onLlAddClicked() {
        String full = etInputFull.getText().toString().trim();
        String cut = etInputCut.getText().toString().trim();
        if (StringUtils.isEmpty(full)) {
            ToastHelper.makeText("请输入满多少元", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return;
        }
        if (StringUtils.isEmpty(cut)) {
            ToastHelper.makeText("请输入减多少元", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return;
        }
        if (rule.size() > 3) {
            ToastHelper.makeText("最多只能添加4条规则", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
        } else {
            FullCutPostBean.RuleBean ruleBean = new FullCutPostBean.RuleBean();
            ruleBean.setPrice(Integer.parseInt(full));
            ruleBean.setDiscount(Integer.parseInt(cut));
            rule.add(ruleBean);
            fullCutRuleAdapter.setBeanList(rule);
            etInputFull.setText("");
            etInputCut.setText("");
        }

    }

    @OnClick(R.id.btn_save)
    public void onBtnSaveClicked() {
        if (checkContent()){
            setLoadingMessageIndicator(true);
            FullCutPostBean fullCutPostBean = new FullCutPostBean();
            fullCutPostBean.setMansong_name(etName.getText().toString().trim());
            fullCutPostBean.setStart_time(tvStartTime.getText().toString());
            fullCutPostBean.setEnd_time(tvEndTime.getText().toString());
            fullCutPostBean.setRules(rule);
            fullCutPostBean.setRemark(etRemark.getText().toString().trim());
            fullCutPostBean.setStore_id(UserLogic.getUser().getStore_id()+"");
            RetrofitAPIManager.create(OperationService.class).mamsongEditOrAdd(fullCutPostBean)
                    .compose(SchedulerUtils.ioMainScheduler()).subscribe(new BaseObserver<Observer>(true) {
                @Override
                protected void onApiComplete() {
                    setLoadingMessageIndicator(false);
                }

                @Override
                protected void onSuccees(BaseEntity<Observer> t) throws Exception {
                    ToastHelper.makeText(t.getMessage(), Toast.LENGTH_SHORT,ToastHelper.NORMALTOAST).show();
                    finish();
                }
            });
        }
    }

    public boolean checkContent() {
        if (StringUtils.isEmpty(etName.getText().toString().trim())) {
            ToastHelper.makeText("请输入活动名称", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }

        if (StringUtils.isEmpty(tvStartTime.getText().toString().trim())) {
            ToastHelper.makeText("请选择开始时间", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }

        if (StringUtils.isEmpty(tvEndTime.getText().toString().trim())) {
            ToastHelper.makeText("请选择结束时间", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }


        if (rule == null || rule.size() < 1) {
            ToastHelper.makeText("请添加满立减规则", Toast.LENGTH_SHORT, ToastHelper.NORMALTOAST).show();
            return false;
        }
        return true;
    }
}