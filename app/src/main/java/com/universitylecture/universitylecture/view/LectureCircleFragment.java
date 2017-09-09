package com.universitylecture.universitylecture.view;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.universitylecture.universitylecture.R;

//讲座圈界面
public class LectureCircleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lecture_circle, container, false);

        // Inflate the layout for this fragment
        return view;
    }

}
