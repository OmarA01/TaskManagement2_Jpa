package com.digitinary.jpa.entities.taskmanagement;

import com.digitinary.jpa.enums.Priority;
import com.digitinary.jpa.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a task in the system
 *
 * @author Omar Asaad
 * @since 24/7/2024
 */
@Entity
@Table(name = "Task")
public class Task {

    private Integer id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDate dueDate;
    private Set<Project> projects = new HashSet<>();
    /**
     * Default constructor
     */
    public Task(){

    }

    /*public Task(String title, String description, String status, String priority, String dueDate){
        if(title == null || description == null || status == null || priority == null || dueDate == null)
            throw new IllegalArgumentException("Some attributes are null. Please provide valid attributes");

        if (!dueDate.matches("^(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/\\d{4}$"))
            throw new IllegalArgumentException("Due Date should match this format dd/MM/yyyy");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dueDate, formatter);

        this.id = 0;
        this.title = title;
        this.description = description;
        setStatus(status);
        setPriority(priority);
        this.dueDate = date;
    }*/

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        status = status.toUpperCase();
        switch (status) {
            case "P":
                this.status = Status.PENDING;
                break;
            case "I":
                this.status = Status.IN_PROGRESS;
                break;
            case "C":
                this.status = Status.COMPLETED;
                break;
            default:
                throw new IllegalArgumentException("Invalid status value");
        }
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    public Priority getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        priority = priority.toUpperCase();
        switch (priority) {
            case "H":
                this.priority = Priority.HIGH;
                break;
            case "M":
                this.priority = Priority.MEDIUM;
                break;
            case "L":
                this.priority = Priority.LOW;
                break;
            default:
                throw new IllegalArgumentException("Invalid priority value");
        }
    }

    @Column(name = "due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @ManyToMany(mappedBy = "tasks")
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString(){
        return "----- Task"+getId()+" -> title: "+getTitle()+"\nDescription: "+
                getDescription()+"\nStatus: "+getStatus()+", priority: "+
                getPriority()+"\ndue Date: "+getDueDate();
    }

}
