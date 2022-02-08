package com.ask0n.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    private boolean verified;
    private int sentCount;
    private String feedback;

    public Status() {}

    public Status(boolean verified, int sentCount, String feedback) {
        this.verified = verified;
        this.sentCount = sentCount;
        this.feedback = feedback;
    }

    public boolean isVerified() {
        return verified;
    }

    @JsonProperty
    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getSentCount() {
        return sentCount;
    }

    @JsonProperty
    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

    public String getFeedback() {
        return feedback;
    }

    @JsonProperty
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
