package com.digitinary.jpa.entities.taskmanagement;

import com.digitinary.jpa.entities.usermanagement.User;
import com.digitinary.jpa.exceptions.AlreadyExistsException;
import com.digitinary.jpa.exceptions.InvalidNameException;
import com.digitinary.jpa.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Represents a project in the system
 *
 * @author Omar Asaad
 * @since 24/7/2024
 */
@Entity
@Table(name = "Project")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Project {

    private Integer id;
    private String name;
    private Set<Task> tasks = new HashSet<>();
    private Set<User> users = new HashSet<>();

    /**
     * Default constructor for project (required by Jpa)
     */
    public Project() {

    }

    /**
     * parameterized constructor for project
     * @param name: name of the project
     */
    public Project(String name) {
        if(name == null)
            throw new InvalidNameException("Project name cant be null");

        this.name = name;
    }

    /**
     * Get primary key of project
     * @return id of project
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_seq")
    @SequenceGenerator(name = "project_seq", sequenceName = "project_sequence", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    /**
     * set id of project
     * @param id: new id for project
     */
    private void setId(Integer id) {
        this.id = id;
    }

    /**
     * get name of project
     * mapped as a column in table user (name)
     * @return name of project
     */
    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    /**
     * set new name for project
     * @param name: new name for project
     */
    public void setName(String name) {
        if(name == null)
            throw new InvalidNameException("Project name cant be null");
        this.name = name;
    }

    /**
     * get set of tasks in project
     * create a separate table for the task & project many-to-many relationship
     * @return set of tasks
     */
    @ManyToMany
    @JoinTable(
            name = "project_task",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    public Set<Task> getTasks() {
        return tasks;
    }

    /**
     * set new set of tasks to project
     * @param tasks: new set of tasks
     */
    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * get set of users assigned to project
     * @return set of users
     */
    @OneToMany(mappedBy = "project", cascade =  CascadeType.REMOVE)
    public Set<User> getUsers() {
        return users;
    }

    /**
     * assign new set of users to project
     * @param users: new set of users
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * add task to project
     * @param task: task to be added
     */
    public void addTask(Task task) {
        if (tasks.add(task)) {
            System.out.println("Task added successfully");
        } else {
            throw new AlreadyExistsException("Task already exists in project: "+getName());
        }
    }

    /**
     * remove task from project
     * @param title: title of task to be removed
     */
    public void removeTask(String title) {
        tasks.stream().filter(task -> task.getTitle().equals(title))
                .findFirst().ifPresentOrElse(task -> {
                    tasks.remove(task);
                    System.out.println("Task \"" + task.getTitle() + "\" removed successfully from \"" + this.getName() + "\" project");
                }, () -> {
                    throw new NotFoundException("Task doesn't exist");
                });
    }

    /**
     * assign user to project
     * @param user: user to be assigned
     */
    public void addUser(User user) {
        if (users.add(user)) {
            System.out.println("User added successfully");
        } else {
            throw new AlreadyExistsException("User already exists in project: "+getName());
        }
    }

    /**
     * dissociate user from project
     * @param email: user to be removed
     */
    public void removeUser(String email) {
        users.stream().filter(user-> user.getEmail().equals(email))
                .findFirst().ifPresentOrElse(user -> {
                    users.remove(user);
                    System.out.println("User \"" + user.getFirstName() + "\" removed successfully from \"" + this.getName() + "\" project");
                }, () -> {
                    throw new NotFoundException("User doesn't exist");
                });
    }


}
