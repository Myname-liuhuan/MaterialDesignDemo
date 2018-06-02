package com.example.materialdesigndemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    FruitAdapter fruitAdapter;
    private SwipeRefreshLayout swipeRefresh;

    //定义一个Fruit数组
    Fruit[] fruits={new Fruit("fruit1",R.drawable.fruit1),
            new Fruit("fruit2",R.drawable.fruit2),
            new Fruit("fruit3",R.drawable.fruit3),
            new Fruit("fruit4",R.drawable.fruit4),
            new Fruit("fruit5",R.drawable.fruit5),
            new Fruit("fruit6",R.drawable.fruit6)};

    List<Fruit> fruitList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar01=findViewById(R.id.toolbar01);
        setSupportActionBar(toolbar01);

        ActionBar actionBar=getSupportActionBar();//得到当前使用的actionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//将HomeAsUp按钮显示出来
            actionBar.setHomeAsUpIndicator(R.drawable.menu);//为HomeAsUp按钮设置图标
        }

        swipeRefresh=findViewById(R.id.swipeRefreshLayout);

        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout01);//左边可滑出一个界面
        NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);

        //监听整个NavigationItem
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_apple:
                        Toast.makeText(MainActivity.this,"I am Apple",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_orange:
                        Toast.makeText(MainActivity.this,"I am Orange",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_mango:
                        Toast.makeText(MainActivity.this,"I am Mango",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_grapes:
                        Toast.makeText(MainActivity.this,"I am Grapes",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_strawberry:
                        Toast.makeText(MainActivity.this,"I am Strawberry",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        mDrawerLayout.closeDrawer(GravityCompat.START);//收起侧边栏
                        break;
                }
                return true;
            }

        });

        //FloatingActionButton(浮动按钮)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(MainActivity.this,"I am FloatingActionButton",Toast.LENGTH_SHORT).show();
                Snackbar.make(view,"Data deleted",Snackbar.LENGTH_SHORT).setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this,"Data restored",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

        initFruits();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);//用GridLayoutManager将一行设置为存放两个数据(item)
        fruitAdapter=new FruitAdapter(fruitList);//传入List,初始化
        recyclerView.setAdapter(fruitAdapter);

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbarmenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
//                Toast.makeText(this,"You clicked Backup",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this,FruitActivity.class);
                startActivity(intent);
                break;
            case R.id.delete:
                Toast.makeText(this,"You clicked Delete",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this,"You Clicked Setting",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    public void initFruits(){
        fruitList.clear();
        for (int i=0;i<50;i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    //定义刷新要做的操作
    private void refreshFruits(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);//睡眠2秒方便观察现象
                }catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();//重新填充ListFruit数组
                        fruitAdapter.notifyDataSetChanged();//由于数据改变，所以本句语句用来使适配器重新加载更新后的数据
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
