package com.digitinary.jpa.entities.taskmanagement;

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
public class Project {

    private Integer id;
    private String name;
    private Set<Task> tasks = new HashSet<>();

    public Project() {
        // Default constructor required by JPA
    }

    public Project(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany
    @JoinTable(
            name = "project_task",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (tasks.add(task)) {
            task.setId(tasks.size() + 1);
            System.out.println("Task added successfully");
        } else {
            System.out.println("Task \"" + task.getTitle() + "\" already exists in \"" + this.getName() + "\" project");
        }
    }

    public void removeTask(String title) {
        tasks.stream().filter(task -> task.getTitle().equals(title))
                .findFirst().ifPresentOrElse(task -> {
                    tasks.remove(task);
                    System.out.println("Task \"" + task.getTitle() + "\" removed successfully from \"" + this.getName() + "\" project");
                }, () -> System.out.println("Task doesn't exist"));
    }

    public void listTasks() {
        tasks.forEach(task -> System.out.print(task.getTitle() + " - "));
    }

    public List<Task> filterByStatus() {
        return tasks.stream().filter(task -> task.getStatus().equals("PENDING")).collect(Collectors.toList());
    }

    public boolean find(String title) {
        return tasks.stream().anyMatch(task -> task.getTitle().equals(title));
    }


}
