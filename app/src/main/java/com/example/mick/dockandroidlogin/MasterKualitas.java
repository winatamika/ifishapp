package com.example.mick.dockandroidlogin;

/**
 * Created by Mick on 7/25/2018.
 */

public class MasterKualitas {

    private String k_tpi;
    private String k_perusahaan;
    private String tipe;
    private String kode;
    private String deskripsi;


    public MasterKualitas(){}


    public MasterKualitas(String k_tpi  , String k_perusahaan  , String tipe , String kode , String  deskripsi ) {
        this.k_tpi = k_tpi ;
        this.k_perusahaan = k_perusahaan ;
        this.tipe = tipe ;
        this.kode = kode ;
        this.deskripsi = deskripsi ;

    }

    public String getKtpi() {
        return k_tpi;
    }
    public void setKtpi(String k_tpi) {
        this.k_tpi = k_tpi;
    }

    public String getKperusahaan() {
        return k_perusahaan;
    }
    public void setKperusahaan(String k_perusahaan) {
        this.k_perusahaan = k_perusahaan;
    }

    public String getTipe() {
        return tipe;
    }
    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getKode() {
        return kode;
    }
    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    }

