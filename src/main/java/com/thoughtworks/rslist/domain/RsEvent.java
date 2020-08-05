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
    private User user;

    public RsEvent(String eventName, String keyWord,User user) {
        this.eventName=eventName;
        this.keyWord=keyWord;
        this.user=user;
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
    public User getUser() {
        return user;
    }
    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }
}
