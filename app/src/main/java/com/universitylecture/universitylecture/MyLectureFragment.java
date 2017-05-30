package com.universitylecture.universitylecture;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


public class MyLectureFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        LectureSystem lectureSystem = LectureSystem.getLectureSystem();
        List schools = lectureSystem.getSchools();
        School school = (School) schools.get(0);
        List lectures = school.getLectures();

        LectureAdapter adapter = new LectureAdapter( getActivity() , R.layout.lecture_item , lectures );
        View view = inflater.inflate(R.layout.fragment_my_lecture, container, false);
        ListView myLectures = (ListView) view.findViewById(R.id.lectures_of_myLectures);
        myLectures.setAdapter(adapter);
        return view;
    }

}

