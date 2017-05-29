package com.universitylecture.universitylecture;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengqingyundan on 2017/5/28.
 */
//讲座系统类，为单例模式，掌管学校、学生用户、讲座发布者的信息

public class LectureSystem {
    private List schools = new ArrayList<School>();
    private List clients = new ArrayList<client>();
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
        Lecture first = new Lecture( "0" , "1月1日" , "A1-101" , "软件学院" ,"2015软件文化节颁奖","软件学生会","0.5","软件文化节闭幕式" , R.drawable.test );
        Lecture second = new Lecture( "1","1月2日","A1-102","软件学院","2015深信服杯杯比赛规则讲解","深信服老总","0.5","深信服杯开幕式" , R.drawable.test );
        Lecture third = new Lecture("2","1月3日","A1-103","软件学院","2015深信服杯决赛答辩","深信服工作人员","0.5","深信服决赛", R.drawable.test );
        Lecture forth = new Lecture("3","1月4日","A1-104","软件学院","2015软件文化节开幕式","软件学生会","0.5","软件文化节", R.drawable.test );
        Lecture fifth = new Lecture( "4" , "1月1日" , "A1-101" , "软件学院" ,"2016软件文化节颁奖","软件学生会","0.5","软件文化节闭幕式", R.drawable.test );
        Lecture sixth = new Lecture( "5","1月2日","A1-102","软件学院","2016深信服杯杯比赛规则讲解","深信服老总","0.5","深信服杯开幕式", R.drawable.test );
        Lecture seventh = new Lecture("6","1月3日","A1-103","软件学院","2016深信服杯决赛答辩","深信服工作人员","0.5","深信服决赛", R.drawable.test );
        Lecture eighth = new Lecture("7","1月4日","A1-104","软件学院","2016软件文化节开幕式","软件学生会","0.5","软件文化节", R.drawable.test );
        Lecture ninth = new Lecture( "8" , "1月1日" , "A1-101" , "软件学院" ,"2017软件文化节颁奖","软件学生会","0.5","软件文化节闭幕式", R.drawable.test );
        Lecture tenth = new Lecture( "9","1月2日","A1-102","软件学院","2017深信服杯杯比赛规则讲解","深信服老总","0.5","深信服杯开幕式", R.drawable.test );
        Lecture eleventh = new Lecture("10","1月3日","A1-103","软件学院","2017深信服杯决赛答辩","深信服工作人员","0.5","深信服决赛", R.drawable.test );
        Lecture twelvth = new Lecture("11","1月4日","A1-104","软件学院","2017软件文化节开幕式","软件学生会","0.5","软件文化节", R.drawable.test );

        for( int i = 0 ; i < 3 ; i++ ){
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
        }
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
