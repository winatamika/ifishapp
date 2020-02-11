package com.example.mick.dockandroidlogin;

/**
 * Created by Mick on 2/3/2018.
 */

public class MasterGear {

    private String k_alattangkap;
    private String indonesia;
    private String english;
    private String status;


    public MasterGear(){}

    public MasterGear(String k_alattangkap,String indonesia , String english , String status ){
        this.k_alattangkap=k_alattangkap;
        this.indonesia=indonesia;
        this.english=english;
        this.status=status;
    }

    public String getKalattangkap() {
        return k_alattangkap;
    }
    public void setKalattangkap(String k_alattangkap) {
        this.k_alattangkap = k_alattangkap;
    }

    public String getIndonesia() {
        return indonesia;
    }
    public void setIndonsia(String indonesia) {
        this.indonesia = indonesia;
    }

    public String getEnglish() {
        return english;
    }
    public void setEnglish(String english) { this.english = english;}

    public String getStatus() {
        return status;
    }
    public void setStatus(String status)  { this.status = status; }



}
