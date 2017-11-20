package com.universitylecture.universitylecture.view.functionActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.bumptech.glide.Glide;
import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.PopWindowInLectureContent;
import com.universitylecture.universitylecture.util.Constant;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.view.map.NaviBaseWalkActivity;
import com.universitylecture.universitylecture.view.sidebar.TranslucentScrollView;
import com.universitylecture.universitylecture.view.tool.BaseActivity;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import static android.view.Window.FEATURE_NO_TITLE;
import static com.universitylecture.universitylecture.util.MyApplication.getContext;

/**
 * Created by fengqingyundan on 2017/6/25.
 */
//讲座内容界面
public class LectureContentActivity extends BaseActivity implements TranslucentScrollView.OnScrollChangedListener{
    private ImageView lectureImage;
    private TextView lectureName;
    private TextView lectureTime;
    private TextView lectureClassroom;
    private TextView lectureLecturer;
    private TextView lectureCredit;
    private TextView lectureContent;      //内容
    private TextView lectureSponsor;      //讲座主办方
    private TextView lectureCo_sponsor;

    private Lecture lecture;//传进来的讲座
    private String alarm;//判断是否要使闹钟设置按钮可见

    //标题栏部件
    private Button back;
    private TextView title_in_title_bar_of_launch;
    private Button alarmSet;
    //导航去这个讲座
    private Button bt_davi;

    //toolbar透明渐变
    private TranslucentScrollView scrollView;
    private Toolbar toolbar;
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度

    //设置背景高斯模糊
    private RelativeLayout lectureContentLayout;
    private Bitmap sampleImg;
    private Bitmap gaussianBlurImg;

    /**
     * 用于控制NavigationBar的隐藏和显示
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                      | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lecture_content);

        lecture = (Lecture) getIntent().getSerializableExtra("lecture_item");
        alarm = getIntent().getStringExtra("alarm");

        initView();
        initButton();
    }

    private void initButton(){
        back = (Button) findViewById(R.id.go_back_in_lecture_information);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LectureContentActivity.super.onBackPressed();
            }
        });

        bt_davi = (Button) findViewById(R.id.bt_navigation);
        bt_davi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////
                Intent intent = new Intent(LectureContentActivity.this,NaviBaseWalkActivity.class);
                intent.putExtra("latitude", lecture.getLatitude());
                intent.putExtra("longitude",lecture.getLongitude());
                startActivity(intent);
            }
        });

        if( alarm.equals("set") ){
            alarmSet = (Button) findViewById(R.id.alarmSet);
            alarmSet.setVisibility(View.VISIBLE);
            alarmSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopWindowInLectureContent popWindow = new PopWindowInLectureContent( LectureContentActivity.this , lecture);
                    popWindow.showPopupWindow(findViewById(R.id.alarmSet));
                }
            });
        }
    }

    private void initView(){
        //设置toolbar透明渐变
        scrollView = (TranslucentScrollView) findViewById(R.id.lecture_information_scrollview);
        scrollView.setOnScrollChangedListener(this);
        toolbar = (Toolbar) findViewById(R.id.title_in_lecture_information_of_lecturelist);
        //toolbar.setBackgroundColor(Color.argb(0, 18, 176, 242));
        initMeasure();

        lectureImage = (ImageView) findViewById(R.id.lecture_image_in_lecture_content);
        //lectureImage.setImageResource(lecture.getImageId());

        lectureName = (TextView) findViewById(R.id.lecture_name_in_lecture_content);
        lectureName.setText("" + lecture.getTitle());

        lectureTime = (TextView) findViewById(R.id.lecture_time_in_lecture_content);
        lectureTime.setText("" + lecture.getTime().split(":")[0] + ":" + lecture.getTime().split(":")[1]);

        lectureClassroom = (TextView) findViewById(R.id.lecture_classroom_in_lecture_content);
        lectureClassroom.setText("" + lecture.getClassroom());

        lectureLecturer = (TextView) findViewById(R.id.lecture_lecturer_in_lecture_content);
        lectureLecturer.setText("" + lecture.getLecturer());

        lectureCredit = (TextView) findViewById(R.id.lecture_credit_in_lecture_content);
        lectureCredit.setText("" + lecture.getCredit());

        lectureContent = (TextView) findViewById(R.id.lecture_content_in_lecture_content);
        lectureContent.setText( lecture.getContent());

        lectureSponsor = (TextView) findViewById(R.id.lecture_sponsor_in_lecture_content);
        lectureSponsor.setText("" + lecture.getSponsor());

        lectureCo_sponsor = (TextView) findViewById(R.id.lecture_co_sponsor_in_lecture_content);
        lectureCo_sponsor.setText("" + lecture.getCo_sponsor());

        title_in_title_bar_of_launch = (TextView) findViewById(R.id.title_in_title_bar);
        title_in_title_bar_of_launch.setText("");

        Glide.with(getContext()).load(Constant.IMAGE_URI + lecture.getImagePath()).into(lectureImage);

        //设置背景高斯模糊
        lectureContentLayout = (RelativeLayout) findViewById(R.id.lecture_content_relativeLayout);
        sampleImg = BitmapFactory.decodeResource(getResources(), R.drawable.xinshengdai); // 获取原图
        sampleImg = scaleBitmap(sampleImg,0.7);
        gaussianBlurImg = blur(sampleImg, 25f);
        gaussianBlurImg = blur(gaussianBlurImg, 25f);
        gaussianBlurImg = blur(gaussianBlurImg, 25f);
        gaussianBlurImg = blur(gaussianBlurImg, 25f);
        lectureContentLayout.setBackground(new BitmapDrawable(gaussianBlurImg));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1 ) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    //获取了扫描结果，进行处理
                    final String lectureId = lecture.getID();
                    final String result = bundle.getString(CodeUtils.RESULT_STRING);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String m = Object2JSONUtil.joinLecture(lectureId,result);
                            String t = HttpUtilJSON.doPost(m,"joinLecture");
                            String message = JSON2ObjectUtil.getMessage(t);
//                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                            if(message.equals("OK"))
                                OutputMessage.outputMessage("扫码成功");
                            else
                                OutputMessage.outputMessage("扫码失败");
                        }
                    }).start();



                } else if (bundle.getInt(CodeUtils.RESULT_TYPE)== CodeUtils.RESULT_FAILED) {
                    Toast.makeText(LectureContentActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
    }

    @Override
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        //Y轴偏移量
        float scrollY = who.getScrollY();
        //变化率
        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
        //Toolbar背景色透明度
        toolbar.setBackgroundColor(Color.argb((int) (offset * 180),25,25,25));
        //header背景图Y轴偏移
        //imgHead.setTranslationY(scrollY / 2);
    }

    private Bitmap blur(Bitmap bitmap,float radius) {
        Bitmap output = Bitmap.createBitmap(bitmap); // 创建输出图片
        RenderScript rs = RenderScript.create(this); // 构建一个RenderScript对象
        ScriptIntrinsicBlur gaussianBlue = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)); // 创建高斯模糊脚本
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap); // 创建用于输入的脚本类型
        Allocation allOut = Allocation.createFromBitmap(rs, output); // 创建用于输出的脚本类型
        gaussianBlue.setRadius(radius); // 设置模糊半径，范围0f<radius<=25f
        gaussianBlue.setInput(allIn); // 设置输入脚本类型
        gaussianBlue.forEach(allOut); // 执行高斯模糊算法，并将结果填入输出脚本类型中
        allOut.copyTo(output); // 将输出内存编码为Bitmap，图片大小必须注意
        rs.destroy(); // 关闭RenderScript对象，API>=23则使用rs.releaseAllContexts()
        return output;
    }


    /**

     * @param bitmap      原图
     * @param scaledMultiple  缩放倍数
     * @return  按比例缩放截取正中间的部分的位图。
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, double scaledMultiple)
    {
        if( bitmap == null || scaledMultiple <= 0)
        {
            return  null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);

        double widthOfScreen = wm.getDefaultDisplay().getWidth();
        double heightOfScreen = wm.getDefaultDisplay().getHeight();
        double proportion = widthOfScreen/heightOfScreen;   //宽高比

        //从图中按比例截取正中间的部分。
        int yTopLeft = (int)(heightOrg * (1-scaledMultiple) / 2);
        int xTopLeft = (int)((widthOrg - heightOrg * scaledMultiple * proportion ) / 2);

        try{
            result = Bitmap.createBitmap(bitmap, xTopLeft, yTopLeft, (int)(heightOrg * scaledMultiple* proportion), (int)(heightOrg * scaledMultiple));
            bitmap.recycle();
        }
        catch(Exception e){
            return null;
        }

        return result;
    }
}
