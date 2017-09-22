package com.universitylecture.universitylecture.view.tool;

import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.LecturePublisher;
import com.universitylecture.universitylecture.pojo.School;
import com.universitylecture.universitylecture.pojo.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengqingyundan on 2017/5/28.
 */
//讲座系统类，为单例模式，掌管学校、学生用户、讲座发布者的信息
public class LectureSystem {
    private List schools = new ArrayList<School>();
    private List clients = new ArrayList<User>();
    private List lecturePublishers = new ArrayList<LecturePublisher>();

    private static LectureSystem lectureSystem = null;

    private LectureSystem(){
        initSchools();
//        initClients();
//        initLecture();
    }

    //初始化学校
    private void initSchools(){
        School scut = new School( "0" , "SCUT" );
        School sysu = new School( "1" , "SYSU" );
        School szu = new School( "2" , "SU" );

        initLectures( scut );
        initLectures( sysu );
        initLectures( szu );

        schools.add(scut);
        schools.add(sysu);
        schools.add(szu);
    }

    //对学校的讲座进行初始化
    private void initLectures( School school ){
        List lectures = school.getLectures();
        Lecture first = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture second = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture third = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture forth = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture fifth = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture sixth = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture seventh = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture eighth = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture ninth = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture tenth = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture eleventh = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);
        Lecture twelvth = new Lecture("软件文化节闭幕式","2017-7-12 18:30" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","院长","0.5","软件文化节闭幕式","软件学生会","无","",0,0);

//        for( int i = 0 ; i < 3 ; i++ ){
            lectures.add(first);
            lectures.add(second);
            lectures.add(third);
            lectures.add(forth);
            lectures.add(fifth);
            lectures.add(sixth);
            lectures.add(seventh);
            lectures.add(eighth);
            lectures.add(ninth);
            lectures.add(tenth);
            lectures.add(eleventh);
            lectures.add(twelvth);
//        }
    }


    public static LectureSystem getLectureSystem(){
        if(lectureSystem == null){
            lectureSystem = new LectureSystem();
        }
            return lectureSystem;
    }

    public List getSchools(){
        return schools;
    }

    public List getClients(){
        return clients;
    }

    public List getLecturePublishers(){
        return lecturePublishers;
    }


}
