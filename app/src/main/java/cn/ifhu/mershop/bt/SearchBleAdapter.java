package cn.ifhu.mershop.bt;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import cn.ifhu.mershop.R;
import cn.ifhu.mershop.base.AppInfo;
import cn.ifhu.mershop.print.PrintUtil;


/**
 * Created by yefeng on 6/2/15.
 * github:yefengfreedom
 */
public class SearchBleAdapter extends BaseAdapter {

    private ArrayList<BluetoothDevice> mDevices;
    private LayoutInflater mInflater;
    private String mConnectedDeviceAddress;

    public SearchBleAdapter(Context mContext, ArrayList<BluetoothDevice> mDevices) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mDevices = null == mDevices ? new ArrayList<BluetoothDevice>() : mDevices;
        mConnectedDeviceAddress = PrintUtil.getDefaultBluethoothDeviceAddress(mContext);
    }

    public ArrayList<BluetoothDevice> getDevices() {
        return mDevices;
    }


    public void setDevices(ArrayList<BluetoothDevice> mDevices) {
        if (null == mDevices) {
            mDevices = new ArrayList<BluetoothDevice>();
        }
        this.mDevices = mDevices;
        this.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        if (null != this.mDevices) {
            this.mDevices = sortByBond(this.mDevices);
        }
        super.notifyDataSetChanged();
    }

    private ArrayList<BluetoothDevice> sortByBond(ArrayList<BluetoothDevice> mDevices) {
        if (null == mDevices) {
            return null;
        }
        if (mDevices.size() < 2) {
            return mDevices;
        }
        ArrayList<BluetoothDevice> bondDevices = new ArrayList<BluetoothDevice>();
        ArrayList<BluetoothDevice> unBondDevices = new ArrayList<BluetoothDevice>();
        int size = mDevices.size();
        for (int i = 0; i < size; i++) {
            BluetoothDevice bluetoothDevice = mDevices.get(i);
            if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                bondDevices.add(bluetoothDevice);
            } else {
                unBondDevices.add(bluetoothDevice);
            }
        }
        mDevices.clear();
        mDevices.addAll(bondDevices);
        mDevices.addAll(unBondDevices);
        bondDevices.clear();
        bondDevices = null;
        unBondDevices.clear();
        unBondDevices = null;
        return mDevices;
    }

    public void setConnectedDeviceAddress(String macAddress) {
        this.mConnectedDeviceAddress = macAddress;
    }

    public void addDevices(ArrayList<BluetoothDevice> mDevices) {
        if (null == mDevices) {
            return;
        }
        for (BluetoothDevice bluetoothDevice : mDevices) {
            addDevices(bluetoothDevice);
        }
    }

    public void addDevices(BluetoothDevice mDevice) {
        if (null == mDevice) {
            return;
        }
        if (!this.mDevices.contains(mDevice)) {
            this.mDevices.add(mDevice);
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public BluetoothDevice getItem(int position) {
        return mDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.item_connection, parent, false);
            holder = new ViewHolder();
            if (null != convertView) {
                convertView.setTag(holder);
            }
        }
        holder.name = convertView.findViewById(R.id.tv_name);
        holder.state = convertView.findViewById(R.id.tv_state);
        holder.line = convertView.findViewById(R.id.iv_line);

        BluetoothDevice bluetoothDevice = mDevices.get(position);
        String dName = bluetoothDevice.getName() == null ? "未知设备" : bluetoothDevice.getName();
        if (TextUtils.isEmpty(dName)) {
            dName = "未知设备";
        }
        holder.name.setText(dName);
        String dAddress = bluetoothDevice.getAddress() == null ? "未知地址" : bluetoothDevice.getAddress();

        if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
            if (dAddress.equals(mConnectedDeviceAddress)) {
                holder.state.setText("已连接");
            } else {
                holder.state.setText("已配对");
            }
        } else {
            holder.state.setText("未配对");
        }
        if (position == getCount() - 1) {
            holder.line.setVisibility(View.GONE);
        } else {
            holder.line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView state;
        ImageView line;
    }
}
