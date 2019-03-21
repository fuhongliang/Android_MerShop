package cn.ifhu.dialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import cn.ifhu.R;
import cn.ifhu.utils.DeviceUtil;

/**
 * @author tony
 * @date 2018/8/13
 */
public class DialogWheelFragment extends BaseDialogFragment{

    private OperateDialogConfirmListner confirmListner;
    private LinearLayout llContent;
    TimePicker timePickerBegin;
    TimePicker timePickerEnd;

    List<String> stringList = new ArrayList<String>();
    public static void showOperateDialog(FragmentManager fragmentManager, Bundle bundle, OperateDialogConfirmListner confirmListner){
        DialogWheelFragment dialogFragment = new DialogWheelFragment();
        dialogFragment.setArguments(bundle);
        dialogFragment.setDialogConfirmListner(confirmListner);
        dialogFragment.show(fragmentManager,"AlertDialogFragment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Bottom_top_dialog_style);
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels- DeviceUtil.dip2px(0), ViewGroup.LayoutParams.WRAP_CONTENT);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_wheel_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        TextView title = view.findViewById(R.id.tv_title);
        Bundle bundle = getArguments();
        title.setText(bundle.getString("title"));
        stringList = bundle.getStringArrayList("stringList");
        llContent = view.findViewById(R.id.ll_content);
        timePickerBegin = view.findViewById(R.id.time_picker);
        timePickerEnd = view.findViewById(R.id.time_picker2);
        addOptionalView();
//        changeTimePickerColor(view);
    }

    public void addOptionalView() {
        timePickerBegin.setIs24HourView(true);
        //设置为24小时显示格式
        timePickerBegin.setCurrentHour(0);
        //当前小时
        timePickerBegin.setCurrentMinute(30);
        //当前分钟

        timePickerEnd.setIs24HourView(true);
        timePickerEnd.setCurrentHour(0);
        timePickerEnd.setCurrentMinute(30);

        timePickerBegin.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        timePickerEnd.setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

        timePickerBegin.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                long delayMills = (hourOfDay * 60 + minute) * 60 * 1000;
            }
        });
    }

    public void changeTimePickerColor(View view) {
        Resources systemResources = Resources.getSystem();
        int hourNumberPickerId = systemResources.getIdentifier("hour", "id", "android");
        int minuteNumberPickerId = systemResources.getIdentifier("minute", "id", "android");

        NumberPicker hourNumberPicker = view.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker = view.findViewById(minuteNumberPickerId);

        setNumberPickerDivider(hourNumberPicker, Color.YELLOW);
        setNumberPickerDivider(minuteNumberPicker, Color.GREEN);

        setNumberpickerTextColour(hourNumberPicker, Color.RED);
        setNumberpickerTextColour(minuteNumberPicker, Color.BLUE);

//        setPickerSize(hourNumberPicker, 30, this);

    }

    //指定分割线颜色
    private void setNumberPickerDivider(NumberPicker numberPicker, int color) {

        try {
            Field dividerFields = NumberPicker.class.getDeclaredField("mSelectionDivider");

            dividerFields.setAccessible(true);

            dividerFields.set(numberPicker, new ColorDrawable(color));

        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
            Log.w("setNumberPickerTxtClr", e);
        }
    }



    //另一种指定文字颜色的方法
    private void setNumberpickerTextColour(NumberPicker number_picker, int color) {
        final int count = number_picker.getChildCount();

        for (int i = 0; i < count; i++) {
            View child = number_picker.getChildAt(i);

            try {
                Field wheelpaint_field = number_picker.getClass().getDeclaredField("mSelectorWheelPaint");
                wheelpaint_field.setAccessible(true);
                ((Paint) wheelpaint_field.get(number_picker)).setColor(color);
                ((EditText) child).setTextColor(color);
                number_picker.invalidate();
            } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                Log.i("setNumberPickerTxtClr", "set_numberpicker_text_colour: "+e);
            }
        }
    }


    //指定NumberPicker大小                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                改的NumberPicker和NumberPicker的宽度值
//    private static void setPickerSize(NumberPicker np, int widthDpValue, Context context) {
//        int widthPxValue = ToolUtils.dp2px(context, widthDpValue);
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthPxValue, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.setMargins(0, 0, 0, 0);
//        np.setLayoutParams(params);
//    }


    public void setDialogConfirmListner(OperateDialogConfirmListner alertDialogConfirmListner) {
        this.confirmListner = alertDialogConfirmListner;
    }

    public interface OperateDialogConfirmListner {
        void onClickTextView(String string);
    }
}