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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Comment;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.util.Constant;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.view.functionActivity.LectureContentActivity;

import java.util.ArrayList;

/**
 * Created by fengqingyundan on 2017/10/8.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TYPE_FOOTER = 0;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 1;  //说明是不footer的
    public static final int TYPE_HEADER = 2;
    
    //extends RecyclerView.Adapter<RecyclerView.ViewHolder>
    private Context mContext;//获取活动上下文，为点击事件服务
    private ArrayList<Comment> mCommentList = new ArrayList<>();
    private View mFooterView;
    private View mHeaderView;

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout comment_item;
        TextView topicLecture;
        TextView question;
        TextView description;
        TextView asker;

        public ViewHolder(View view){
            super(view);

            if( view == mFooterView){
                return;
            }

            comment_item = (LinearLayout) view.findViewById(R.id.comment_item_in_lecture_circle);
            topicLecture = (TextView) view.findViewById(R.id.topicLecture_of_comment);
            question = (TextView) view.findViewById(R.id.question_of_comment);
            description = (TextView) view.findViewById(R.id.description_of_comment);
            asker = (TextView) view.findViewById(R.id.asker_of_comment);
        }
    }

    public CommentAdapter(ArrayList<Comment> commentList , Context context){
        mContext = context;
        mCommentList = commentList;
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
            return new CommentAdapter.ViewHolder(mFooterView);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);

        //设置每一项的点击事件
        final ViewHolder holder = new ViewHolder(view);
        holder.comment_item.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int position = holder.getAdapterPosition();
                Comment comment = mCommentList.get(position);
                Toast.makeText(mContext , "点击了一下" , Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mContext , LectureContentActivity.class);
//                intent.putExtra("lecture_item",lecture);
//                intent.putExtra("alarm" , alarm );
//                mContext.startActivity(intent);
            }
        });
        return holder;

    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        if(getItemViewType(position) == TYPE_NORMAL){
            if(holder instanceof CommentAdapter.ViewHolder) {
                Comment comment = mCommentList.get(position);
                Log.e("position", "onBindViewHolder: " + position);

                ((ViewHolder)holder).topicLecture.setText(comment.getTopicLecture());

                ((ViewHolder)holder).question.setText(comment.getQuestion());
                ((ViewHolder)holder).description.setText(comment.getDescription());
                ((ViewHolder)holder).asker.setText(comment.getAsker());
                return;
            }
            return;
        }else{
            return;
        }

//        Lecture lecture = mCommentList.get(position);
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
        //return mCommentList.size();

        if( mFooterView == null){
            return mCommentList.size();
        }else {
            return mCommentList.size() + 1;
        }
    }

    public void setmCommentList(ArrayList<Comment> comments) {
        mCommentList = comments;
    }

    public ArrayList<Comment> getmLectureLIst() {
        return mCommentList;
    }
}
