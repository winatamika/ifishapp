package com.example.mick.dockandroidlogin;

/**
 * Created by Mick on 2/3/2018.
 */

public class MasterTpi {

    private String k_tpi;
    private String n_tpi;
    private String fake;


    public MasterTpi(){}

    public MasterTpi(String k_tpi,String n_tpi , String fake){
        this.k_tpi=k_tpi;
        this.n_tpi=n_tpi;
        this.fake=fake;
    }

    public String getKtpi() {
        return k_tpi;
    }
    public void setKtpi(String k_tpi) {
        this.k_tpi = k_tpi;
    }

    public String getNtpi() {
        return n_tpi;
    }
    public void setNtpi(String n_tpi) {
        this.n_tpi = n_tpi;
    }

    public String getFake() {
        return fake;
    }
    public void setFake(String fake) {
        this.fake = fake;

    }



}
