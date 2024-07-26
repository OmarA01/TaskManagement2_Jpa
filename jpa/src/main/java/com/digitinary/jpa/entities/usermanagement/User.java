package com.digitinary.jpa.entities.usermanagement;

import com.digitinary.jpa.exceptions.InvalidEmailException;
import com.digitinary.jpa.exceptions.InvalidNameException;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Represents a user in the system
 *
 * @author Omar Asaad
 * @since 24/7/2024
 */

@Entity
@Table(name = "Users")
public class User {

    /**
     * an id for each user, a name, and an email
     */
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;

    @Column(
            updatable = false,
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            insertable = false
    )
    private LocalDateTime lastModified;


    public User(){

    }

    public User(String firstName, String lastName, String email){
        validateName(firstName);
        validateName(lastName);
        validateEmail(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "F_Name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        validateName(firstName);
        this.firstName = firstName;
    }

    @Column(name = "L_Name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        validateName(lastName);
        this.lastName = lastName;
    }

    @Column(unique = true, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateEmail(email);
        this.email = email;
    }

    private void validateName(String firstName) {
        if (firstName == null) {
            throw new InvalidNameException("First name can't be null");
        }
    }

    private void validateEmail(String email) {
        if (email == null || !isValidEmail(email)) {
            throw new InvalidEmailException("Invalid email format. (correct example: Jon@gmail.com)");
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    public void updateUser(String firstName, String lastName, String email){
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
    }
}
