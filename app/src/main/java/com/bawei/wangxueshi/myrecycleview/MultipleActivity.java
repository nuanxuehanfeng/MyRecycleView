package com.bawei.wangxueshi.myrecycleview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.bawei.wangxueshi.myrecycleview.footer.LoadMoreFooterView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;


public class MultipleActivity extends Activity implements MultipleAdapter.MulOnItemClickListener {

    private IRecyclerView recyclerView;
    private ArrayList<String> list;
    private MultipleAdapter mainAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case  1:
                    //刷新停止
                    recyclerView.setRefreshing(false);
                    break;
                case 2:
                    //隐藏
                    // LoadMoreFooterView.Status.GONE;
                    // LoadMoreFooterView.Status.ERROR; //错误
                    // LoadMoreFooterView.Status.THE_END; 加载到最后
                    // LoadMoreFooterView.Status.LOADING;加载中
                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
                    break;
            }
        }
    };
    private LoadMoreFooterView loadMoreFooterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (IRecyclerView) findViewById(R.id.iRecyclerView);
        //加载数据
        initData();
        //线性布局管理器
//               LinearLayoutManager.VERTICAL 表示水平垂直滑动
//                false true     layouts from end to start. else
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
     //表格布局管理器 4  为几列
      //  GridLayoutManager gridLayoutManager=new GridLayoutManager(this,4);
     // 瀑布流  4为几列，第二个为方向
    //    StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL);

        //设置布局管理
        recyclerView.setLayoutManager(linearLayoutManager);
        mainAdapter = new MultipleAdapter(this,list);
       // recyclerView.setAdapter(mainAdapter);
        //注意 上拉和下啦 需要不同的方法来添加适配器
        recyclerView.setIAdapter(mainAdapter);
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
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            //newState 滚动的状态
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(AbsListView.OnScrollListener.SCROLL_STATE_IDLE==newState){
                    //当前滚动 停止
                    int findLastCompletelyVisibleItemPosition =  linearLayoutManager.findLastCompletelyVisibleItemPosition() ;
                    int findFirstCompletelyVisibleItemPosition =   linearLayoutManager.findFirstCompletelyVisibleItemPosition();

                    int findFirstVisibleItemPosition =  linearLayoutManager.findFirstVisibleItemPosition() ;
                    int findLastVisibleItemPosition =  linearLayoutManager.findLastVisibleItemPosition() ;


                    System.out.println("findLastCompletelyVisibleItemPosition = " + findLastCompletelyVisibleItemPosition);
                    System.out.println("findFirstCompletelyVisibleItemPosition = " + findFirstCompletelyVisibleItemPosition);
                    System.out.println("findFirstVisibleItemPosition = " + findFirstVisibleItemPosition);
                    System.out.println("findLastVisibleItemPosition = " + findLastVisibleItemPosition);

                }



            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
         mainAdapter.setOnItemClickListener(this);
//加载更多   注意  要更改 refresh_header和load_more 这两个布局的类引用 要就改成自己的
        //加载更多的动画
          loadMoreFooterView = (LoadMoreFooterView)recyclerView.getLoadMoreFooterView();
          recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //加载更多的的状态
                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
                handler.sendEmptyMessageDelayed(2,1000);
            }
        });
        //刷新
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
            //    recyclerView.setRefreshEnabled(true);  不用也行
            recyclerView.setRefreshing(true);
                handler.sendEmptyMessageAtTime(1,1000);
            }
        });
    }
    public void initData(){
        list = new ArrayList();
        for (int i=0;i<30;i++){
            list.add(i+"");
        }
    }
    @Override
    public void MulonItemClickListener(int position, View view) {
        mainAdapter.add(position-1);
        //指定为值插入
        mainAdapter.notifyItemInserted(position); // 在指定的位置 插入item， 局部刷新
        //刷新指定区域 start end
        //更新的位置 为positon 到  list.size();
        mainAdapter.notifyItemRangeChanged(position,list.size());
    }
    @Override
    public void MulonItemLongClickListener(int position, View view) {
      mainAdapter.remove(position-1);
     mainAdapter.notifyDataSetChanged();
    }
}
