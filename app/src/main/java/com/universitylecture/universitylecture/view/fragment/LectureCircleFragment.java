package com.universitylecture.universitylecture.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.PopWindowAboutMoreButton;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.view.sidebar.SlideMenu;
import com.universitylecture.universitylecture.view.tool.PersonalInformation;

import de.hdodenhof.circleimageview.CircleImageView;

//讲座圈界面
public class LectureCircleFragment extends Fragment {

    private CircleImageView drawerToggleImageButton;
    private SlideMenu slideMenu;
    private Button moreButton;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lecture_circle, container, false);

        //左上角头像框点击事件
        slideMenu = (SlideMenu) getActivity().findViewById(R.id.slideMenu);
        drawerToggleImageButton = (CircleImageView) view.findViewById(R.id.toggle_drawer_open_in_lecture_circle);
        drawerToggleImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu();
            }
        });

        //右上角加号头像框点击事件
        moreButton = (Button)  view.findViewById(R.id.more_in_lecture_circle);
        user = new User(PersonalInformation.id, PersonalInformation.name, PersonalInformation.password, PersonalInformation.sex, PersonalInformation.phoneNumber);
        moreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopWindowAboutMoreButton popWindow = new PopWindowAboutMoreButton(getActivity(), user);
                popWindow.showPopupWindow( view.findViewById(R.id.more_in_lecture_circle));
            }
        });




        // Inflate the layout for this fragment
        return view;
    }



}
