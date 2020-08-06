package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

public class RsEvent {
    //@NotNull(group=a.class)
    private String eventName;
    private String keyWord;
    @Valid
    private int userId;

    public RsEvent(String eventName, String keyWord,int userId) {
        this.eventName=eventName;
        this.keyWord=keyWord;
        this.userId=userId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
    @JsonIgnore
    public int getUserId() {
        return userId;
    }
    @JsonProperty
    public void setUserId(int userId) {
        this.userId = userId;
    }
}
