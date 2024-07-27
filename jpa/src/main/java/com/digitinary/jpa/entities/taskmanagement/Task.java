package com.digitinary.jpa.entities.taskmanagement;

import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.enums.Priority;
import com.digitinary.jpa.enums.Status;
import com.digitinary.jpa.exceptions.InvalidDateException;
import com.digitinary.jpa.exceptions.InvalidNameException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Task {

    private Integer id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDate dueDate;
    private Set<Project> projects = new HashSet<>();
    private Set<User> users = new HashSet<>();
    /**
     * Default constructor
     */
    public Task(){

    }

    public Task(String title, String description, Status status, Priority priority, String dueDate){
        if(title == null || description == null || status == null || priority == null || dueDate == null)
            throw new IllegalArgumentException("Some attributes are null. Please provide valid attributes");

        isValidDate(dueDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dueDate, formatter);

        this.title = title;
        this.description = description;
        setStatus(status);
        setPriority(priority);
        this.dueDate = date;
    }

    // Getters and setters
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_sequence", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    @Column(name = "title", unique = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateString(title, "title");
        this.title = title;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateString(description, "description");
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status getStatus() {
        return status;
    }

    /*public void setStatus(String status) {
        validateString(status, "status");
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
    }*/

    public void setStatus(Status status){
        this.status = status;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    public Priority getPriority() {
        return priority;
    }

   /* public void setPriority(String priority) {
        validateString(priority, "priority");
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
    }*/

    public void setPriority(Priority priority){
        this.priority = priority;
    }

    @Column(name = "due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }

    /*public void setDueDate(String dueDate) {
        isValidDate(dueDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dueDate = LocalDate.parse(dueDate, formatter);
    }*/

    public void setDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }

    @ManyToMany(mappedBy = "tasks")
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @ManyToMany(mappedBy = "tasks")
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    private void isValidDate(String dueDate){
        if (!dueDate.matches("^(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/\\d{4}$"))
            throw new InvalidDateException("Due Date should match this format dd/MM/yyyy");
    }

    private void validateString(String string, String type){
        if(string == null)
            throw new InvalidNameException(type+" can't be null");
    }

    public void updateTask(Task task){
        if(task.getTitle() != null)
            this.setTitle(task.getTitle());
        if(task.getDescription() != null)
            this.setDescription(task.getDescription());
        if(task.getStatus() != null)
            this.setStatus(task.getStatus());
        if(task.getPriority() != null)
            this.setPriority(task.getPriority());
        if(task.getDueDate() != null)
            this.setDueDate(task.getDueDate());

        System.out.println("Task updated successfully");
    }

    @Override
    public String toString(){
        return "----- Task"+getId()+" -> title: "+getTitle()+"\nDescription: "+
                getDescription()+"\nStatus: "+getStatus()+", priority: "+
                getPriority()+"\ndue Date: "+getDueDate();
    }

}
