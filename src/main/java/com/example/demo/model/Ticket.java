package com.example.demo.model;

public class Ticket {

    public int id;
    public String title;
    public String type;
    public String status;
    public String statusIcon;
    public String statusClass;
    public String priority;
    public String priorityClass;
    public String responsible;
    public String sla;
    public String updated;
    public String createdAt;

    public Ticket(int id, String title, String type, String status,
            String statusIcon, String statusClass,
            String priority, String priorityClass,
            String responsible, String sla,
            String updated, String createdAt) {

        this.id = id;
        this.title = title;
        this.type = type;
        this.status = status;
        this.statusIcon = statusIcon;
        this.statusClass = statusClass;
        this.priority = priority;
        this.priorityClass = priorityClass;
        this.responsible = responsible;
        this.sla = sla;
        this.updated = updated;
        this.createdAt = createdAt;
    }
}
