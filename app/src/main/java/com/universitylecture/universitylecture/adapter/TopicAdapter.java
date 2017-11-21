package com.universitylecture.universitylecture.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Topic;
import com.universitylecture.universitylecture.view.LectureCircleActivity.AnswerListActivity;

import java.util.ArrayList;

/**
 * Created by fengqingyundan on 2017/10/8.
 */

//讲座圈页面中评论列表的adapter
public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TYPE_FOOTER = 0;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 1;  //说明是不footer的
    public static final int TYPE_HEADER = 2;
    
    //extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    private Context mContext;//获取活动上下文，为点击事件服务
    private ArrayList<Topic> mTopicList = new ArrayList<>();
    private View mFooterView;
    private View mHeaderView;

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout topic_item;
        TextView question;
        TextView description;

        public ViewHolder(View view){
            super(view);

            if( view == mFooterView){
                return;
            }

            topic_item = (LinearLayout) view.findViewById(R.id.topic_item_in_lecture_circle);
            question = (TextView) view.findViewById(R.id.question_of_topic);
            description = (TextView) view.findViewById(R.id.description_of_topic);
        }
    }

    public TopicAdapter(ArrayList<Topic> commentList , Context context){
        mContext = context;
        mTopicList = commentList;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if( mFooterView != null && viewType == TYPE_FOOTER){
            return new TopicAdapter.ViewHolder(mFooterView);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_item,parent,false);

        //设置每一项的点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.topic_item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Topic topic = mTopicList.get(position);
//                Toast.makeText(mContext , "点击了一下" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext , AnswerListActivity.class);
                intent.putExtra("topic_item",topic);
                mContext.startActivity(intent);
            }
        });
        return holder;

    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        if(getItemViewType(position) == TYPE_NORMAL){
            if(holder instanceof TopicAdapter.ViewHolder) {
                Topic Topic = mTopicList.get(position);
                Log.e("position", "onBindViewHolder: " + position);

                ((ViewHolder)holder).question.setText(Topic.getTitle());
                ((ViewHolder)holder).description.setText(Topic.getDescription());
                return;
            }
            return;
        }else{
            return;
        }

//        Lecture lecture = mTopicList.get(position);
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
        //return mTopicList.size();

        if( mFooterView == null){
            return mTopicList.size();
        }else {
            return mTopicList.size() + 1;
        }
    }

    public void setmCommentList(ArrayList<Topic> comments) {
        mTopicList = comments;
    }

    public ArrayList<Topic> getmLectureLIst() {
        return mTopicList;
    }
}
