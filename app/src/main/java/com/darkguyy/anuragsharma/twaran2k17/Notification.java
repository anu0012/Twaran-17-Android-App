package com.darkguyy.anuragsharma.twaran2k17;

/**
 * Created by anuragsharma on 28/03/17.
 */

public class Notification {
    private String topic;
    private String description;

    public Notification(String topic,String description){
        this.topic = topic;
        this.description = description;
    }

    public String getTopic(){
        return topic;
    }

    public String getDescription(){
        return description;
    }
}
