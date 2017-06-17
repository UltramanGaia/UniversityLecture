package com.universitylecture.universitylecture;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by fengqingyundan on 2017/5/28.
 */
//为了使用Listview而建立的容器类
public class LectureAdapter extends ArrayAdapter<Lecture> {
    private int resourceId;

    public LectureAdapter(Context context , int textViewResourceId , List<Lecture> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    public View getView(int position , View converView , ViewGroup parent){
        Lecture lecture = getItem(position);
        View view;
        ViewHolder viewHolder;
        if( converView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.lecture_Image = (ImageView) view.findViewById(R.id.lectrue_image);
            viewHolder.lecture_name = (TextView) view.findViewById(R.id.lecture_name);
            view.setTag(viewHolder);
        }
        else{
            view = converView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.lecture_Image.setImageResource(lecture.getImageId());
        viewHolder.lecture_name.setText(lecture.getTitle());
        return view;
    }

    class ViewHolder{
        ImageView lecture_Image;
        TextView lecture_name;
    }
}
