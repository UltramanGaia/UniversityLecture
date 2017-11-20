package com.universitylecture.universitylecture.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Comment;

import java.util.ArrayList;

/**
 * Created by fengqingyundan on 2017/11/15.
 */

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TYPE_FOOTER = 0;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 1;  //说明是不footer的
    public static final int TYPE_HEADER = 2;

    //extends RecyclerView.Adapter<RecyclerView.ViewHolder>
//    private Context mContext;//获取活动上下文，为点击事件服务
    private ArrayList<Comment> mAnswerList = new ArrayList<>();
    private View mFooterView;
    private View mHeaderView;

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfAnswer;
        TextView answerContent;
        TextView answerTime;

        public ViewHolder(View view){
            super(view);

            if( view == mFooterView){
                return;
            }

            nameOfAnswer = (TextView) view.findViewById(R.id.name_of_answerer);
            answerContent = (TextView) view.findViewById(R.id.content_of_answer);
            answerTime = (TextView) view.findViewById(R.id.time_of_answer);
        }
    }

    public CommentAdapter(ArrayList<Comment> answerList ){
//        mContext = context;
        mAnswerList = answerList;
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
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if( mFooterView != null && viewType == TYPE_FOOTER){
            return new CommentAdapter.ViewHolder(mFooterView);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item,parent,false);

        //设置每一项的点击事件
        final CommentAdapter.ViewHolder holder = new CommentAdapter.ViewHolder(view);
        return holder;

    }

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position){
        if(getItemViewType(position) == TYPE_NORMAL){
            if(holder instanceof CommentAdapter.ViewHolder) {
                Comment comment = mAnswerList.get(position);
                Log.e("position", "onBindViewHolder: " + position);

                ((ViewHolder)holder).nameOfAnswer.setText(comment.getUsername());
                ((ViewHolder)holder).answerContent.setText(comment.getContent());
                ((ViewHolder)holder).answerTime.setText(comment.getTime());
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
            return mAnswerList.size();
        }else {
            return mAnswerList.size() + 1;
        }
    }

    public void setmAnswerList(ArrayList<Comment> answers) {
        mAnswerList = answers;
    }

    public ArrayList<Comment> getmLectureLIst() {
        return mAnswerList;
    }
}
