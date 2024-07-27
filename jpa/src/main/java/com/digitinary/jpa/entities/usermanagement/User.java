package com.digitinary.jpa.entities.usermanagement;

import com.digitinary.jpa.entities.taskmanagement.Project;
import com.digitinary.jpa.entities.taskmanagement.Task;
import com.digitinary.jpa.exceptions.AlreadyExistsException;
import com.digitinary.jpa.exceptions.InvalidEmailException;
import com.digitinary.jpa.exceptions.InvalidNameException;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user in the system
 *
 * @author Omar Asaad
 * @since 24/7/2024
 */

@Entity
@Table(name = "Users")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {

    /**
     * an id for each user, a name, and an email
     */
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Task> tasks = new HashSet<>();
    private Project project;

    /**
     * default constructor for User (required by jpa)
     */
    public User(){

    }

    /**
     * Parameterized constructor for user
     * @param firstName: first name of user
     * @param lastName:  last name of user
     * @param email:     email of user
     */
    public User(String firstName, String lastName, String email){
        validateName(firstName);
        validateName(lastName);
        validateEmail(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Get Primary key of user (id)
     * @return: Id of user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    /**
     * Set id of user
     * @param id: new id for user
     */
    private void setId(Integer id) {
        this.id = id;
    }

    /**
     * get first name of user
     * mapped as a column in table user (F_Name)
     * @return first name of user
     */
    @Column(name = "F_Name")
    public String getFirstName() {
        return firstName;
    }

    /**
     * set first name of user
     * @param firstName: new first name of user
     */
    public void setFirstName(String firstName) {
        validateName(firstName);
        this.firstName = firstName;
    }

    /**
     * get last name of user
     * mapped as a column in table user (L_Name
     * @return last name of user
     */
    @Column(name = "L_Name")
    public String getLastName() {
        return lastName;
    }

    /**
     * set last name of user
     * @param lastName: new last name of user
     */
    public void setLastName(String lastName) {
        validateName(lastName);
        this.lastName = lastName;
    }

    /**
     * get email of user
     * mapped as a column in table user (email), set as unique
     * @return email
     */
    @Column(name = "email", unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    /**
     * set email of user
     * @param email: new email of user
     */
    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    /**
     * get set of tasks the belongs to user
     * create a separate table for the user & project many-to-many relationship
     * @return set of tasks
     */
    @ManyToMany
    @JoinTable(
            name = "user_task",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    public Set<Task> getTasks() {
        return tasks;
    }

    /**
     * set the set of tasks for user
     * @param tasks: new list of tasks for user
     */
    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * get project assigned to user
     * adding a column to this relationship as a project id in user table
     * @return project
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return project;
    }

    /**
     * Assign project to user
     * @param project: project to be assigned
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * add task to project
     * @param task: task to be added
     */
    public void addTask(Task task) {
        if (tasks.add(task)) {
            System.out.println("Task added successfully");
        } else {
            throw new AlreadyExistsException("Task already exists for "+getFirstName());
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
                    System.out.println("Task \"" + task.getTitle() + "\" removed successfully from \"" + getFirstName()+ "\"'s list");
                }, () -> System.out.println("Task doesn't exist"));
    }

    /**
     * Method to validate name isnt null
     * @param name: name to be validated
     */
    private void validateName(String name) {
        if (name == null) {
            throw new InvalidNameException("First name can't be null");
        }
    }

    /**
     * Method to validate email is in correct format
     * @param email: email to be validated
     */
    private void validateEmail(String email) {
        if (email == null || !isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format. (correct example: Jon@gmail.com)");
        }
    }

    /**
     * Method to validate email is in correct format
     * @param email: email to be validated
     * @return true if correct, falst if not
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Update user information
     * @param firstName: new first name of user
     * @param lastName:  new last name of user
     * @param email      new email of user
     */
    public void updateUser(String firstName, String lastName, String email){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
    }
}
