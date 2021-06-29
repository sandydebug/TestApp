package com.example.assignmentapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class langmod {

    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("nativeName")
    @Expose
    String nativename;

    public langmod(String name, String nativename) {
        this.name = name;
        this.nativename = nativename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativename() {
        return nativename;
    }

    public void setNativename(String nativename) {
        this.nativename = nativename;
    }
}
