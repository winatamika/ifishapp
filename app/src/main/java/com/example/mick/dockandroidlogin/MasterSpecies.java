package com.example.mick.dockandroidlogin;

/**
 * Created by Mick on 2/3/2018.
 */

public class MasterSpecies {

    private String fishcode;
    private String scientific_name;
    private String species_name;
    private String tipe;


    public MasterSpecies(){}

    public MasterSpecies(String fishcode,String scientific_name , String species_name , String tipe){
        this.fishcode=fishcode;
        this.scientific_name=scientific_name;
        this.species_name=species_name;
        this.tipe=tipe;
    }

    public String getFishcode() {
        return fishcode;
    }
    public void setFishcode(String fishcode) {
        this.fishcode = fishcode;
    }

    public String getScientific_name() {
        return scientific_name;
    }
    public void setScientific_name(String scientific_name) {
        this.scientific_name = scientific_name;
    }

    public String getSpecies_name() {
        return species_name;
    }
    public void setSpecies_name(String species_name) {
        this.species_name = species_name;
    }

    public String getTipe() {
        return tipe;
    }
    public void SetTipe(String tipe) {
        this.tipe = tipe;
    }

}
