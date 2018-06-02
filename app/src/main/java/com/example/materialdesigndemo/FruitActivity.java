package com.example.materialdesigndemo;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class FruitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);

        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolBar);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//将toolBar设置为actionBar显示
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);//显示HomeAsUp键
            actionBar.setTitle("Cat");
        }
//        collapsingToolbarLayout.setTitle("Cat");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch(menuItem.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(menuItem);//最后返回父类的方法的返回值boolean类型
    }
}
