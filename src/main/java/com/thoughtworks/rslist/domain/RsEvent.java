package com.thoughtworks.rslist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RsEvent {
    //@NotNull(group=a.class)
    private String eventName;
    private String keyWord;
    private int voteNum;
    @Valid
    private int userId;

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
    //@JsonIgnore
    public int getUserId() {
        return userId;
    }
    //@JsonProperty
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVoteNum() { return voteNum; }

    public void setVoteNum(int voteNum) { this.voteNum = voteNum; }
}
