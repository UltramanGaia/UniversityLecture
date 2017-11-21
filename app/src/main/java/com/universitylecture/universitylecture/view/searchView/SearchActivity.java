package com.universitylecture.universitylecture.view.searchView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.githang.statusbar.StatusBarCompat;
import com.universitylecture.universitylecture.R;

import static android.view.Window.FEATURE_NO_TITLE;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//状态栏不会被隐藏但activity布局会扩展到状态栏所在位置
                    // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//导航栏不会被隐藏但activity布局会扩展到导航栏所在位置
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            //半透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //半透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_search);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.transparent),true);

        //初始化搜索框
        init();
    }

    /**
     * 初始化成员变量
     */

    private EditText et_search; // 搜索按键
    private Button tv_clear;  // 删除搜索记录按键
    private Button searchBack; // 返回按键
    private FlowLayout hisSearch; //历史搜索
    private FlowLayout hotSearch; //热门搜索
    private TextView noHisSearch; //提示暂无搜索记录字样

    // ListView列表 & 适配器
    private SearchListView listView;
    private BaseAdapter adapter;

    // 数据库变量
    // 用于存放历史搜索记录
    private RecordSQLiteOpenHelper helper ;
    private SQLiteDatabase db;

    // 回调接口
    private ICallBack mCallBack; // 搜索按键回调接口
    private bCallBack bCallBack; // 返回按键回调接口

    /**
     * 初始化搜索框
     */
    private void init(){

        //初始化UI组件
        initView();

        //实例化数据库SQLiteOpenHelper子类对象
        helper = new RecordSQLiteOpenHelper(this);

        //第1次进入时查询所有的历史搜索记录
        queryData("");

        /**
         * "清空搜索历史"按钮
         */
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                builder.setMessage("确定要删除全部历史记录？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 清空数据库和历史搜索标签
                        deleteData();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
            }
        });

        /**
         * 监听输入键盘更换后的搜索按键
         * 调用时刻：点击键盘上的搜索键时
         */
        et_search.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {

                    // 1. 点击搜索按键后，根据输入的搜索字段进行查询
                    // 注：由于此处需求会根据自身情况不同而不同，所以具体逻辑由开发者自己实现，此处仅留出接口
                    if (!(mCallBack == null)){
                        mCallBack.SearchAciton(et_search.getText().toString());
                    }
//                    Toast.makeText(SearchActivity.this, "需要搜索的是" + et_search.getText(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SearchActivity.this,SearchAnswerActivity.class);
                    intent.putExtra("searchKey" , et_search.getText().toString());
                    startActivity(intent);

                    // 2. 点击搜索键后，对该搜索字段在数据库是否存在进行检查（查询）
                    boolean hasData = hasData(et_search.getText().toString().trim());
                    // 3. 若存在，则不保存；若不存在，则将该搜索字段保存（插入）到数据库，并作为历史搜索记录
                    if (!hasData) {
                        if ( et_search.getText().toString().trim().length() > 0){
                            insertData(et_search.getText().toString().trim());

                            hisSearch.addTag(et_search.getText().toString().trim());
                            tv_clear.setVisibility(View.VISIBLE);
                            noHisSearch.setVisibility(View.GONE);
                        }
                    }
                }
                return false;
            }
        });


        /**
         * 搜索框的文本变化实时监听
         */
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            // 输入文本后调用该方法
            @Override
            public void afterTextChanged(Editable s) {
                // 每次输入后，模糊查询数据库 & 显示
                // 注：若搜索框为空,则模糊搜索空字符 = 显示所有的搜索历史
                String tempName = et_search.getText().toString();
                //queryData(tempName); // ->>关注1
            }
        });


        /**
         * 搜索记录列表（ListView）监听
         * 即当用户点击搜索历史里的字段后,会直接将结果当作搜索字段进行搜索
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 获取用户点击列表里的文字,并自动填充到搜索框内
                //TextView textView = (TextView) view.findViewById(android.R.id.text1);
                //String name = textView.getText().toString();
                //et_search.setText(name);
                //Toast.makeText(SearchActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 返回按钮返回上一层
         */
        searchBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }


    /**
     * 绑定搜索框xml视图
     */
    private void initView(){
        et_search = (EditText) findViewById(R.id.et_search);
        listView = (SearchListView) findViewById(R.id.listView);
        tv_clear = (Button) findViewById(R.id.tv_clear);
        searchBack = (Button) findViewById(R.id.search_back);
        initHisTag();
        initHotTag();
    }

    /**
     * 模糊查询数据 & 显示到ListView列表上
     */
    private void queryData(String tempName) {

        // 1. 模糊搜索
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 2. 创建adapter适配器对象 & 装入模糊搜索的结果
        adapter = new SimpleCursorAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, cursor, new String[] { "name" },
                new int[] { android.R.id.text1 }, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 3. 设置适配器
        //listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // 当输入框为空 & 数据库中有搜索记录时，显示 "删除搜索记录"按钮
        if ( cursor.getCount() != 0){
            while ( cursor.moveToNext()){
                hisSearch.addTag(cursor.getString(1));
            }
            tv_clear.setVisibility(View.VISIBLE);
            noHisSearch.setVisibility(View.GONE);
        }
        else {
            tv_clear.setVisibility(View.INVISIBLE);
            noHisSearch.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 清空数据库
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
        hisSearch.cleanTag();
        tv_clear.setVisibility(View.INVISIBLE);
        noHisSearch.setVisibility(View.VISIBLE);
    }

    /**
     * 检查数据库中是否已经有该搜索记录
     */
    private boolean hasData(String tempName) {
        // 从数据库中Record表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        return cursor.moveToNext();
    }

    /**
     * 插入数据到数据库，即写入搜索字段到历史搜索记录
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 点击键盘中搜索键后的操作，用于接口回调
     */
    public void setOnClickSearch(ICallBack mCallBack){
        this.mCallBack = mCallBack;
    }

    /**
     * 点击返回后的操作，用于接口回调
     */
    public void setOnClickBack(bCallBack bCallBack){
        this.bCallBack = bCallBack;
    }

    /**
     * 初始化历史搜索
     */
    private void initHisTag() {
        hisSearch = (FlowLayout) findViewById(R.id.his_search);
        noHisSearch = (TextView) findViewById(R.id.no_search_history);
        hisSearch.setOnTagClickListener(new FlowLayout.OnTagClickListener() {
            @Override
            public void TagClick(String text) {
                // 获取用户点击列表里的文字,并自动填充到搜索框内
                et_search.setText(text);
                Toast.makeText(SearchActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化热门搜索
     */
    private void initHotTag() {
        hotSearch = (FlowLayout) findViewById(R.id.hot_search);
        String[] mStrings = {"辩在华园", "招聘会", "阿里巴巴校招", "三星Galaxy杯", "网易游戏策划", "美团外卖", "google", "淘宝", "小米", "黑框框程序设计大赛"};
        hotSearch.setData(mStrings);
        hotSearch.setOnTagClickListener(new FlowLayout.OnTagClickListener() {
            @Override
            public void TagClick(String text) {
                // 获取用户点击列表里的文字,并自动填充到搜索框内
                et_search.setText(text);
                Toast.makeText(SearchActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
