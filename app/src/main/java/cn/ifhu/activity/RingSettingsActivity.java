package cn.ifhu.activity;

import android.app.Service;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


    }