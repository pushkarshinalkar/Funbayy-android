package com.example.layoutsapp.ModelClasses;

import android.content.Context;

public class StarCast_ModelClass {

    private String SCname;
    private String SCdepartment;
    //    private String Epdate1;
//    private String Epdesc1;
    private String SCrole;

    private String SCimageLink;
    //new code



    public StarCast_ModelClass(String castname, String castRoleName, String castDept, String castImageLink)
    {
        this.SCname=castname;
        this.SCrole=castRoleName;;
        this.SCdepartment=castDept;
        this.SCimageLink=castImageLink;
    }

    public String getSCname() {
        return SCname;
    }

    public  String getSCrole(){
        return  SCrole;
    }

    public  String getSCdepartment(){return SCdepartment; }
    public  String getSCimageLink(){return SCimageLink; }



}

