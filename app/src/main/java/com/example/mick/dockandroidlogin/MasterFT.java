package com.example.mick.dockandroidlogin;

/**
 * Created by Mick on 2/24/2019.
 */

public class MasterFT {

    private String id;
    private String k_tpi;
    private String nama_ft;

    public MasterFT(){}

    public MasterFT(String id, String k_tpi , String nama_ft ){
        this.id=id;
        this.k_tpi=k_tpi;
        this.nama_ft=nama_ft;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getKtpi() {
        return k_tpi;
    }
    public void setKtpi(String k_tpi) {
        this.k_tpi = k_tpi;
    }

    public String getNamaFt() {
        return nama_ft;
    }
    public void setNamaFt(String nama_ft) {
        this.nama_ft = nama_ft;
    }


}
