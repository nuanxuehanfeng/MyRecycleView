package com.bawei.wangxueshi.myrecycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/5/26.
 */

public class MainAdapter  extends RecyclerView.Adapter<MainAdapter.CViewHoler>{
    Context context;
    List<String> list;

    public MainAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    //创建viewholder
    //viewType  类型
    @Override
    public CViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.main_item,null);
        CViewHoler cViewHoler = new CViewHoler(view);
        return cViewHoler;
    }
//viewholder 的绑定
    @Override
    public void onBindViewHolder(final CViewHoler holder, final int position) {
        //瀑布流 设置随机高度
        final ViewGroup.LayoutParams layoutParams = holder.textView.getLayoutParams();
        layoutParams.height= new Random().nextInt(100);
        holder.textView.setText(list.get(position));
        //系统的点击事件
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当点击时：调用接口的方法
                  listener.onItemClickListener(position,v);
            }
        });
        //系统的长按事件
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //当长按时：调用接口的方法
                listener.onItemLongClickListener(position,v);
                //注意 要将返回值改为true
                return true;
            }
        });

    }
//返回 数量
    @Override
    public int getItemCount() {
        return list.size();
    }
//创建 viewholder  必须有 并且 继承 哦
    class CViewHoler extends RecyclerView.ViewHolder{
        TextView textView;
        //itemView 适配器的布局
        public CViewHoler(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.text);
        }
    }
    //定义一个接口
    interface OnItemClickListener {

        void onItemClickListener(int position,View view);
        void onItemLongClickListener(int position,View view);
    }
    //属性
    public OnItemClickListener listener;
    //调用这个方法时 实现listener 接口
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


}
