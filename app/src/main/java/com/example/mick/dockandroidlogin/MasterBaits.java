package com.example.mick.dockandroidlogin;

/**
 * Created by Mick on 2/3/2018.
 */

public class MasterBaits {

    private String category;
    private String scientific_name;
    private String species_name;
    private String description;

    public MasterBaits(){}

    public MasterBaits(String category,String scientific_name , String species_name , String description){
        this.category=category;
        this.scientific_name=scientific_name;
        this.species_name=species_name;
        this.description=description;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
