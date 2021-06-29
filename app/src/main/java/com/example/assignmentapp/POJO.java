package com.example.assignmentapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class POJO {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("capital")
    @Expose
    private String Capital;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("subregion")
    @Expose
    private String subregion;
    @SerializedName("population")
    @Expose
    private String population;
    @SerializedName("borders")
    @Expose
    private ArrayList<String> borders;
    @SerializedName("languages")
    @Expose
    private ArrayList<langmod> languages;

    public POJO(String name, String capital, String flag, String region, String subregion, String population, ArrayList<String> borders, ArrayList<langmod> languages) {
        this.name = name;
        Capital = capital;
        this.flag = flag;
        this.region = region;
        this.subregion = subregion;
        this.population = population;
        this.borders = borders;
        this.languages = languages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }

    public void setBorders(ArrayList<String> borders) {
        this.borders = borders;
    }

    public ArrayList<langmod> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<langmod> languages) {
        this.languages = languages;
    }
}
