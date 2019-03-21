package cn.ifhu.activity;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import cn.ifhu.R;
import cn.ifhu.base.BaseActivity;


    public class RingSettingsActivity extends BaseActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ring_settings);
        }


        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()){
                case R.id.swh_status:
                    if(compoundButton.isChecked()) Toast.makeText(this,"开关:ON",Toast.LENGTH_SHORT).show();
                    else Toast.makeText(this,"开关:OFF",Toast.LENGTH_SHORT).show();
                    break;
            }

        }

//        Vibrator vibrator;
//        //通过点击事件触发震动
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                //开始震动
//                case R.id.start:
//                    vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
//                    long [] pattern = {100,400,100,400}; // 停止 开启 停止 开启
//                    //第二个参数表示使用pattern第几个参数作为震动时间重复震动，如果是-1就震动一次
//                    vibrator.vibrate(pattern,2);
//                    break;
//                //取消震动
//                case R.id.stop:
//                    vibrator.cancel();
//                    break;
//
//                default:
//                    break;
//            }
//        }



    }