package com.example.demo.model;

import java.util.List;

public class TicketTracking {

    private int id;

    private String title;
    private String status;
    private String createdAt;
    private String priority;
    private String responsible;
    private String category;

    private Integer slaTotal;
    private Integer slaUsed;
    private String slaRemaining;

    private List<TicketTimeline> timeline;
    private List<TicketAttachment> attachments;

    public TicketTracking() {
    }

    // getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getSlaTotal() {
        return slaTotal;
    }

    public void setSlaTotal(Integer slaTotal) {
        this.slaTotal = slaTotal;
    }

    public Integer getSlaUsed() {
        return slaUsed;
    }

    public void setSlaUsed(Integer slaUsed) {
        this.slaUsed = slaUsed;
    }

    public String getSlaRemaining() {
        return slaRemaining;
    }

    public void setSlaRemaining(String slaRemaining) {
        this.slaRemaining = slaRemaining;
    }

    public List<TicketTimeline> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<TicketTimeline> timeline) {
        this.timeline = timeline;
    }

    public List<TicketAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TicketAttachment> attachments) {
        this.attachments = attachments;
    }
}