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

    /**
     * Parameterized constructor for task
     *
     * @param title       title of the task
     * @param description description of the task
     * @param status      status of the task
     * @param priority    priority of the task
     * @param dueDate     due date of the task in dd/MM/yyyy format
     */

    public Task(String title, String description, Status status, Priority priority, String dueDate){
        if(title == null || description == null || status == null || priority == null || dueDate == null)
            throw new InvalidNameException("Some attributes are null. Please provide valid attributes");

        isValidDate(dueDate);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(dueDate, formatter);

        this.title = title;
        this.description = description;
        setStatus(status);
        setPriority(priority);
        this.dueDate = date;
    }

    /**
     * Get primary key of task
     *
     * @return id of task
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
    @SequenceGenerator(name = "task_seq", sequenceName = "task_sequence", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    /**
     * Set id of task
     *
     * @param id new id for task
     */
    private void setId(int id) {
        this.id = id;
    }

    /**
     * Get title of task
     *
     * @return title of task
     */
    @Column(name = "title", unique = true)
    public String getTitle() {
        return title;
    }

    /**
     * Set new title for task
     *
     * @param title new title for task
     */
    public void setTitle(String title) {
        validateString(title, "title");
        this.title = title;
    }

    /**
     * Get description of task
     *
     * @return description of task
     */
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    /**
     * Set new description for task
     *
     * @param description new description for task
     */
    public void setDescription(String description) {
        validateString(description, "description");
        this.description = description;
    }

    /**
     * Get status of task
     *
     * @return status of task
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public Status getStatus() {
        return status;
    }

    /**
     * Set new status for task
     *
     * @param status new status for task
     */
    public void setStatus(Status status){
        this.status = status;
    }

    /**
     * Get priority of task
     *
     * @return priority of task
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    public Priority getPriority() {
        return priority;
    }

    /**
     * Set new priority for task
     *
     * @param priority new priority for task
     */
    public void setPriority(Priority priority){
        this.priority = priority;
    }

    /**
     * Get due date of task
     *
     * @return due date of task
     */
    @Column(name = "due_date")
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Set new due date for task
     *
     * @param dueDate new due date for task
     */
    public void setDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }

    /**
     * Get set of projects associated with task
     *
     * @return set of projects
     */
    @ManyToMany(mappedBy = "tasks")
    public Set<Project> getProjects() {
        return projects;
    }

    /**
     * Set new set of projects to task
     *
     * @param projects new set of projects
     */
    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    /**
     * Get set of users associated with task
     *
     * @return set of users
     */
    @ManyToMany(mappedBy = "tasks")
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Set new set of users to task
     *
     * @param users new set of users
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Validate if the due date is in the correct format
     *
     * @param dueDate due date in string format
     */
    private void isValidDate(String dueDate){
        if (!dueDate.matches("^(0[1-9]|[1-2][0-9]|3[0-1])/(0[1-9]|1[0-2])/\\d{4}$"))
            throw new InvalidDateException("Due Date should match this format dd/MM/yyyy");
    }

    /**
     * Validate if the given string is not null
     *
     * @param string string to be validated
     * @param type   type of the string (title, description, etc.)
     */
    private void validateString(String string, String type){
        if(string == null)
            throw new InvalidNameException(type+" can't be null");
    }

    /**
     * Update the attributes of the task
     *
     * @param task task with new attributes
     */
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

    /**
     * Convert task object to string
     *
     * @return string representation of task
     */
    @Override
    public String toString(){
        return "----- Task"+getId()+" -> title: "+getTitle()+"\nDescription: "+
                getDescription()+"\nStatus: "+getStatus()+", priority: "+
                getPriority()+"\ndue Date: "+getDueDate();
    }

}
