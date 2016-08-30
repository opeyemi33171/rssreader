package com.example.opeyemi.rssreader.datamodels;

import java.util.Date;

/**
 * Created by opeyemi on 11/08/2016.
 */
public class SourceItem {

    private String name;
    private String description;
    private String icon;
    private String link;
    private String parentSource;
    private Date date;

    public SourceItem(){

    }

    public SourceItem(String name, String description, String icon, String link){

        this.name = name;
        this.description = description;
        this.icon = icon;
        this.link = link;

    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getParentSource() {
        return parentSource;
    }

    public void setParentSource(String parentSource) {
        this.parentSource = parentSource;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
