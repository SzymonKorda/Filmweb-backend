package com.example.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "film", uniqueConstraints = {@UniqueConstraint(
        columnNames = {
                "title",
                "release_year"
        }
)})
public class Film {

    @Id
    @Column(name = "film_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Column(name = "title")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Box office is mandatory")
    @Column(name = "box_office")
    private Integer boxOffice;

    @NotNull(message = "Duration is mandatory")
    @Column(name = "duration")
    private Integer duration;

    @NotNull(message = "Release year is mandatory")
    @Column(name = "release_year")
    private Integer releaseYear;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Film_Actor",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    @Builder.Default
    private List<Actor> actors = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "userFilms")
    private List<User> users = new ArrayList<>();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "film_id")
    private List<Comment> comments = new ArrayList<>();

}
