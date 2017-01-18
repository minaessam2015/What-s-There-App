package com.example.shafy.whatsthere.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shafy on 15/12/2016.
 */

public class Sources {
    private String id;
    private String name;
    private String description;
    private String sourceURL;
    private String LOGO_URL=" http://i.newsapi.org/"+id+"-l.png";
    private static String category="general";
    private static String SOURCE_URL=" https://newsapi.org/v1/sources?category="+category;
    public static List<Sources> sources=new ArrayList<>();
    public Sources(String id, String name, String description, String sourceURL){
        this.id=id;
        this.name=name;
        this.description=description;
        this.sourceURL=sourceURL;
    }

    public static String getSourceUrl() {
        return SOURCE_URL;
    }

    public static void setCategory(String category) {
        Sources.category = category;
        SOURCE_URL=" https://newsapi.org/v1/sources?category="+category;
    }

    public String getSourceId(){return id;}
    public void setSourceID(String id){this.id=id;}

    public String getSourceName() {
        return name;
    }

    public void setSourceName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public String getLogoURL() {
        return LOGO_URL;
    }

}
