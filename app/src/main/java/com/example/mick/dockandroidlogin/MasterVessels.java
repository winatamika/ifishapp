package com.example.mick.dockandroidlogin;

/**
 * Created by Mick on 2/3/2018.
 */

public class MasterVessels {

    private String vic;
    private String versi;
    private String nama_kapal;
    private String nama_pemilik;
    private String tahun_pembuatan;
    private String jumlah_abk;
    private String jenis_alat_tangkap;
    private String panjang_kapal;
    private String lebar;
    private String dalam;
    private String gross_tonnage;
    private String pk;
    private String bahan_kapal;
    private String k_tpi;
    private String n_tpi;
    private String k_perusahaan;
    private String n_perusahaan;
    private String nama_kapten;

    public MasterVessels(){}

    public MasterVessels(String vic  , String versi  , String nama_kapal , String nama_pemilik , String  tahun_pembuatan , String jumlah_abk  , String jenis_alat_tangkap , String panjang_kapal  ,String lebar ,String dalam ,String gross_tonnage , String pk ,String bahan_kapal , String k_tpi ,String n_tpi , String k_perusahaan , String n_perusahaan ,String nama_kapten){

        this.vic = vic ;
        this.versi = versi ;
        this.nama_kapal = nama_kapal;
        this.nama_pemilik = nama_pemilik ;
        this.tahun_pembuatan = tahun_pembuatan ;
        this.jumlah_abk = jumlah_abk ;
        this.jenis_alat_tangkap = jenis_alat_tangkap ;
        this.panjang_kapal = panjang_kapal;
        this.lebar = lebar;
        this.dalam = dalam;
        this.gross_tonnage = gross_tonnage ;
        this.pk = pk ;
        this.bahan_kapal = bahan_kapal ;
        this.k_tpi = k_tpi ;
        this.n_tpi = n_tpi ;
        this.k_perusahaan = k_perusahaan ;
        this.n_perusahaan = n_perusahaan ;
        this.nama_kapten = nama_kapten;
    }

    public String getVic() {
        return vic;
    }
    public void setVic(String vic) {
        this.vic = vic;
    }


    public String getVersi() {
        return versi;
    }
    public void setVersi(String versi) {
        this.versi = versi;
    }

    public String getNama_kapal() {
        return nama_kapal;
    }
    public void setNama_kapal(String nama_kapal) {
        this.nama_kapal = nama_kapal;
    }


    public String getNama_pemilik() {
        return nama_pemilik;
    }
    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getTahun_pembuatan() {
        return tahun_pembuatan;
    }
    public void setTahun_pembuatan(String tahun_pembuatan) {
        this.tahun_pembuatan = tahun_pembuatan;
    }


    public String getJumlah_abk() {
        return jumlah_abk;
    }
    public void setJumlah_abk(String jumlah_abk) {
        this.jumlah_abk = jumlah_abk;
    }


    public String getJenis_alat_tangkap() {
        return jenis_alat_tangkap;
    }
    public void setJenis_alat_tangkap(String jenis_alat_tangkap) {
        this.jenis_alat_tangkap = jenis_alat_tangkap;
    }


    public String getPanjang_kapal() {
        return panjang_kapal;
    }
    public void setPanjang_kapal(String panjang_kapal) {
        this.panjang_kapal = panjang_kapal;
    }

    public String getLebar() {
        return lebar;
    }
    public void setLebar(String lebar) {
        this.lebar = lebar;
    }

    public String getDalam() {
        return dalam;
    }
    public void setDalam(String dalam) {
        this.dalam = dalam;
    }

    public String getGross_tonnage() {
        return gross_tonnage;
    }
    public void setGross_tonnage(String gross_tonnage) {
        this.gross_tonnage = gross_tonnage;
    }


    public String getPk() {
        return pk;
    }
    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getBahan_kapal() {
        return bahan_kapal;
    }
    public void setBahan_kapal(String bahan_kapal) {
        this.bahan_kapal = bahan_kapal;
    }

    public String getK_tpi() {
        return k_tpi;
    }
    public void setK_tpi(String k_tpi) {
        this.k_tpi = k_tpi;
    }


    public String getN_tpi() {
        return n_tpi;
    }
    public void setN_tpi(String n_tpi) {
        this.n_tpi = n_tpi;
    }


    public String getK_perusahaan() {
        return k_perusahaan;
    }
    public void setK_perusahaan(String k_perusahaan) {
        this.k_perusahaan = k_perusahaan;
    }

    public String getN_perusahaan() {
        return n_perusahaan;
    }
    public void setN_perusahaan(String n_perusahaan) {
        this.n_perusahaan = n_perusahaan;
    }


    public String getNama_kapten() {
        return nama_kapten;
    }
    public void setNama_kapten(String nama_kapten) {
        this.nama_kapten = nama_kapten;
    }

}
