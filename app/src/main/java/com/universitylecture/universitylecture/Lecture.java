package com.universitylecture.universitylecture;

/**
 * Created by fengqingyundan on 2017/5/27.
 */
//讲座类
public class Lecture {
    private String ID;
    private String time;
    private String classroom;
    private String institude;
    private String description;
    private String lecturer;
    private String credit;
    private String name;
    int imageId;

    public Lecture( String pID , String ptime , String pclassroom , String pinstitude , String pdescription ,
                    String plecturer , String pcredit , String pname ,int pimageId){
        ID = pID;
        time = ptime;
        classroom = pclassroom;
        institude = pinstitude;
        description = pdescription;
        lecturer = plecturer;
        credit = pcredit;
        name = pname;
        imageId = pimageId;
    }
    public void setImageId( int pimageId ){
        imageId = pimageId;
    }

    public int getImageId(){
        return imageId;
    }

    public void setID(String pID){
        ID = pID;
    }

    public String getID(){
        return ID;
    }

    public void setTime(String ptime){
        time = ptime;
    }

    public String getTime(){
        return time;
    }

    public void setClassroom( String pclassroom ){
        classroom = pclassroom;
    }

    public String getClassroom(){
        return classroom;
    }

    public void setInstitude( String pinstitude ){
        institude = pinstitude;
    }

    public String getInstitude(){
        return institude;
    }

    public void setDescription( String pdescription ){
        description = pdescription;
    }

    public String getDescription(){
        return description;
    }

    public void setLecturer(String plecturer){
        lecturer = plecturer;
    }

    public String getLecturer(){
        return lecturer;
    }

    public void setCredit(String pcredit){
        credit = pcredit;
    }

    public String getCredit(){
        return credit;
    }

    public void setName(String pname){
        name = pname;
    }

    public String getName(){
        return name;
    }
}
