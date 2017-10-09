package com.universitylecture.universitylecture.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.util.Constant;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.view.functionActivity.LectureContentActivity;

import java.util.ArrayList;

/**
 * Created by fengqingyundan on 2017/6/1.
 */
//为了使用recyclerview而建立的容器类
public class LectureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TYPE_FOOTER = 0;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 1;  //说明是不footer的
    public static final int TYPE_HEADER = 2;

    private Context mContext;//获取活动上下文，为点击事件服务
    private ArrayList<Lecture> mLectureLIst = new ArrayList<>();
    private View mFooterView;
    private View mHeaderView;

    private String alarm;//传入set为有闹钟选择按钮，unset为没有

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout lecture_item;
        ImageView lectureImage;
        TextView lectureName;
        TextView lectureTime;
        TextView lectureClassroom;
        TextView lectureLecturer;
        TextView lectureIntroduction;

        public ViewHolder(View view){
            super(view);

            if( view == mFooterView){
                return;
            }

            lecture_item = (LinearLayout) view.findViewById(R.id.lecture_item_in_mylecture_or_lecturelist);
            lectureName = (TextView) view.findViewById(R.id.lecture_name_in_mylecture_or_lecturelist);
            lectureClassroom = (TextView) view.findViewById(R.id.lecture_classroom_in_mylecture_or_lecturelist);
            lectureTime = (TextView) view.findViewById(R.id.lecture_time_in_mylecture_or_lecturelist);
            lectureLecturer = (TextView) view.findViewById(R.id.lecture_lecturer_in_mylecture_or_lecturelist);
            lectureIntroduction = (TextView) view.findViewById(R.id.lecture_introduction_in_mylecture_or_lecturelist);

            DisplayMetrics dm = MyApplication.getContext().getResources().getDisplayMetrics();
            int width = dm.widthPixels;
            int height = dm.heightPixels;

            lectureImage = (ImageView) view.findViewById(R.id.lecture_image_in_mylecture_or_lecturelist);
            LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) lectureImage.getLayoutParams();
//获取当前控件的布局对象
            params.height=width/3;//设置当前控件布局的高度
            lectureImage.setLayoutParams(params);//将设置好的布局参数应用到控件中
        }
    }

    public LectureAdapter(ArrayList<Lecture> lectureList , Context context , String alarmSet){
        mContext = context;
        mLectureLIst = lectureList;
        alarm = alarmSet;
    }


    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }


    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View mFooterView) {
        this.mFooterView = mFooterView;
        notifyItemInserted(getItemCount()-1);
    }

    //创建View，如果是FooterView，直接在Holder中返回
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if( mFooterView != null && viewType == TYPE_FOOTER){
            return new ViewHolder(mFooterView);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecture_item,parent,false);

        //设置每一项的点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.lecture_item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Lecture lecture = mLectureLIst.get(position);
                Intent intent = new Intent(mContext , LectureContentActivity.class);
                intent.putExtra("lecture_item",lecture);
                intent.putExtra("alarm" , alarm );
                mContext.startActivity(intent);
            }
        });
        return holder;

    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        if(getItemViewType(position) == TYPE_NORMAL){
            if(holder instanceof ViewHolder) {
                Lecture lecture = mLectureLIst.get(position);
                Log.e("position", "onBindViewHolder: " + position);

                ((ViewHolder)holder).lectureName.setText(lecture.getTitle());

                String date = lecture.getTime().split(" ")[0];
                String time = lecture.getTime().split(" ")[1];
                ((ViewHolder)holder).lectureTime.setText(date + " " + time.split(":")[0] + ":" + time.split(":")[1]);
                ((ViewHolder)holder).lectureClassroom.setText(lecture.getClassroom());
                ((ViewHolder)holder).lectureLecturer.setText(lecture.getLecturer());
                ((ViewHolder)holder).lectureIntroduction.setText(lecture.getIntroduction());
                Glide.with(MyApplication.getContext()).load(Constant.IMAGE_URI + lecture.getImagePath()).into(((ViewHolder)holder).lectureImage);

                return;
            }
            return;
        }else{
            return;
        }

//        Lecture lecture = mLectureLIst.get(position);
//        ((ViewHolder)holder).lectureImage.setImageResource(lecture.getImageId());
//        ((ViewHolder)holder).lectureName.setText(lecture.getName());
    }

    /** 重写这个方法，很重要，是Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */
    @Override
    public int getItemViewType(int position) {
        if ( mFooterView == null){
            return TYPE_NORMAL;
        }
        if (position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    public int getItemCount(){
        //return mLectureLIst.size();

        if( mFooterView == null){
            return mLectureLIst.size();
        }else {
            return mLectureLIst.size() + 1;
        }
    }

    public void setmLectureLIst(ArrayList<Lecture> lectures) {
        mLectureLIst = lectures;
    }

    public ArrayList<Lecture> getmLectureLIst() {
        return mLectureLIst;
    }
}
