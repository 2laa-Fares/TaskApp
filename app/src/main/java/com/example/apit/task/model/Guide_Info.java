package com.example.apit.task.model;

import java.util.Date;

public class Guide_Info {
    public int Guide_Info_Syskey;
    public int Guide_ID ;
    public String Guide_Name ;
    public int Guide_NATIONALITY;
    public int Guide_Gender;
    public String Guide_Phone_Num ;
    public String Guide_Mobile_num;
    public String Guide_ID_NO ;
    public int SYSUSERKEY;
    public int LAST_SYSUSERKEY ;
    public Date SYSDATE_TIME ;
    public Date LAST_SYSDATE_TIME;
    public int Guide_IDType ;
    public boolean Guide_Status ;
    public String Guide_Eng_Name;
    public byte[] Guide_ID_image ;

    @Override
    public String toString() {
        return Guide_Name;
    }
}
