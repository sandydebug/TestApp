package com.example.assignmentapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

@Entity(tableName = "country")
public class POJO {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;
    @ColumnInfo(name = "capital")
    @SerializedName("capital")
    @Expose
    private String Capital;
    @ColumnInfo(name = "flag")
    @SerializedName("flag")
    @Expose
    private String flag;
    @ColumnInfo(name = "region")
    @SerializedName("region")
    @Expose
    private String region;
    @ColumnInfo(name = "subregion")
    @SerializedName("subregion")
    @Expose
    private String subregion;
    @ColumnInfo(name = "population")
    @SerializedName("population")
    @Expose
    private String population;
    @ColumnInfo(name = "borders")
    @TypeConverters(borderC.class)
    @SerializedName("borders")
    @Expose
    private ArrayList<String> borders;
    @ColumnInfo(name = "languages")
    @TypeConverters(langC.class)
    @SerializedName("languages")
    @Expose
    private ArrayList<langmod> languages;


    public POJO() {
    }

    public POJO(int uid, String name, String capital, String flag, String region, String subregion, String population, ArrayList<String> borders, ArrayList<langmod> languages) {

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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public static class borderC{
        @TypeConverter
        public static ArrayList<String> restoreList(String listOfString){
            return new Gson().fromJson(listOfString, new TypeToken<ArrayList<String>>() {}.getType());
        }

        @TypeConverter
        public static String saveListOfString(ArrayList<String> listOfString){
            return new Gson().toJson(listOfString);
        }
    }
    public static class langC{
        @TypeConverter
        public static ArrayList<langmod> restoreList(String listOfString){
            return new Gson().fromJson(listOfString, new TypeToken<ArrayList<langmod>>() {}.getType());
        }

        @TypeConverter
        public static String saveListOfString(ArrayList<langmod> listOfString){
            return new Gson().toJson(listOfString);
        }
    }
}


