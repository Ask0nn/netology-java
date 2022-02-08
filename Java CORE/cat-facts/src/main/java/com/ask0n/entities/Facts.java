package com.ask0n.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

@JsonIgnoreProperties(value = { "__v" })
public class Facts {
    private String id;
    private Status status;
    private String type;
    private boolean deleted;
    private String user;
    private String text;
    private String source;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private boolean used;

    public Facts() {}

    public Facts(String id, Status status, String type, boolean deleted, String user, String text, String source, LocalDateTime updatedAt, LocalDateTime createdAt, boolean used) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.deleted = deleted;
        this.user = user;
        this.text = text;
        this.source = source;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.used = used;
    }

    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    @JsonProperty
    public void setStatus(Status status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    @JsonProperty
    public void setType(String type) {
        this.type = type;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @JsonProperty
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getUser() {
        return user;
    }

    @JsonProperty
    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    @JsonProperty
    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    @JsonProperty
    public void setSource(String source) {
        this.source = source;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @JsonProperty
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isUsed() {
        return used;
    }

    @JsonProperty
    public void setUsed(boolean used) {
        this.used = used;
    }
}
