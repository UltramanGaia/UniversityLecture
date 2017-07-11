package com.universitylecture.universitylecture.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.util.MyApplication;

import java.util.List;

/**
 * Created by fengqingyundan on 2017/6/1.
 */
//为了使用recyclerview而建立的容器类
public class LectureAdapterTwo extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TYPE_FOOTER = 0;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 1;  //说明是不footer的
    public static final int TYPE_HEADER = 2;

    private Context mContext;//获取活动上下文，为点击事件服务
    private List<Lecture> mLectureLIst;
    private View mFooterView;
    private View mHeaderView;

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView lectureImage;
        TextView lectureName;

        public ViewHolder(View view){
            super(view);

            if( view == mFooterView){
                return;
            }
            lectureImage = (ImageView) view.findViewById(R.id.lectrue_image);
            lectureName = (TextView) view.findViewById(R.id.lecture_name);
        }
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }

    public LectureAdapterTwo(List<Lecture> lectureList , Context context){
        mLectureLIst = lectureList;
        mContext = context;
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
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        if(getItemViewType(position) == TYPE_NORMAL){
            if(holder instanceof ViewHolder) {
                Lecture lecture = mLectureLIst.get(position);
/*
                ((ViewHolder)holder).lectureImage.setImageResource(lecture.getImageId());
*/
                Glide.with(MyApplication.getContext()).load(lecture.getImagePath()).into(((ViewHolder)holder).lectureImage);
                ((ViewHolder)holder).lectureName.setText(lecture.getTitle());
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
}
