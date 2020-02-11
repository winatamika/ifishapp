package com.example.mick.dockandroidlogin.model;

/**
 * Created by Mick on 2/6/2018.
 */

public class DataTripProcess {

    private String id, k_tpi, k_perusahaan;

    public DataTripProcess() {
    }

    public DataTripProcess(String id, String k_tpi, String k_perusahaan) {
        this.id = id;
        this.k_tpi = k_tpi;
        this.k_perusahaan = k_perusahaan;
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getK_tpi() {
        return k_tpi;
    }

    public void setK_tpi(String k_tpi) {
        this.k_tpi = k_tpi;
    }

    public String getK_perusahaan() {
        return k_perusahaan;
    }

    public void setK_perusahaan(String k_perusahaan) {
        this.k_perusahaan = k_perusahaan;
    }


}
