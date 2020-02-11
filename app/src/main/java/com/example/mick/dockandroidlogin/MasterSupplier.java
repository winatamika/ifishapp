package com.example.mick.dockandroidlogin;

/**
 * Created by Mick on 2/3/2018.
 */

public class MasterSupplier {

    private String k_perusahaan;
    private String n_perusahaan;
    private String status;


    public MasterSupplier(){}

    public MasterSupplier(String k_perusahaan,String n_perusahaan , String status){
        this.k_perusahaan=k_perusahaan;
        this.n_perusahaan=n_perusahaan;
        this.status=status;
    }

    public String getKperusahaan() {
        return k_perusahaan;
    }
    public void setKperusahaan(String k_perusahaan) {
        this.k_perusahaan = k_perusahaan;
    }

    public String getNperusahaan() {
        return n_perusahaan;
    }
    public void setNperusahaan(String n_perusahaan) {
        this.n_perusahaan = n_perusahaan;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;

    }

}
