package com.example.opeyemi.rssreader.datamodels;

import java.util.jar.Attributes;

import io.realm.RealmObject;

/**
 * Created by opeyemi on 11/08/2016.
 */
public class Source extends RealmObject {
    private String name;
    private String url;
    private Boolean favorite;
    private String colorHexadeciaml;

    public Source(String name, String url){

        this.name = name;
        this.url = url;
    }

    public Source(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public String getColorHexadeciaml() {
        return colorHexadeciaml;
    }

    public void setColorHexadeciaml(String colorHexadeciaml) {
        this.colorHexadeciaml = colorHexadeciaml;
    }
}
