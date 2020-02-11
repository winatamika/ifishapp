package com.example.mick.dockandroidlogin;

/**
 * Created by Mick on 2/3/2018.
 */

public class MasterSetup {


    private String a;
    private String b;
    private String v;
    private String k;


    public MasterSetup(){}

    public MasterSetup(String a,String b , String v , String k ){
        this.a=a;
        this.b=b;
        this.v=v;
        this.k=k;
    }

    public String getA() {
        return a;
    }
    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }
    public void setB(String b) {
        this.b = b;
    }

    public String getV() {
        return v;
    }
    public void setV(String v) { this.v = v;}

    public String getK() {
        return k;
    }
    public void setK(String k)  { this.k = k; }

}
