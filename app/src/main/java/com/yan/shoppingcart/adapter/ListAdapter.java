package com.yan.shoppingcart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yan.shoppingcart.Bean.Bean;
import com.yan.shoppingcart.R;

import java.util.List;

/**
 * Created by dell on 2017/2/19.
 */

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<Bean> list;
    private CheckInterface mCheckInterface;
    private ModifyCountInterface modifyCountInterface;

    public void setOnMyCheckInterface(CheckInterface mCheckInterface) {
        this.mCheckInterface = mCheckInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public ListAdapter(Context context, List<Bean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item2, null);
            holder = new ViewHolder();
            holder.namTv = (TextView) convertView.findViewById(R.id.tv_intro_);
            holder.priceTv = (TextView) convertView.findViewById(R.id.tv_price_);
            holder.ch_CheckBox = (CheckBox) convertView.findViewById(R.id.check_box_);

            holder.iv_increase = (TextView) convertView.findViewById(R.id.tv_add_);
            holder.iv_decrease = (TextView) convertView.findViewById(R.id.tv_reduce_);
            holder.tv_count = (TextView) convertView.findViewById(R.id.tv_num_);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Bean bean = list.get(position);
        holder.namTv.setText(list.get(position).getName());
        holder.priceTv.setText("￥" + list.get(position).getPrice());

        holder.ch_CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.ch_CheckBox.isChecked()) {
                    list.get(position).setCheck_(true);
                } else {
                    list.get(position).setCheck_(false);
                }
                mCheckInterface.checkGroup(position, holder.ch_CheckBox.isChecked());
            }
        });
        //增加
        holder.iv_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease(position,holder.tv_count,holder.ch_CheckBox.isChecked());
            }
        });
        //减少
        holder.iv_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doDecrease(position,holder.tv_count,holder.ch_CheckBox.isChecked());
            }
        });

        holder.ch_CheckBox.setChecked(list.get(position).isCheck_());
        return convertView;
    }

    class ViewHolder {
        TextView namTv, iv_increase, iv_decrease, tv_count;
        TextView priceTv;
        CheckBox ch_CheckBox;
    }

    /**
     * 创建接口
     */
    public interface CheckInterface {
        public void checkGroup(int position, boolean isChecked);
    }

    public interface ModifyCountInterface {
        //增加
        public void doIncrease(int position, View showCountView, boolean isChecked);

        //减少
        public void doDecrease(int position, View showCountView, boolean isChecked);
    }

}
