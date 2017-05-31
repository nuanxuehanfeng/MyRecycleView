package com.bawei.wangxueshi.myrecycleview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.aspsine.irecyclerview.IRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private IRecyclerView recyclerView;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // startActivity(new Intent(this,MultipleActivity.class));
//        app:fillColor="#ffffffff"
//        app:pageColor="#9f888888"
//        app:strokeWidth="0dp"

        recyclerView = (IRecyclerView) findViewById(R.id.iRecyclerView);
        //加载数据
        initData();
        //线性布局管理器
//               LinearLayoutManager.VERTICAL 表示水平垂直滑动
//                false true     layouts from end to start. else
     //LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
     //表格布局管理器 4  为几列
      //  GridLayoutManager gridLayoutManager=new GridLayoutManager(this,4);
     // 瀑布流  4为几列，第二个为方向
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);

        //设置布局管理
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        MainAdapter mainAdapter = new MainAdapter(this, list);
        recyclerView.setAdapter(mainAdapter);
        //设置分割线
        //new HorizontalDividerItemDecoration.Builder(this).build()  jar包中默认的分割线
        recyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.RED)
                       // .sizeResId(R.dimen.divider)  分割线的高度
                     //   .marginResId(R.dimen.leftmargin, R.dimen.rightmargin) //左右的间距
                        .build());

         //每个条目添加动画   系统默认动画DefaultItemAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //调用 MainAdapter 中的方法，实现接口
        mainAdapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, View view) {
                Toast.makeText(MainActivity.this,"dianjia",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClickListener(int position, View view) {
                Toast.makeText(MainActivity.this,"long",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void initData(){
        list = new ArrayList();
        for (int i=0;i<30;i++){
            list.add(i+"");
        }
    }





}
