package com.example.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "actor")
public class Actor {

    @Id
    @Column(name = "actor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Description is mandatory")
    @Column(name = "description")
    private String description;

    @NotBlank(message = "Born place is mandatory")
    @Column(name = "born_place")
    private String bornPlace;

    @NotNull(message = "Born year name is mandatory")
    @Column(name = "born_year")
    private Integer bornYear;

    @NotNull(message = "Height name is mandatory")
    @Column(name = "height")
    private Integer height;

    @Builder.Default
    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    private Set<Film> films = new HashSet<>();

}


