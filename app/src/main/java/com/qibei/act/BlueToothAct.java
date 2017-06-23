package com.qibei.act;

import android.bluetooth.BluetoothAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.qibei.R;
import com.qibei.databinding.ActivityBlueToothBinding;

public class BlueToothAct extends AppCompatActivity {

    private ActivityBlueToothBinding binding;
    private BluetoothAdapter adapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = BluetoothAdapter.getDefaultAdapter();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_blue_tooth);
        binding.setPresenter(new Presenter());

    }

    public class Presenter {
        public void onOpenBT(View view) {
            if (adapter == null) {
                Toast.makeText(BlueToothAct.this, "当前设备不支持蓝牙功能", Toast.LENGTH_SHORT).show();
                return;
            }
            if (adapter.isEnabled()) {//已经打开蓝牙
//                adapter.disable();
                String name = adapter.getName();
                String address = adapter.getAddress();
                shToast("蓝牙名臣：" + name+"address:"+address);
            } else {
                boolean enable = adapter.enable();//打开蓝牙
                shToast("蓝牙状态：" + enable);
            }
        }
    }

    public void shToast(String value) {
        if (mToast == null) {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        mToast.setText(value);
        mToast.show();

    }
}
