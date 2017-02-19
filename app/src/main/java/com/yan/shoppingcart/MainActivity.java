package com.yan.shoppingcart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yan.shoppingcart.Bean.Bean;
import com.yan.shoppingcart.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 实现购物车功能
 */
public class MainActivity extends AppCompatActivity implements ListAdapter.CheckInterface, ListAdapter.ModifyCountInterface {

    private ListView lv_fragment_shopcar_cart;
    private List<Bean> list;
    private ListAdapter listAdapter;
    private TextView sumPrice;
    private Button shopcar_pushsum;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private CheckBox check_shopcar;
    private CheckBox total_check_delete;
    private TextView shopcar_reset;
    private RelativeLayout relative_delteView;
    private RelativeLayout relative_jiesuan;
    private Button btn_fragment_shopcar_delet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        getData();
        setAdapter();
    }

    //获取id
    private void intiView() {
        lv_fragment_shopcar_cart = (ListView) findViewById(R.id.lv_fragment_shopcar_cart);
        sumPrice = (TextView) findViewById(R.id.tv_fragment_shopcar_total_sum);
        shopcar_pushsum = (Button) findViewById(R.id.btn_fragment_shopcar_pushsum);
        check_shopcar = (CheckBox) findViewById(R.id.total_check_fragment_shopcar);
        shopcar_reset = (TextView) findViewById(R.id.tv_fragment_shopcar_reset);

        relative_delteView = (RelativeLayout) findViewById(R.id.relative_delteView);
        relative_jiesuan = (RelativeLayout) findViewById(R.id.relative_jiesuan);
        total_check_delete = (CheckBox) findViewById(R.id.total_check_delete);
        btn_fragment_shopcar_delet = (Button) findViewById(R.id.btn_fragment_shopcar_delet);
        compile();
        check_shopcar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setCheck_(isChecked);
                    }
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setCheck_(isChecked);
                    }
                }
                listAdapter.notifyDataSetChanged();
                calculate();
            }
        });
    }

    /**
     * 编辑按钮监听
     */
    int flag = 0;

    private void compile() {
        shopcar_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag++;
                if (flag == 1) {
                    //显示视图
                    relative_delteView.setVisibility(View.VISIBLE);
                    relative_jiesuan.setVisibility(View.GONE);
                    //改变字体
                    shopcar_reset.setText("完成");
                    deleteCheck();
                    //判断进行删除
                    btn_fragment_shopcar_delet.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            delete();
                        }
                    });
                    refresh();
                } else {
                    shopcar_reset.setText("编辑");
                    relative_delteView.setVisibility(View.GONE);
                    relative_jiesuan.setVisibility(View.VISIBLE);
                    flag = 0;
                    refresh();
                }
            }
        });
    }
    //刷新操作  适配
    private void refresh(){
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCheck_(false);
        }
        listAdapter.notifyDataSetChanged();
    }
    //删除复选框操作
    private void deleteCheck() {
        total_check_delete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setCheck_(isChecked);
                    }
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setCheck_(isChecked);
                    }
                }
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    //删除操作
    private void delete() {
        for (int i = 0; i < list.size(); i++) {
            boolean check_ = list.get(i).isCheck_();
            if (check_) {
                //进行删除
                if (total_check_delete.isChecked()) {
                    list.clear();
                } else {
                    list.remove(i);
                }
            }
        }
        listAdapter.notifyDataSetChanged();
        calculate();
    }

    //数据
    private void getData() {
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new Bean(10, "第" + i + "号", 1));
        }
    }

    //进行是适配的方法
    private void setAdapter() {
        listAdapter = new ListAdapter(this, list);
        lv_fragment_shopcar_cart.setAdapter(listAdapter);
        listAdapter.setOnMyCheckInterface(this);
        listAdapter.setModifyCountInterface(this);
    }

    /**
     * 回调方法
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {
        Bean bean = list.get(position);
        calculate();
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < list.size(); i++) {
            Bean bean = list.get(i);
            if (bean.isCheck_()) {
                int count = bean.getCount();
                totalCount += count;
                totalPrice += bean.getPrice() * bean.getCount();
            }
        }
        sumPrice.setText("￥" + totalPrice);
        shopcar_pushsum.setText("去支付(" + totalCount + ")");
    }

    /**
     * 增加的回调
     */
    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        //拿到对象
        Bean bean = list.get(position);
        int count = bean.getCount();
        count++;
        bean.setCount(count);
        //因为传过来的是View 需要进行强转
        ((TextView) showCountView).setText(count + "");
        listAdapter.notifyDataSetChanged();
        calculate();
    }
    /**
     * 减少的回调
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        //拿到对象
        Bean bean = list.get(position);
        int count = bean.getCount();
        count--;
        if (count == 1) {
            return;
        }
        bean.setCount(count);
        //因为传过来的是View 需要进行强转
        ((TextView) showCountView).setText(count + "");
        listAdapter.notifyDataSetChanged();
        calculate();
    }
}
