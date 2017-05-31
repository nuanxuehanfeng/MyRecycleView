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

public class MultipleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<String> list;
    //定义三种type类型
    private int header=0;
    private int footer=2;
    private int content=1;

    public MultipleAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }
    //创建viewholder
    //viewType  类型
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType==0){
            View view=View.inflate(context,R.layout.header,null);
            HViewHoder hViewHoder = new HViewHoder(view);
            return  hViewHoder;

        }
        else if(viewType==2){
            View view=View.inflate(context,R.layout.footer,null);
            FViewHoder fViewHoder = new FViewHoder(view);
            return  fViewHoder;
        }
        else{
            View view=View.inflate(context,R.layout.main_item,null);
            CViewHolder cViewHoder = new CViewHolder(view);
            return  cViewHoder;
        }



    }
    //viewholder 的绑定
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof  HViewHoder){
            HViewHoder hViewHoder = (HViewHoder) holder;
            hViewHoder.textView.setText("我是header");
        }
        if(holder instanceof  FViewHoder){
            FViewHoder fViewHoder = (FViewHoder) holder;
            fViewHoder.textView.setText("我是footer");
        }
        if(holder instanceof  CViewHolder){
            CViewHolder cViewHoder = (CViewHolder) holder;
            //因为 加了个头
            cViewHoder.textView.setText(list.get(position-1));
            cViewHoder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.MulonItemClickListener(position,v);
                }
            });
            cViewHoder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.MulonItemLongClickListener(position,v);
                    return true;
                }
            });
        }


    }
    //返回 数量
    @Override
    public int getItemCount() {
        return list.size()+2;//因为有头有尾
    }
//返回类型
    @Override
    public int getItemViewType(int position) {
        //第一行
        if(position==0){
            return  header;
        }
        //最后一行
        else if(position==list.size()+1){
            return  footer;
        }
        //中间行
        else{
            return  content;
        }

    }

    //头
    class HViewHoder extends RecyclerView.ViewHolder{

        TextView textView;
        public HViewHoder(View itemView) {
            super(itemView);
            //发现控件
            textView= (TextView) itemView.findViewById(R.id.header);
        }
    }
//脚
class   FViewHoder extends RecyclerView.ViewHolder{
    TextView textView;
    public FViewHoder(View itemView) {
        super(itemView);
        //发现控件
        textView= (TextView) itemView.findViewById(R.id.footer);
    }
}

//创建 viewholder  必须有 并且 继承 哦
    class CViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        //itemView 适配器的布局
        public CViewHolder(View itemView) {
            super(itemView);
            //发现控件
            textView= (TextView) itemView.findViewById(R.id.text);
        }
    }



    //定义一个接口
    interface MulOnItemClickListener {

        void MulonItemClickListener(int position, View view);
        void MulonItemLongClickListener(int position, View view);
    }


    //属性
    public MulOnItemClickListener listener;
    //调用这个方法时 实现listener 接口
    public void setOnItemClickListener(MulOnItemClickListener listener){
        this.listener = listener;
    }
  //增加数据的方法
    public void add(int  position){
        list.add(position,"insert"+position);

       // notifyDataSetChanged();;
    }
    //移除数据的方法
    public void remove(int  position){
        list.remove(position) ;
     //   notifyDataSetChanged();;
    }

}
