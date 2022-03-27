package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film", uniqueConstraints = {@UniqueConstraint(
        columnNames = {
                "title",
                "premiere_year"
        }
)})
public class Film {

    @Id
    @Column(name = "film_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Title is mandatory")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Boxoffice is mandatory")
    @Column(name = "boxoffice")
    private Integer boxoffice;

    @NotNull(message = "Duration is mandatory")
    @Column(name = "duration")
    private Integer duration;

    @NotNull(message = "Premiere year is mandatory")
    @Column(name = "premiere_year")
    private Integer premiereYear;


    //JOIN TABLES

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Film_Actor",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private List<Actor> actors = new ArrayList<>();

    @ManyToMany(mappedBy = "userFilms")
    private List<User> users = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "film_id")
    private List<Comment> comments = new ArrayList<>();

    public Film() {

    }

    public Film(@NotBlank(message = "Title is mandatory") String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPremiereYear() {
        return premiereYear;
    }

    public void setPremiereYear(Integer premiereYear) {
        this.premiereYear = premiereYear;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getBoxoffice() {
        return boxoffice;
    }

    public void setBoxoffice(Integer boxoffice) {
        this.boxoffice = boxoffice;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }
}
