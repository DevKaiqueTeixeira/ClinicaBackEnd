package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    private int id;

    private String title;
    private String type;
    private String status;

    @Column(name = "status_icon")
    private String statusIcon;

    @Column(name = "status_class")
    private String statusClass;

    private String priority;

    @Column(name = "priority_class")
    private String priorityClass;

    private String responsible;
    private String sla;
    private String updated;

    @Column(name = "created_at")
    private String createdAt;

    // 🔹 Construtor vazio (OBRIGATÓRIO pro JPA)
    public Ticket() {
    }

    // 🔹 Construtor completo (igual ao mock)
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

    // 🔹 Getters e Setters

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusIcon() {
        return statusIcon;
    }

    public void setStatusIcon(String statusIcon) {
        this.statusIcon = statusIcon;
    }

    public String getStatusClass() {
        return statusClass;
    }

    public void setStatusClass(String statusClass) {
        this.statusClass = statusClass;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPriorityClass() {
        return priorityClass;
    }

    public void setPriorityClass(String priorityClass) {
        this.priorityClass = priorityClass;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getSla() {
        return sla;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}